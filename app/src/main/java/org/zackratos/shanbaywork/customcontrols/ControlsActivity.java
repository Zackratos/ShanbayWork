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






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());

        showByTextView();


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






}
