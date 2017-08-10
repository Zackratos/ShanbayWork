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
import org.zackratos.shanbaywork.loadimage.imageloader.ImageInfo;
import org.zackratos.shanbaywork.loadimage.imageloader.RxImageLoader;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/7.
 */

public abstract class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    protected Context context;
    protected String[] images;

    public ImageAdapter(Context contexts, String[] images) {
        this.context = contexts;
        this.images = images;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {


        showImage(holder, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position);
            }
        });

/*        RxImageLoader.with(context)
                .load(images[position])
                .error(R.drawable.image_default)
                .into((ImageView) holder.itemView);*/

/*        Glide.with(context)
                .load(imageInfos[position].getUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //去除 glide 自带的磁盘缓存
                .skipMemoryCache(true)                      //去除 glide 自带的内存缓存
                .error(R.drawable.image_default)
                .into((ImageView) holder.itemView);*/
    }


    @Override
    public int getItemCount() {
        return images.length;
    }


    protected abstract void showImage(ImageViewHolder holder, int position);





    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }


}
