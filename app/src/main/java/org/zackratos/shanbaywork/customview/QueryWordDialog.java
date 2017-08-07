package org.zackratos.shanbaywork.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zackratos.shanbaywork.BottomDialog;
import org.zackratos.shanbaywork.R;

/**
 * Created by Administrator on 2017/8/7.
 */

public class QueryWordDialog extends BottomDialog {

    private static final String WORD = "word";

    public static QueryWordDialog newInstance(String word) {
        QueryWordDialog dialog = new QueryWordDialog();
        Bundle args = new Bundle();
        args.putString(WORD, word);
        dialog.setArguments(args);
        return dialog;
    }


    private String word;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getString(WORD);
    }




    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.dialog_query_word, container, false);

        return view;
    }



    public void setData() {



    }






}
