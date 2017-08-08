package org.zackratos.shanbaywork.loadimage.imageloader;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2017/8/7.
 */

public abstract class CacheObservable {


    public Observable<ImageInfo> getImage(String imageName) {


        return Observable.just(imageName)
                .map(new Function<String, ImageInfo>() {
                    @Override
                    public ImageInfo apply(@NonNull String s) throws Exception {
                        return getImageFromCache(s);
                    }
                });
    }



    public abstract ImageInfo getImageFromCache(String imageName);

    public abstract void putImageToCache(ImageInfo image);



}
