package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/8/14.
 */

public class LocalCacheUtils {


    private String path;


    public LocalCacheUtils(Context context) {
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
            File file = new File(path, name);

            //通过得到文件的父文件,判断父文件是否存在
            File parentFile = file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
