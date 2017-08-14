package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zackratos.shanbaywork.R;

/**
 * Created by Administrator on 2017/8/14.
 */

public class BitmapUtils {

    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;

    public BitmapUtils(Context context){

        mLocalCacheUtils = new LocalCacheUtils(context);
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils);
    }


    public void disPlay(ImageView ivPic, String name) {
        ivPic.setImageResource(R.drawable.image_default);
        Bitmap bitmap;


        //本地缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(name);
        if(bitmap !=null){
            ivPic.setImageBitmap(bitmap);
            return;
        }
        //网络缓存
        mNetCacheUtils.getBitmapFromNet(ivPic, name);
    }



}
