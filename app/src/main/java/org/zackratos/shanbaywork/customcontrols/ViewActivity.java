package org.zackratos.shanbaywork.customcontrols;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.zackratos.shanbaywork.R;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ViewActivity extends ControlsActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ViewActivity.class);
        return intent;
    }


    private QueryWordView queryWordView;

    private ObjectAnimator closeAnimator;
    private ObjectAnimator openAnimator;
    private boolean opened;
//    private int queryHeight;

//    private float translationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryWordView = (QueryWordView) findViewById(R.id.query_word_view);
        final float translationY = queryWordView.getTranslationY();


        queryWordView.setOnCloseListener(new QueryWordView.OnCloseListener() {
            @Override
            public void onClose() {
                if (closeAnimator == null) {
                    closeAnimator = ObjectAnimator.ofFloat(queryWordView, "translationY",
                            0, translationY);
                }

                closeAnimator.start();
                opened = false;
            }
        });
    }

/*    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            queryHeight = queryWordView.getHeight();
        }
    }*/

    @Override
    protected int createContentView() {
        return R.layout.activity_view;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //回收 mediaplayer
        queryWordView.releaseMediaPlayer();
    }



    @Override
    protected void showQueryWord(String word) {

        if (openAnimator == null) {
            openAnimator = ObjectAnimator.ofFloat(queryWordView, "translationY",
                    queryWordView.getTranslationY(), 0);
        }

        if (!opened) {
            openAnimator.start();
        }
        queryWordView.setWord(word);

        opened = true;

    }
}
