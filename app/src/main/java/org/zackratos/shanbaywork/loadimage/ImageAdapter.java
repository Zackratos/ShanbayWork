package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.zackratos.shanbaywork.R;
import org.zackratos.shanbaywork.loadimage.imageloader.BitmapUtils;

/**
 * Created by Administrator on 2017/8/7.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    protected Context context;
    protected String[] images;

    public ImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {


        new BitmapUtils(context).disPlay((ImageView) holder.itemView, images[position]);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return images.length;
    }


//    protected abstract void showImage(ImageViewHolder holder, int position);





    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }


}
