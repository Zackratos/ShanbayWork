package org.zackratos.shanbaywork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.zackratos.shanbaywork.customcontrols.dialog.DialogControlsActivity;
import org.zackratos.shanbaywork.customcontrols.view.ViewControlsActivity;
import org.zackratos.shanbaywork.loadimage.ImageActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void customDialog(View view) {
        startActivity(DialogControlsActivity.newIntent(this));
    }

    public void customView(View view) {
        startActivity(ViewControlsActivity.newIntent(this));
    }

    public void loadImage(View view) {
        startActivity(ImageActivity.newIntent(this));
    }


}
