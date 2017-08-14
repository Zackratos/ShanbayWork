package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.zackratos.shanbaywork.R;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = "ImageActivity";


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ImageActivity.class);
        return intent;
    }



    private static final String NAME_1 = "f1672263006c6e28bb9dee7652fa4cf6.jpg";
    private static final String NAME_2 = "8c997fae9ebb2b22ecc098a379cc2ca3.jpg";
    private static final String NAME_3 = "2a4616f067285b4bd59fe5401cd7106b.jpeg";
    private static final String NAME_4 = "b0e3ab329c8d8218d2af5c8dfdc21125.jpg";
    private static final String NAME_5 = "670abb28408a9a0fc3dd9666e5ca1584.jpeg";
    private static final String NAME_6 = "1e8d675468ab61f4e5bdebd4bcb5f007.jpeg";
    private static final String NAME_7 = "9b2f93cbfa104dae1e67f540ff14a4c2.jpg";
    private static final String NAME_8 = "f5e0631e00a09edbbf2eb21eb71b4d3c.jpeg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);

        rv.setLayoutManager(new GridLayoutManager(this, 3));

        String[] images = new String[] {
                NAME_1,
                NAME_2,
                NAME_3,
                NAME_4,
                NAME_5,
                NAME_6,
                NAME_7,
                NAME_8,
        };


        rv.setAdapter(new ImageAdapter(this, images));

    }



//    protected abstract ImageAdapter setImageAdapter(String[] images);




}
