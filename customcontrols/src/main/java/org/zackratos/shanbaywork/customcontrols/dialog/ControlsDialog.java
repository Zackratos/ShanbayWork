package org.zackratos.shanbaywork.customcontrols.dialog;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.zackratos.shanbaywork.customcontrols.R;
import org.zackratos.shanbaywork.customcontrols.WordApi;
import org.zackratos.shanbaywork.customcontrols.WordInfo;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 自定义控件 dialog 实现查词框
 * Created by Administrator on 2017/8/16.
 */

public class ControlsDialog extends BottomDialog {

    private static final String WORD = "word";

    public static ControlsDialog newInstance(String word) {
        ControlsDialog dialog = new ControlsDialog();
        Bundle args = new Bundle();
        args.putString(WORD, word);
        dialog.setArguments(args);
        return dialog;
    }

    // 传入的单词
    private String word;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getString(WORD);
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_controls, container, false);
        msgView = (TextView) view.findViewById(R.id.word_msg);
        contentView = (TextView) view.findViewById(R.id.word_content);
        pronunciationView = (TextView) view.findViewById(R.id.word_pronunciation);
        definitionView = (TextView) view.findViewById(R.id.word_definition);
        audioButton = (ImageButton) view.findViewById(R.id.word_audio);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        // 点击关闭 dialog
        view.findViewById(R.id.word_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        showWordInfo();
        return view;
    }

    /**
     *  显示单词信息
     */
    private void showWordInfo() {
        new Retrofit.Builder().baseUrl(WordApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WordApi.class)
                .rxQueryWord(word)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<WordInfo, ObservableSource<WordInfo.Data>>() {
                    @Override
                    public ObservableSource<WordInfo.Data> apply(@NonNull WordInfo wordInfo) throws Exception {
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

    // 记录 mediaPlayer 是否已设置了 DataSource
    private boolean hasSource;

    /**
     *  播放单词发音
     */
    private void playAudio() {
        if (audioUrl == null) {
            return;
        }
        try {
            if (!hasSource) {
                mMediaPlayer.setDataSource(audioUrl);
                mMediaPlayer.prepare();
                hasSource = true;
            }
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 回收 mediaPlayer 资源
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
