package com.shishiTec.HiMaster.Net;

import com.shishiTec.HiMaster.Net.cookie.CookieJarImpl;
import com.shishiTec.HiMaster.Net.cookie.store.MemoryCookieStore;
import com.shishiTec.HiMaster.Utils.NetUtils;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pursue on 16/3/24.
 */
public class RetrofitManager {
    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private final Retrofit retrofit;
    private static RetrofitManager mInstance;

    public static RetrofitManager getmInstance(){
        if (mInstance == null){
            synchronized (RetrofitManager.class){
                if (mInstance == null) mInstance = new RetrofitManager();
            }
        }
        return mInstance;
    }

    public RetrofitManager() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(BaseApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
        //OkHttp初始化
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(loggingInterceptor)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        //retrofit配置
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiContact.BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .addConverterFactory(GsonConverterFactory.create())//添加 Gson 转换器
                .build();
    }
    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkConnected()) {
                request = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isNetworkConnected()) {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .header("Content-Type", "application/json")
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", CACHE_CONTROL_CACHE)
                        .header("Content-Type", "application/json")
                        .removeHeader("Pragma").build();
            }
        }
    };


    public ApiService createService(){
        return retrofit.create(ApiService.class);
    }

    

}
