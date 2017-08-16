package org.zackratos.shanbaywork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.zackratos.shanbaywork.customcontrols.ControlsActivity;
import org.zackratos.shanbaywork.loadimage.ImageActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void customDialog(View view) {
        startActivity(new Intent(this, ControlsActivity.class));
    }

    public void customView(View view) {
    }

    public void loadImage(View view) {
        startActivity(ImageActivity.newIntent(this));
    }


}
