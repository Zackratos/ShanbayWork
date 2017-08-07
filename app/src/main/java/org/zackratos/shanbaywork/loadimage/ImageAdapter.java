package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.zackratos.shanbaywork.R;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/7.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private ImageInfo[] imageInfos;

    public ImageAdapter(Context contexts, ImageInfo[] imageInfos) {
        this.context = contexts;
        this.imageInfos = imageInfos;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Observable.just(imageInfos[position])
                .subscribeOn(Schedulers.io());

        Glide.with(context)
                .load(imageInfos[position].getUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //去除 glide 自带的磁盘缓存
                .skipMemoryCache(true)                      //去除 glide 自带的内存缓存
                .error(R.drawable.image_default)
                .into((ImageView) holder.itemView);
    }


    @Override
    public int getItemCount() {
        return imageInfos.length;
    }






    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }


}
