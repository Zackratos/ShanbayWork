package org.zackratos.shanbaywork.customcontrols;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/16.
 */

public interface WordApi {

    String BASE_URL = "https://api.shanbay.com/";

    @GET("bdc/search")
    Observable<WordInfo> rxQueryWord(@Query("word")String word);
}
