package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.zackratos.shanbaywork.R;
import org.zackratos.shanbaywork.loadimage.imageloader.BitmapUtils;
import org.zackratos.shanbaywork.loadimage.rximageloader.ImageApi;

/**
 * Created by Administrator on 2017/8/9.
 */

public class GlideAdapter extends ImageAdapter {


    public GlideAdapter(Context contexts, String[] images) {
        super(contexts, images);
    }


    @Override
    protected void showImage(ImageViewHolder holder, int position) {
        new BitmapUtils(context).disPlay((ImageView) holder.itemView, images[position]);
/*        Glide.with(context)
                .load(ImageApi.BASE_URL + "media/media_store/image/" + images[position])
                .error(R.drawable.image_default)
                .into((ImageView) holder.itemView);*/

    }
}
