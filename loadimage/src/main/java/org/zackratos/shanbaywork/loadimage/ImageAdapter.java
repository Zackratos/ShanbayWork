package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.zackratos.shanbaywork.loadimage.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private String[] mImages;
    private ImageLoader mImageLoader;

    //用于记录所有正在下载或等待下载的任务
    private List<AsyncTask> tasks;

    public ImageAdapter(Context context, String[] images) {
        this.mImages = images;
        mImageLoader = new ImageLoader(context);
        tasks = new ArrayList<>();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        ImageView imageView = (ImageView) holder.itemView;
        AsyncTask task = mImageLoader.disPlay(imageView, mImages[position]);
        if (task != null) {
            tasks.add(task);
        }
        // 图片下载失败时，点击重试
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.length;
    }

    /**
     * 取消所有任务
     */
    public void cancelAllTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            AsyncTask task = tasks.get(i);
            task.cancel(false);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
