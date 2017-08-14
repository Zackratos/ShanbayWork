package org.zackratos.shanbaywork.loadimage.rximageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/7.
 */

public class DiskObservable extends CacheObservable {

    private Context context;

    private static final String TAG = "DiskObservable";

    private static final String IMAGE = "image";

    public DiskObservable(Context context) {
        this.context = context;
    }

    @Override
    public ImageInfo getImageFromCache(String imageName) {
        Log.d(TAG, "getImageFromCache: ");
        File path = createFolder();

        if (path == null) {
            return new ImageInfo(imageName, (Bitmap) null);
        }

//        Bitmap bitmap = BitmapFactory.decodeFile(path.getAbsolutePath() + "/" + imageName);
        Bitmap bitmap = BitmapTools.zoomBitmap(path.getAbsolutePath() + "/" + imageName);

        return new ImageInfo(imageName, bitmap);
    }


    @Override
    public void putImageToCache(ImageInfo imageInfo) {
        File path = createFolder();
        if (path == null || imageInfo.getBitmap() == null) {
            return;
        }

        saveBitmap(path, imageInfo);


    }



    private void saveBitmap(File path, ImageInfo imageInfo) {
        File file = new File(path, imageInfo.getName());
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            imageInfo.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }





    private File createFolder() {
        File path = context.getExternalFilesDir(IMAGE);
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

        return path;
    }




}
