package com.shishiTec.HiMaster.Utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.R;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

/**
 * Created by kc11 on 2015/11/27.
 */
public class xImage {
    static ImageOptions imageOptions = null;

    public static ImageOptions init() {
        if (imageOptions == null) {
            imageOptions = new ImageOptions.Builder()
//                    .setRadius(Utils.dip2px(5))  是否圆角
                    .setCrop(true)
//                    .setIgnoreGif(false)//是否支持显示gif图 false 显示
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.default_image)//加载失败的图片
                    .build();
        }

        return imageOptions;
    }

    /**
     * 加载网络图片
     * @param view ImageView控件
     * @param url 网络图片地址 / 图片本地地址 /支持直接填写本地图片地址
     */
    public static void setImage(ImageView view, String url) {
        x.image().bind(view, ApiContact.IMAGE_URL+url, init());
    }

    /**
     * 加载网络图片
     * @param view ImageView控件
     * @param url 网络图片地址 / 图片本地地址 /支持直接填写本地图片地址
     */
    public static void setImage(ImageView view, String url, final Callback.CommonCallback<Drawable> callback) {
        x.image().bind(view, ApiContact.IMAGE_URL + url, init(), new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {// 加载成功
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {//加载失败
                callback.onError(ex,isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {//加载取消
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {//加载结束 完毕
                callback.onFinished();
            }
        });
    }
    /**
     * 下载网络图片  并显示
     * @param url 网络图片地址
     */
    public static void setImage(String url, final ImageView imageView) {
        x.image().loadFile(url, init(), new Callback.CacheCallback<File>() {
            @Override
            public boolean onCache(File file) {//这个方法  如果当前下载 并且加载的图片是已经下载好的  那么直接会从缓存中读取图片 并进行加载
                setImage(imageView,file.getPath());//加载图片
                return false;
            }

            @Override
            public void onSuccess(File file) {
                setImage(imageView,file.getPath());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 加载网络Gif图片
     * @param view ImageView控件
     * @param url 网络图片地址
     */
    public static void setGIFImage(ImageView view, String url) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.MATRIX).build();
        x.image().bind(view, url, imageOptions);

    }

    /**清理图片缓存*/
    public static void clearxImageCache() {
        x.image().clearCacheFiles();

    }
}
