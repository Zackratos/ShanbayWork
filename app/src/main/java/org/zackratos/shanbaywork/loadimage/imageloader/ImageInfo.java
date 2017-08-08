package org.zackratos.shanbaywork.loadimage.imageloader;


import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/8/7.
 */

public class ImageInfo {

    private String name;

//    private String url;
    private Bitmap bitmap;


    public ImageInfo(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
