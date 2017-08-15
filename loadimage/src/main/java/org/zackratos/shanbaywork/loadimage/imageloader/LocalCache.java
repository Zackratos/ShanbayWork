package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/8/14.
 * 本地缓存
 */

public class LocalCache {

    //图片储存路径
    private String path;

    public LocalCache(Context context) {
        path = context.getExternalCacheDir().getParent() + "/images";
    }

    /**
     * 从本地读取图片
     * @param name
     */
    public Bitmap getBitmapFromLocal(String name){
        try {
            File file = new File(path, name);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     * @param name
     * @param bitmap
     */
    public void setBitmapToLocal(String name, Bitmap bitmap){
        try {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file = new File(path, name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
