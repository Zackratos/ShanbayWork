package org.zackratos.shanbaywork.customcontrols.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.zackratos.shanbaywork.customcontrols.R;
import org.zackratos.shanbaywork.customcontrols.WordApi;
import org.zackratos.shanbaywork.customcontrols.WordInfo;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  自定义 view 实现查词框
 * Created by Administrator on 2017/8/16.
 */

public class ControlsView extends FrameLayout {

    // 发音的 url 地址
    private String audioUrl;

    // 错误信息
    private TextView msgView;
    // 单词内容
    private TextView contentView;
    // 音标
    private TextView pronunciationView;
    // 释义
    private TextView definitionView;
    // 播放按钮
    private ImageButton audioButton;

    private MediaPlayer mMediaPlayer;

    public ControlsView(@NonNull Context context) {
        this(context, null);
    }

    public ControlsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_controls, this, false);
        msgView = (TextView) view.findViewById(R.id.word_msg);
        contentView = (TextView) view.findViewById(R.id.word_content);
        pronunciationView = (TextView) view.findViewById(R.id.word_pronunciation);
        definitionView = (TextView) view.findViewById(R.id.word_definition);
        audioButton = (ImageButton) view.findViewById(R.id.word_audio);
        audioButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        view.findViewById(R.id.word_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClose();
                }
            }
        });
        addView(view);

        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 显示单词信息
     * @param word 传入的单词
     */
    public void showWordInfo(String word) {
        new Retrofit.Builder().baseUrl(WordApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WordApi.class)
                .rxQueryWord(word)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<WordInfo, ObservableSource<WordInfo.Data>>() {
                    @Override
                    public ObservableSource<WordInfo.Data> apply(@io.reactivex.annotations.NonNull WordInfo wordInfo) throws Exception {
                        // 若请求成功，发射单词查询数据
                        if ("SUCCESS".equals(wordInfo.getMsg())) {
                            return Observable.just(wordInfo.getData());
                        }
                        // 若请求失败，发射错误信息
                        return Observable.error(new Throwable(wordInfo.getMsg()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WordInfo.Data>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull WordInfo.Data data) throws Exception {
                        contentView.setText(data.getContent());
                        pronunciationView.setText(String.format("/%s/", data.getPronunciation()));
                        definitionView.setText(data.getDefinition());
                        audioButton.setImageResource(R.drawable.ic_volume_up_24dp);
                        msgView.setText(null);
                        audioUrl = data.getAudio();
                        hasSource = false;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        msgView.setText(throwable.getMessage());
                        contentView.setText(null);
                        pronunciationView.setText(null);
                        definitionView.setText(null);
                        audioButton.setImageDrawable(null);
                        audioUrl = null;
                    }
                });
    }

    // 记录 mediaPlayer 是否已设置了 DataSource
    private boolean hasSource;

    /**
     *  播放发音
     */
    private void playAudio() {
        if (audioUrl == null) {
            return;
        }
        try {
            if (!hasSource) {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(audioUrl);
                mMediaPlayer.prepare();
                hasSource = true;
            }
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  释放 mediaPlayer 对象
     */
    public void releaseMediaPlayer() {
        if (mMediaPlayer == null) {
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    /**
     *  关闭查词框的回调接口
     */
    interface OnCloseListener {
        void onClose();
    }

    private OnCloseListener mListener;

    public void setOnCloseListener(OnCloseListener listener) {
        this.mListener = listener;
    }



}
