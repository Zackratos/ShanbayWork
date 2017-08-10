package org.zackratos.shanbaywork.customcontrols;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Administrator on 2017/8/7.
 */

public class QueryWordDialog extends BottomDialog {

    private static final String TAG = "QueryWordDialog";

    private static final String WORD = "word";

    public static QueryWordDialog newInstance(String word) {
        QueryWordDialog dialog = new QueryWordDialog();
        Bundle args = new Bundle();
        args.putString(WORD, word);
        dialog.setArguments(args);
        return dialog;
    }


    private String word;

    private MediaPlayer mediaPlayer;

    private String audioUrl;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getString(WORD);

    }


    private TextView msgView;

    private TextView contentView;

    private TextView pronunciationView;

    private TextView definitionView;

    private ImageButton audioButton;




    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.dialog_query_word, container, false);

        msgView = findView(view, R.id.word_msg);
        contentView = findView(view, R.id.word_content);
        pronunciationView = findView(view, R.id.word_pronunciation);
        definitionView = findView(view, R.id.word_definition);
        audioButton = findView(view, R.id.word_audio);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        findView(view, R.id.word_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        showWordInfo();
        return view;
    }


    private <T extends View> T findView(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }




    private void playAudio() {

        if (audioUrl == null) {
            return;
        }

        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
            }
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void showWordInfo() {

        RetrofitManager.getWordRetrofit().create(WordApi.class)
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
                    }
                });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
