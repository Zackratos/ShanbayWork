package org.zackratos.shanbaywork.customcontrols;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import org.zackratos.shanbaywork.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ControlsActivity extends AppCompatActivity {


    private static final String TAG = "ControlsActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());

        showByTextView();
/*        String text = getString(R.string.text);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ParagraphAdapter(this, text.split("\n\n")));*/


    }





    private void showByTextView() {

        String text = getString(R.string.text);
        SpannableString spannableString = new SpannableString(text);
        final TextView tv = (TextView) findViewById(R.id.text_view);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        int start = 0;
        int end = 0;


        for (String paragraph : text.split("\n\n")) {

            for (final String word : paragraph.split(" ")) {



                end = start + word.length();

                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        tv.getText()
                                .subSequence(tv.getSelectionStart(),
                                        tv.getSelectionEnd());

//                        showQueryWord(word.replaceAll("\\pP", ""));
                        showQueryWord(word.replaceAll("\\.", "")
                                .replaceAll(",", "").replaceAll("\\[", "")
                                .replaceAll("]", "").replaceAll("''", ""));
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {

                    }
                }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                start = end + 1;
            }

            start = end + 2;
        }

        tv.setText(spannableString);


    }


    @LayoutRes
    protected abstract int createContentView();

    protected abstract void showQueryWord(String word);



/*    public void getEachWord(TextView textView){
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
    }*/



    /*    private void showByLinearLayout() {

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

                        TextView tv = (TextView) widget;
                        String s = tv
                                .getText()
                                .subSequence(tv.getSelectionStart(),
                                        tv.getSelectionEnd()).toString();
                        QueryWordDialog dialog = QueryWordDialog.newInstance(s);
                        dialog.show(getSupportFragmentManager(), "tag");

                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {


                    }
                }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                start = end + 1;
            }

            container.addView(newTextView(spannableString));
        }
    }*/


/*    private TextView newTextView(SpannableString spannableString) {
        TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_paragraph, container, false);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(spannableString);
        return tv;
    }*/





/*    private void clickWord(String text) {
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
*//*                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return s.replaceAll(",", "")
                                .replaceAll("\\.", "")
                                .replaceAll("'", "")
                                .replaceAll("\\[", "")
                                .replaceAll("]", "");
                    }
                })*//*
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
    }*/





}
