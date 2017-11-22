package com.shishiTec.HiMaster.UI.Views;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.shishiTec.HiMaster.R;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Pursue on 16/1/13.
 */
public class GlideConfigureModule implements GlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        ViewTarget.setTagId(R.id.image_tag);
        int memoryCache = (int) (Runtime.getRuntime().maxMemory() / 8);//应用可用内存的1/8
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)//设置图片格式
                .setMemoryCache(new LruResourceCache(memoryCache))
                .setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()))
                .setDiskCache(new DiskCache.Factory() {
                    @Override
                    public DiskCache build() {
                        File cacheDirectory;
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && hasExternalStoragePermission(context)) {
                            // 如果SD卡可以用的话把图片缓存到SD卡上
                            cacheDirectory = new File(context.getExternalCacheDir(), Environment.getExternalStorageDirectory()
                                    .getAbsolutePath()
                                    + File.separator+"imageCache"+File.separator+"master");
                        } else {
                            // 把图片缓存到应用data/data/包/...下
                            cacheDirectory = context.getCacheDir();
                        }
                        return DiskLruCacheWrapper.get(cacheDirectory, 100 * 1024 * 1024);
                    }
                });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }
}
