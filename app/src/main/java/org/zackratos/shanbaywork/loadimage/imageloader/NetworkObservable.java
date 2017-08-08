package org.zackratos.shanbaywork.loadimage.imageloader;

/**
 * Created by Administrator on 2017/8/7.
 */

public class NetworkObservable extends CacheObservable {
    @Override
    public Image getImageFromCache(ImageInfo imageInfo) {
        return null;
    }

    @Override
    public void putImageToCache(Image image) {

    }
}
