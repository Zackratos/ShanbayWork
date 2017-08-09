package org.zackratos.shanbaywork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.zackratos.shanbaywork.customcontrols.ControlsActivity;
import org.zackratos.shanbaywork.loadimage.ImageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void customView(View view) {
        enter(ControlsActivity.newIntent(this));
    }



    public void loadImage(View view) {
        enter(ImageActivity.newIntent(this));
    }




    private void enter(Intent intent) {
        startActivity(intent);
    }


}
