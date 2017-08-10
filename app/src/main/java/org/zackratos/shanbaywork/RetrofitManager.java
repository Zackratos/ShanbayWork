package org.zackratos.shanbaywork;

import org.zackratos.shanbaywork.customcontrols.WordApi;
import org.zackratos.shanbaywork.loadimage.imageloader.ImageApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/8.
 */

public class RetrofitManager {



    public static Retrofit getWordRetrofit() {
        return Single.wordRetrofit;
    }


    public static Retrofit getImageRetrofit() {
        return Single.imageRetrofit;
    }



    private static class Single {
        private static final Retrofit wordRetrofit = new Retrofit.Builder()
                .baseUrl(WordApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        private static final Retrofit imageRetrofit = new Retrofit.Builder()
                .baseUrl(ImageApi.BASE_URL)
                .build();
    }



}
