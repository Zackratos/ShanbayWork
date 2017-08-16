package org.zackratos.shanbaywork.customcontrols;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 *
 * Created by Administrator on 2017/8/16.
 */

public abstract class ControlsActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
        tv = (TextView) findViewById(R.id.text_view);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        showText();
    }

    /**
     * 显示文本
     */
    private void showText() {
        new AsyncTask<Void, Void, SpannableString>() {
            @Override
            protected SpannableString doInBackground(Void... params) {
                String englishText = getString(R.string.english_text);
                SpannableString spannableString = new SpannableString(englishText);
                int start = 0; // 单词首字母在文本中的位置
                int end = 0;   // 单词末尾字母在文本中的位置
                // 循环每个段落
                for (String paragraph : englishText.split("\n\n")) {
                    // 循环每个段落中的每一个单词
                    for (final String word : paragraph.split(" ")) {
                        end = start + word.length();
                        spannableString.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                // 点击单词后高亮显示
                                tv.getText().subSequence(tv.getSelectionStart(),
                                        tv.getSelectionEnd());
                                showWordControls(word);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                // 不调用 super 方法，保证每个单词不变色，不加下划线
                            }
                        }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        start = end + 1;
                    }
                    start = end + 2;
                }
                return spannableString;
            }

            @Override
            protected void onPostExecute(SpannableString spannableString) {
                tv.setText(spannableString);
            }
        }.execute();
    }


    /**
     *  设置布局
     * @return 布局文件
     */
    @LayoutRes
    protected abstract int contentView();


    /**
     * 弹出查词框
     * @param word 要查询的单词
     */
    protected abstract void showWordControls(String word);
}
