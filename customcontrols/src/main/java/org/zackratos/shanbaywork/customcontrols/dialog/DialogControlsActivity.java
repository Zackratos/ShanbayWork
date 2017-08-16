package org.zackratos.shanbaywork.customcontrols.dialog;

import android.content.Context;
import android.content.Intent;

import org.zackratos.shanbaywork.customcontrols.ControlsActivity;
import org.zackratos.shanbaywork.customcontrols.R;

/**
 *
 * Created by Administrator on 2017/8/16.
 */

public class DialogControlsActivity extends ControlsActivity {

    private static final String WORD_TAG = "word";

    /**
     *  使用静态方法启动 activity ，方便传递数据
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DialogControlsActivity.class);
        return intent;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_controls;
    }

    @Override
    protected void showWordControls(String word) {
        ControlsDialog dialog = ControlsDialog.newInstance(word);
        dialog.show(getSupportFragmentManager(), WORD_TAG);
    }
}
