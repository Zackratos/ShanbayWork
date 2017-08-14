package org.zackratos.shanbaywork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.zackratos.shanbaywork.customcontrols.DialogActivity;
import org.zackratos.shanbaywork.customcontrols.ViewActivity;
import org.zackratos.shanbaywork.loadimage.ImageActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void customDialog(View view) {
        enter(DialogActivity.newIntent(this));
    }



    public void customView(View view) {
        enter(ViewActivity.newIntent(this));
    }



    public void loadGlide(View view) {
        enter(ImageActivity.newIntent(this));
    }






    private void enter(Intent intent) {
        startActivity(intent);
    }






}
