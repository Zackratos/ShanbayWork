package org.zackratos.shanbaywork.loadimage.rximageloader;

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

        networkObservable = new NetworkObservable();
    }


    public Observable<ImageInfo> getImageFromDisk(String imageName) {
        return diskObservable.getImage(imageName);
    }



    public Observable<ImageInfo> getImageFromNetwork(String imageName, @DrawableRes final int defaultId) {
        return networkObservable.getImage(imageName)
                .doOnNext(new Consumer<ImageInfo>() {
                    @Override
                    public void accept(@NonNull ImageInfo imageInfo) throws Exception {
                        byte[] bytes = imageInfo.getBytes();
                        if (bytes == null) {
                            Bitmap bitmap = BitmapTools.zoomBitmap(context.getResources(), defaultId);
                            imageInfo.setBitmap(bitmap);
                            return;
                        }
                        Bitmap originBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageInfo.setBitmap(originBitmap);
                        imageInfo.setBytes(null);
                        diskObservable.putImageToCache(imageInfo);             //原尺寸保存
                        Bitmap zoomBitmap = BitmapTools.zoomBitmap(bytes);     //压缩后显示
                        imageInfo.setBitmap(zoomBitmap);
                    }
                });
/*                .doOnNext(new Consumer<ImageInfo>() {
                    @Override
                    public void accept(@NonNull ImageInfo imageInfo) throws Exception {
                        diskObservable.putImageToCache(imageInfo);
                        if (imageInfo.getBitmap() == null && defaultId != 0) {
                            Bitmap bitmap = BitmapTools.zoomBitmap(context.getResources(), defaultId);
                            imageInfo.setBitmap(bitmap);
                        }
                    }
                });*/
    }









}
