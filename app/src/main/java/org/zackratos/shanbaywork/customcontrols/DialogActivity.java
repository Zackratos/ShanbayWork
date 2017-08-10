package org.zackratos.shanbaywork.customcontrols;

import android.content.Context;
import android.content.Intent;

import org.zackratos.shanbaywork.R;

/**
 * Created by Administrator on 2017/8/9.
 */

public class DialogActivity extends ControlsActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DialogActivity.class);
        return intent;
    }


    @Override
    protected int createContentView() {
        return R.layout.activity_controls;
    }


    @Override
    protected void showQueryWord(String word) {
        QueryWordDialog dialog = QueryWordDialog.newInstance(word);
        dialog.show(getSupportFragmentManager(), "tag");

    }
}
