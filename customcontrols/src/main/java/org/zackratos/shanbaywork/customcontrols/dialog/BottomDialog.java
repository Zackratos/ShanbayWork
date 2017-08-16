package org.zackratos.shanbaywork.customcontrols.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import org.zackratos.shanbaywork.customcontrols.R;

/**
 * 从界面底部弹出的 dialog
 * Created by Administrator on 2017/8/16.
 */

public abstract class BottomDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题栏，设置主题
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置 dialog 的宽高
        getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //设置 dialog 的背景为 null
        getDialog().getWindow().setBackgroundDrawable(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        // 在底部显示
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        return createView(inflater, container);
    }

    /**
     * 设置布局
     * @param inflater
     * @param container
     * @return
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);
}
