package org.zackratos.shanbaywork.customview;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.zackratos.shanbaywork.BottomDialog;
import org.zackratos.shanbaywork.R;
import org.zackratos.shanbaywork.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getString(WORD);



    }



    private TextView contentView;

    private TextView pronunciationView;

    private TextView definitionView;

    private ImageButton audioButton;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.dialog_query_word, container, false);

        contentView = findView(view, R.id.word_content);
        pronunciationView = findView(view, R.id.word_pronunciation);
        definitionView = findView(view, R.id.word_definition);
        audioButton = findView(view, R.id.word_audio);
        showWordInfo();
        return view;
    }


    private <T extends View> T findView(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }



    private void showWordInfo() {

        RetrofitManager.getWordRetrofit().create(WordApi.class)
                .rxQueryWord(word)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<WordInfo>() {
                    @Override
                    public boolean test(@NonNull WordInfo wordInfo) throws Exception {
                        return "SUCCESS".equals(wordInfo.getMsg());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WordInfo>() {
                    @Override
                    public void accept(@NonNull WordInfo wordInfo) throws Exception {
                        WordInfo.Data data = wordInfo.getData();
                        contentView.setText(data.getContent());
                        pronunciationView.setText(data.getPronunciation());
                        definitionView.setText(data.getDefinition());
                        audioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }








}
