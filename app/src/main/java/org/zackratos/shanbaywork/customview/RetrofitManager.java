package org.zackratos.shanbaywork.customview;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/8.
 */

public class RetrofitManager {



    public static Retrofit getRetrofit() {
        return Single.retrofit;

    }



    private static class Single {
        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WordApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



}
