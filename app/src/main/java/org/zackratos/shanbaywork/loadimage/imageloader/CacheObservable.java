package org.zackratos.shanbaywork.loadimage.imageloader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Administrator on 2017/8/7.
 */

public abstract class CacheObservable {


    public Observable<Image> getImage(ImageInfo imageInfo) {


        return Observable.just(imageInfo)
                .map(new Function<ImageInfo, Image>() {
                    @Override
                    public Image apply(@NonNull ImageInfo imageInfo) throws Exception {

                        return getImageFromCache(imageInfo);
                    }
                });
    }



    public abstract Image getImageFromCache(ImageInfo imageInfo);

    public abstract void putImageToCache(Image image);



}
