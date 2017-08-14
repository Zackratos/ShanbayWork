package org.zackratos.shanbaywork.customcontrols;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.zackratos.shanbaywork.R;
import org.zackratos.shanbaywork.RetrofitManager;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/9.
 */

public class QueryWordView extends FrameLayout {


    private TextView msgView;
    private TextView contentView;
    private TextView pronunciationView;
    private TextView definitionView;
    private ImageButton audioButton;

    private MediaPlayer mediaPlayer;

    private String audioUrl;
    private String lastAudioUrl;


    public QueryWordView(Context context) {
        this(context, null);
    }

    public QueryWordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_query_word, this, false);
        msgView = findView(view, R.id.word_msg);
        contentView = findView(view, R.id.word_content);
        pronunciationView = findView(view, R.id.word_pronunciation);
        definitionView = findView(view, R.id.word_definition);
        findView(view, R.id.word_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClose();
                }
            }
        });
        audioButton = findView(view, R.id.word_audio);
        audioButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        addView(view);
    }


    private <T extends View> T findView(View view, @IdRes int id) {

        return (T) view.findViewById(id);

    }


    private void playAudio() {
        if (audioUrl == null) {
            return;
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            startMediaPlayer();
        } else {
            if (!audioUrl.equals(lastAudioUrl)) { // 如果 url 变了就重置 mediaplayer
                mediaPlayer.reset();
                startMediaPlayer();
            } else {    // url 没变就直接播放
                mediaPlayer.start();
            }
        }


        lastAudioUrl = audioUrl;



    }



    private void startMediaPlayer() {

        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void setWord(String word) {
        RetrofitManager.getWordRetrofit()
                .create(WordApi.class)
                .rxQueryWord(word)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<WordInfo, ObservableSource<WordInfo.Data>>() {
                    @Override
                    public ObservableSource<WordInfo.Data> apply(@NonNull WordInfo wordInfo) throws Exception {
                        if ("SUCCESS".equals(wordInfo.getMsg())) {
                            return Observable.just(wordInfo.getData());
                        }
                        return Observable.error(new Throwable(wordInfo.getMsg()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WordInfo.Data>() {
                    @Override
                    public void accept(@NonNull WordInfo.Data data) throws Exception {
                        msgView.setText(null);
                        contentView.setText(data.getContent());
                        pronunciationView.setText(String.format("/%s/", data.getPronunciation()));
                        definitionView.setText(data.getDefinition());
                        audioButton.setImageResource(R.drawable.ic_volume_up_24dp);
                        audioUrl = data.getAudio();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        msgView.setText(throwable.getMessage());
                        contentView.setText(null);
                        pronunciationView.setText(null);
                        definitionView.setText(null);
                        audioButton.setImageDrawable(null);
                        audioUrl = null;
                    }
                });
    }




    public void releaseMediaPlayer() {
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;

    }



    public interface OnCloseListener {
        void onClose();
    }

    public OnCloseListener listener;

    public void setOnCloseListener(OnCloseListener listener) {
        this.listener = listener;
    }







}
