package org.zackratos.shanbaywork.loadimage;

import android.content.Context;
import android.widget.ImageView;

import org.zackratos.shanbaywork.R;
import org.zackratos.shanbaywork.loadimage.imageloader.RxImageLoader;

/**
 * Created by Administrator on 2017/8/9.
 */

public class RxLoaderAdapter extends ImageAdapter {

    public RxLoaderAdapter(Context contexts, String[] images) {
        super(contexts, images);
    }

    @Override
    protected void showImage(ImageViewHolder holder, int position) {

        RxImageLoader.with(context)
                .load(images[position])
                .error(R.drawable.image_default)
                .into((ImageView) holder.itemView);
    }
}
