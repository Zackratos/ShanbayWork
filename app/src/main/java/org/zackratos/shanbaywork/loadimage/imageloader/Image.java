package org.zackratos.shanbaywork.loadimage.imageloader;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/8/7.
 */

public class Image {

    private ImageInfo imageInfo;

    private Bitmap bitmap;

    public Image(ImageInfo imageInfo, Bitmap bitmap) {
        this.imageInfo = imageInfo;
        this.bitmap = bitmap;

    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
