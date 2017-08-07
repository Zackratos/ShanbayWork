package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2017/8/7.
 */

public class DiskObservable extends CacheObservable {

    private Context context;

    private static final String TAG = "DiskObservable";

    public DiskObservable(Context context) {
        this.context = context;
    }

    @Override
    public Image getImageFromCache(ImageInfo imageInfo) {

        Log.d(TAG, "getImageFromCache: ");
        String name = imageInfo.getName();
        File path = context.getExternalFilesDir("images");
        if (path == null) {
            return null;
        }
        
        if (!path.exists()) {
            path.mkdir();
        } else {
            if (path.isFile()) {
                path.delete();
                path.mkdir();
            }
        }
        Log.d(TAG, "getImageFromCache: " + path.getAbsolutePath() + "/" + name);
        Bitmap bitmap = BitmapFactory.decodeFile(path.getAbsolutePath() + "/" + name);
        
        return new Image(imageInfo, bitmap);
    }



    @Override
    public void putImageToCache(Image image) {

    }





}
