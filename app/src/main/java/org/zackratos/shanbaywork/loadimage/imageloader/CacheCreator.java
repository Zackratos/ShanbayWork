package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/8/8.
 */

public class CacheCreator {

    private DiskObservable diskObservable;

    private NetworkObservable networkObservable;

    private Context context;

    public CacheCreator(Context context) {
        this.context = context;

        diskObservable = new DiskObservable(context);

        networkObservable = new NetworkObservable(context);
    }


    public Observable<ImageInfo> getImageFromDisk(String imageName) {
        return diskObservable.getImage(imageName);
    }



    public Observable<ImageInfo> getImageFromNetwork(String imageName, @DrawableRes final int defaultId) {
        return networkObservable.getImage(imageName)
                .doOnNext(new Consumer<ImageInfo>() {
                    @Override
                    public void accept(@NonNull ImageInfo imageInfo) throws Exception {
                        diskObservable.putImageToCache(imageInfo);
                        if (imageInfo.getBitmap() == null && defaultId != 0) {
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), defaultId);
                            imageInfo.setBitmap(bitmap);
                        }
                    }
                });
    }









}
