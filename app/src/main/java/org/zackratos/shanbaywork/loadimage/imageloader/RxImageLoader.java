package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/7.
 * 仿 glide 的图片加载框架
 */

public class RxImageLoader {


    private Context context;

    private CacheCreator cacheCreator;


    private RxImageLoader(Context context) {
        //使用 ApplicationContext 防止内存泄漏
        this.context = context;

        cacheCreator = new CacheCreator(context);
    }


    /**
     * 懒汉式双重校验锁
     * @param context
     * @return
     */


    public static RxImageLoader with(Context context) {
/*        if (imageLoader == null) {
            synchronized (RxImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new RxImageLoader(context);
                }
            }
        }*/


        return new RxImageLoader(context);

    }



    private String imageName;

    public RxImageLoader load(String imageName) {
        this.imageName = imageName;
        return this;
    }


    @DrawableRes
    private int errorImage;

    public RxImageLoader error(@DrawableRes int resourceId) {
        this.errorImage = resourceId;
        return this;
    }





    private static final String TAG = "RxImageLoader";


    private boolean reponse;


    public void into(final ImageView imageView) {
        reponse = true;
        Observable.concat(cacheCreator.getImageFromDisk(imageName),
                cacheCreator.getImageFromNetwork(imageName, errorImage))
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<ImageInfo>() {
                    @Override
                    public boolean test(@NonNull ImageInfo imageInfo) throws Exception {
                        return imageInfo.getBitmap() != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageInfo>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull ImageInfo imageInfo) {
                        if (!reponse) {
                            return;
                        }
                        Log.d(TAG, "onNext: " + imageInfo.getName());
                        Bitmap bitmap = imageInfo.getBitmap();
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                        disposable.dispose();
                        reponse = false;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });





    }









/*    public static class Builder {

        private Context context;

        private String url;


        private Builder(Context context) {

        }


        public Builder with(Context context) {
            this.context = context;
            return this;
        }



        public Builder load(String url) {
            this.url = url;
            return this;
        }


        public void into(ImageView imageView) {

        }


    }*/
}
