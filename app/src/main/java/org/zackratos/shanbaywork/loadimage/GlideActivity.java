package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/8/9.
 */

public class GlideActivity extends ImageActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GlideActivity.class);
        return intent;
    }


    @Override
    protected ImageAdapter setImageAdapter(String[] images) {
        return new GlideAdapter(this, images);
    }
}
