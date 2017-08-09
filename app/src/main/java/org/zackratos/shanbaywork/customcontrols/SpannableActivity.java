package org.zackratos.shanbaywork.customcontrols;

import android.content.Context;
import android.content.Intent;

import org.zackratos.shanbaywork.R;

/**
 * Created by Administrator on 2017/8/9.
 */

public class SpannableActivity extends ControlsActivity {


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SpannableActivity.class);
        return intent;
    }


    @Override
    protected int createContentView() {
        return R.layout.activity_controls;
    }


    @Override
    protected void showQueryWord(String word) {
        QueryWordDialog dialog = QueryWordDialog.newInstance(word
                .replaceAll("\\.", "").replaceAll(",", "")
                .replaceAll("\\[", "").replaceAll("]", "")
                .replaceAll("''", ""));
        dialog.show(getSupportFragmentManager(), "tag");

    }
}
