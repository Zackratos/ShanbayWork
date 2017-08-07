package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/8/7.
 * 仿 glide 的图片加载框架
 */

public class RxImageLoader {

    private static RxImageLoader imageLoader;

    private Context context;

    private RxImageLoader(Context context) {
        //使用 ApplicationContext 防止内存泄漏
        this.context = context.getApplicationContext();
    }


    /**
     * 懒汉式双重校验锁
     * @param context
     * @return
     */

    public static RxImageLoader with(Context context) {
        if (imageLoader == null) {
            synchronized (RxImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new RxImageLoader(context);
                }
            }
        }

        return imageLoader;

    }



    private ImageInfo imageInfo;

    public RxImageLoader load(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
        return imageLoader;
    }


    private Disposable disposable;


    private static final String TAG = "RxImageLoader";

    public void into(ImageView imageView) {
        Log.d(TAG, "into: ");
        Observable.concat(new DiskObservable(context).getImage(imageInfo), new NetworkObservable().getImage(imageInfo))

                .subscribe(new Observer<Image>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Image image) {
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }




/*    public static class Builder {

        private String url;


        private Builder(Context context) {

        }



        public Builder load(String url) {
            this.url = url;
            return this;
        }


        public void into(ImageView imageView) {

        }


    }*/
}
