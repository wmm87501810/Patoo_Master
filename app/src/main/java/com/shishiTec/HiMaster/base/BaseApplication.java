package com.shishiTec.HiMaster.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.igexin.sdk.PushManager;
import com.shishiTec.HiMaster.Model.bean.EmojiBean;
import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.GlideCircleTransform;
import com.shishiTec.HiMaster.UI.Views.GlideRoundTransform;
import com.shishiTec.HiMaster.Utils.*;

import org.xutils.x;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Pursue on 16/1/5.
 */
public class BaseApplication extends Application{
    private static BaseApplication instance;
    private Glide glide;
    public static List<List<EmojiBean>> emojiBeans;//表情集合
    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        x.Ext.init(this);//初始化xUtils框架
        ShareSDK.initSDK(this);
        //注册推送
        PushManager.getInstance().initialize(this);


        AppContextUtil.init(this);
        initDevice();
        glide = Glide.get(this);
        getLocation();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());
    }


    //初始化设备信息
    private void initDevice() {
        int widthsize = DimenUtils.getwidthsize(this);
        int highsize = DimenUtils.gethighsize(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(widthsize);
        stringBuilder.append(" x ");
        stringBuilder.append(highsize);
        String screen = stringBuilder.toString();
        SharedPreferencesUtil.getInstance().saveScreen(screen);
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    //获得当前坐标
    public void getLocation() {
        final LocationUtil locUtil = new LocationUtil();
        locUtil.getLocation(this, new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                } else {
                    // latitude纬度 longitude经度
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    SharedPreferencesUtil.getInstance().saveLatitude(latitude+"");
                    SharedPreferencesUtil.getInstance().saveLongitude(longitude+"");
                    locUtil.stop();
                }
            }
        });
    }

    /**
     * 加载网络图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadRoundImageView(Context context,ImageView imageView,String imageUrl) {
        glide.with(context)
                .load(getImgUrl(imageUrl))
                .transform(new GlideRoundTransform(this,3))
//                .bitmapTransform(new RoundedCornersTransformation(this,100,10, RoundedCornersTransformation.CornerType.ALL))//自定义Transformation,加载圆角图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸又缓存其他尺寸
//                .thumbnail(0.1f)//缩略图
                .placeholder(R.mipmap.xiaotouxiang)//占位符
                .error(R.mipmap.xiaotouxiang)//错误占位符
                .crossFade()//图片的淡入淡出动画效果
                .into(imageView);
    }

    /**
     * 加载网络图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadImageView(Context context,ImageView imageView,String imageUrl) {
        glide.with(context)
                .load(getImgUrl(imageUrl))
                .transform(new GlideRoundTransform(this,3))
//                .bitmapTransform(new RoundedCornersTransformation(this,100,10, RoundedCornersTransformation.CornerType.ALL))//自定义Transformation,加载圆角图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸又缓存其他尺寸
//                .thumbnail(0.1f)//缩略图
                .placeholder(R.mipmap.xiaotouxiang)//占位符
                .error(R.mipmap.xiaotouxiang)//错误占位符
                .crossFade()//图片的淡入淡出动画效果
                .into(imageView);
    }


    /**
     * 加载方形网络图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadImageALLView(Context context,ImageView imageView,String imageUrl) {
        glide.with(context)
                .load(getImgUrl(imageUrl))
//                .transform(new GlideRoundTransform(this,3))
                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸又缓存其他尺寸
//                .thumbnail(0.1f)//缩略图
                .placeholder(R.mipmap.jiazanshibaitu)//占位符
                .error(R.mipmap.jiazanshibaitu)//错误占位符
                .crossFade()//图片的淡入淡出动画效果
                .into(imageView);
    }

    /**
     *加载圆形图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadCircleImageView(Context context,ImageView imageView,String imageUrl){
        glide.with(context)
                .load(getImgUrl(imageUrl))
                .bitmapTransform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸又缓存其他尺寸
//                .thumbnail(0.1f)//缩略图
                .placeholder(R.mipmap.xiaotouxiang)//占位符
                .error(R.mipmap.xiaotouxiang)//错误占位符
                .crossFade()//图片的淡入淡出动画效果
                .into(imageView);
    }
    /**
     * 加载本地图片
     * @param context
     * @param imageView
     * @param imageId
     */
    public void loadFromDrawable(Context context,ImageView imageView,int imageId){
        glide.with(context)
                .load(imageId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸又缓存其他尺寸
                .crossFade()//图片的淡入淡出动画效果
                .into(imageView);
    }

    public String getImgUrl(String url){
        if(url!=null && url.startsWith("http")){
            return url;
        }
        return ApiContact.IMAGE_URL2+url;
    }


}
