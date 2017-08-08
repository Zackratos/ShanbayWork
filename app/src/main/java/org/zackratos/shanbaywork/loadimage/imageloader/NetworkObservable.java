package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
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

    private Context context;

    public NetworkObservable(Context context) {
        this.context = context;
    }


    @Override
    public ImageInfo getImageFromCache(String imageName) {
        Bitmap bitmap = downloadImage(imageName);
        return new ImageInfo(imageName, bitmap);
    }




    private Bitmap downloadImage(String imageName) {

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

            byte[] bytes = body.bytes();
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void putImageToCache(ImageInfo image) {

    }
}
