package org.zackratos.shanbaywork.customview;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface WordApi {

    public static final String BASE_URL = "https://api.shanbay.com/";



    @GET("bdc/search")
    Observable<WordInfo> rxQueryWord(@Query("word")String word);




}
