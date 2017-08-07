package org.zackratos.shanbaywork.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.zackratos.shanbaywork.R;

public class ViewActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ViewActivity.class);
        return intent;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        TextView tv = (TextView) findViewById(R.id.text_view);
        tv.setText(R.string.english_text);




    }
}
