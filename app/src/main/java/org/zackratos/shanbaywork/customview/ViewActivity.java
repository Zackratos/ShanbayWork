package org.zackratos.shanbaywork.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.zackratos.shanbaywork.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class ViewActivity extends AppCompatActivity {


    private static final String TAG = "ViewActivity";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ViewActivity.class);
        return intent;
    }

    private int start;
    private int end;

    private LinearLayout container;

//    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
//        container = (LinearLayout) findViewById(R.id.linear_layout);
/*
        tv = (TextView) findViewById(R.id.text_view);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        String text = getString(R.string.text);



        tv.setText(text, TextView.BufferType.SPANNABLE);
        getEachWord(tv);*/
        String text = getString(R.string.text);

        container = (LinearLayout) findViewById(R.id.paragraphContainer);

        for (String paragraph : text.split("\n\n")) {
            SpannableString spannableString = new SpannableString(paragraph);

            start = 0;
            end = 0;

            for (String word : paragraph.split(" ")) {
                end = start + word.length();
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        QueryWordDialog dialog = QueryWordDialog.newInstance("hello");
                        dialog.show(getSupportFragmentManager(), "tag");

                    }
                }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                start = end + 1;
            }

            container.addView(newTextView(spannableString));


        }




    }



    private TextView newTextView(SpannableString spannableString) {
        TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_paragraph, container, false);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(spannableString);
        return tv;
    }






    public void getEachWord(TextView textView){
        Spannable spans = (Spannable)textView.getText();
        Integer[] indices = getIndices(
                textView.getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan();
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
        textView.setHighlightColor(Color.BLUE);
    }


    private ClickableSpan getClickableSpan(){
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                String s = tv
                        .getText()
                        .subSequence(tv.getSelectionStart(),
                                tv.getSelectionEnd()).toString();
                Log.d("tapped on:", s);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    public static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }




    private void clickWord2(String text) {



    }






    private void clickWord(String text) {
        final SpannableString spannableString = new SpannableString(text);
        final BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));

        Observable.just(text)
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        return Observable.fromArray(s.split("\n\n"));
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        String paragraph = s.trim();
                        return Observable.fromArray(paragraph.split(" "));
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
/*                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return s.replaceAll(",", "")
                                .replaceAll("\\.", "")
                                .replaceAll("'", "")
                                .replaceAll("\\[", "")
                                .replaceAll("]", "");
                    }
                })*/
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                });
    }





}
