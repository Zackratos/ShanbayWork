package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.zackratos.shanbaywork.loadimage.R;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/14.
 * 网络缓存
 */

public class NetCache {

    private LocalCache mLocalCache;
    private Context mContext;


    public NetCache(Context context, LocalCache localCache) {
        mContext = context;
        mLocalCache = localCache;
    }


    /**
     * 从网络下载图片
     * @param imageView
     * @param name
     * @return 返回 AsyncTask 对象，方便取消任务
     */
    public BitmapTask getBitmapFromNet(ImageView imageView, String name) {
        BitmapTask task = new BitmapTask();
        task.execute(imageView, name);
        return task;
    }

    /**
     * AsyncTask就是对handler和线程池的封装
     * 第一个泛型:参数类型
     * 第二个泛型:更新进度的泛型
     * 第三个泛型:onPostExecute的返回结果
     */
    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView imageView;
        private String name;

        /**
         * 后台耗时操作,存在于子线程中
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params) {
            imageView = (ImageView) params[0];
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
            //无网络或者下载失败时，显示默认图片
            if (result == null) {
                // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Resources resources = mContext.getResources();
                BitmapFactory.decodeResource(resources, R.drawable.image_default, options);
                options.inSampleSize = 3; //图片压缩为原来的 1/3
                // 使用获取到的inSampleSize值再次解析图片
                options.inJustDecodeBounds = false;
                imageView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.image_default, options));
                return;
            }

            imageView.setImageBitmap(result);
            //从网络获取图片后,保存至本地缓存
            mLocalCache.setBitmapToLocal(name, result);

        }
    }

    /**
     * 网络下载图片
     * @param name
     * @return
     */
    private Bitmap downLoadBitmap(String name) {
        try {
            Response<ResponseBody> response = new Retrofit.Builder()
                    .baseUrl(ImageApi.BASE_URL)
                    .build()
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
