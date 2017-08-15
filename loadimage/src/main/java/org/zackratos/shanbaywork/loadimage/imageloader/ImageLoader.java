package org.zackratos.shanbaywork.loadimage.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;



/**
 * Created by Administrator on 2017/8/14.
 * 图片加载框架
 */

public class ImageLoader {

    private NetCache mNetCache;
    private LocalCache mLocalCache;

    public ImageLoader(Context context){
        mLocalCache = new LocalCache(context);
        mNetCache = new NetCache(context, mLocalCache);
    }


    /**
     * 显示图片
     * @param imageView imageview
     * @param name 图片的名字
     * @return 返回 AsyncTask 对象，方便取消任务
     */
    public NetCache.BitmapTask disPlay(ImageView imageView, String name) {
        Bitmap bitmap;
        //本地缓存
        bitmap = mLocalCache.getBitmapFromLocal(name);
        if(bitmap !=null){
            imageView.setImageBitmap(bitmap);
            return null;
        }
        //网络缓存
        return mNetCache.getBitmapFromNet(imageView, name);
    }


}
