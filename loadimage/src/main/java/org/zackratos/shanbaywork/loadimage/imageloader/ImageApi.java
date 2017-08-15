package org.zackratos.shanbaywork.loadimage.imageloader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/8/8.
 */

public interface ImageApi {

    String BASE_URL = "https://static.baydn.com/";

    @GET("media/media_store/image/{name}")
    Call<ResponseBody> downloadImage(@Path("name") String name);
}
