package org.zackratos.shanbaywork.customcontrols.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.zackratos.shanbaywork.customcontrols.ControlsActivity;
import org.zackratos.shanbaywork.customcontrols.R;

/**
 *
 * Created by Administrator on 2017/8/16.
 */

public class ViewControlsActivity extends ControlsActivity {

    /**
     * 静态方法启动 activity ，方便传递数据
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ViewControlsActivity.class);
        return intent;
    }

    private ControlsView controlsView;

    // 选词框弹出动画
    private ObjectAnimator openAnimator;

    // 选词框关闭动画
    private ObjectAnimator closeAnimator;

    // 记录选词框是否已弹出
    private boolean opened;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlsView = (ControlsView) findViewById(R.id.controls_view);
        float translationY = controlsView.getTranslationY();
        openAnimator = ObjectAnimator.ofFloat(controlsView, "translationY", translationY, 0);
        closeAnimator = ObjectAnimator.ofFloat(controlsView, "translationY", 0, translationY);
        controlsView.setOnCloseListener(new ControlsView.OnCloseListener() {
            @Override
            public void onClose() {
                closeAnimator.start();
                opened = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controlsView.releaseMediaPlayer();
    }

    @Override
    protected int contentView() {
        return R.layout.activity_controls_view;
    }

    @Override
    protected void showWordControls(String word) {
        controlsView.showWordInfo(word);
        if (!opened) {
            openAnimator.start();
            opened = true;
        }
    }
}
