package org.zackratos.shanbaywork.loadimage.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.zackratos.shanbaywork.RetrofitManager;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/14.
 */

public class NetCacheUtils {

    private LocalCacheUtils mLocalCacheUtils;


    public NetCacheUtils(LocalCacheUtils localCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
    }


    /**
     * 从网络下载图片
     * @param ivPic 显示图片的imageview
     * @param name   下载图片的
     */
    public void getBitmapFromNet(ImageView ivPic, String name) {
        new BitmapTask().execute(ivPic, name);//启动AsyncTask

    }


    /**
     * AsyncTask就是对handler和线程池的封装
     * 第一个泛型:参数类型
     * 第二个泛型:更新进度的泛型
     * 第三个泛型:onPostExecute的返回结果
     */
    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String name;

        /**
         * 后台耗时操作,存在于子线程中
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            ivPic = (ImageView) params[0];
            name = (String) params[1];

            return downLoadBitmap(name);
        }

        /**
         * 更新进度,在主线程中
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void[] values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时方法结束后执行该方法,主线程中
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                ivPic.setImageBitmap(result);

                //从网络获取图片后,保存至本地缓存
                mLocalCacheUtils.setBitmapToLocal(name, result);

            }
        }
    }



    /**
     * 网络下载图片
     * @param name
     * @return
     */
    private Bitmap downLoadBitmap(String name) {
        try {
            Response<ResponseBody> response = RetrofitManager.getImageRetrofit()
                    .create(ImageApi.class)
                    .downloadImage(name)
                    .execute();
            InputStream is = response.body().byteStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;  //宽高压缩为原来的1/3
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            return BitmapFactory.decodeStream(is, null, options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
