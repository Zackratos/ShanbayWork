package org.zackratos.shanbaywork.loadimage.rximageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.zackratos.shanbaywork.RetrofitManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/7.
 */

public class NetworkObservable extends CacheObservable {



    @Override
    public ImageInfo getImageFromCache(String imageName) {
//        Bitmap bitmap = downloadImage(imageName);
//        return new ImageInfo(imageName, bitmap);
        byte[] bytes = downloadImageByte(imageName);
        return new ImageInfo(imageName, bytes);
    }



    private byte[] downloadImageByte(String imageName) {
        try {
            Response<ResponseBody> response = RetrofitManager.getImageRetrofit()
                    .create(ImageApi.class)
                    .downloadImage(imageName)
                    .execute();
            if (response == null) {
                return null;
            }
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }
            return body.bytes();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private Bitmap downloadImage(String imageName) {

        byte[] bytes = downloadImageByte(imageName);
        if (bytes == null) {
            return null;
        }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

/*        try {
            Response<ResponseBody> response = RetrofitManager.getImageRetrofit()
                    .create(ImageApi.class)
                    .downloadImage(imageName)
                    .execute();
            if (response == null) {
                return null;
            }
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }

            byte[] bytes = body.bytes();
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;*/
    }


    @Override
    public void putImageToCache(ImageInfo image) {

    }
}
