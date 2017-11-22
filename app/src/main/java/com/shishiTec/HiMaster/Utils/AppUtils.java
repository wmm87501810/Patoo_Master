package com.shishiTec.HiMaster.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.gson.Gson;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pursue on 16/4/14.
 */
public class AppUtils {
    private static final String TAG = "AppUtil";
    public static String cardName;
    /**
     * 获得软件版本号
     */
    public static String GetVersionCode() {
        String version = "1.0.0";
        PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
            LogUtil.e(TAG,e.getMessage());
        }
        LogUtil.d(TAG,"versionName="+version);
        return version;
    }

    /*
    获得devicetoken
     */
    public static String getDeviceToken(Context context){
        String devicetoken;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        devicetoken = tm.getDeviceId();

        return devicetoken;
    }
    /**
     * 获得软件名称
     *
     * @return
     */
    public static String GetVersionName() {
        String versionName = "1.0.0";
        PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return versionName;
    }

    /**
     * 获取网络是否可用状态
     *
     * @return
     */
    public static boolean networkIsAvailable() {

        ConnectivityManager cManager = (ConnectivityManager) BaseApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        if (info.isConnected()) {
            return true;
        }
        return false;
    }

    public static int getImageWidth(String imageUrl) {
        int width = 600;
        if (imageUrl != null) {
            String imageName = imageUrl
                    .substring(imageUrl.lastIndexOf("/") + 1);
            String[] array = imageName.split("_");
            if (array.length == 3) {
                width = Integer.parseInt(array[0]);
            }
        }
        return width;
    }

    public static int getImageHeight(String imageUrl) {
        int height = 600;
        if (imageUrl != null) {
            String imageName = imageUrl
                    .substring(imageUrl.lastIndexOf("/") + 1);
            String[] array = imageName.split("_");
            if (array.length == 3) {
                height = Integer.parseInt(array[1]);
            }
        }
        return height;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return boolean
     */
    public static boolean isMobileNO(String mobiles) {
        if (mobiles.startsWith("1")&&mobiles.length()==11){
            return true;
        }else {
            return false;
        }
//        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//        if (TextUtils.isEmpty(mobiles))
//            return false;
//        else
//            return mobiles.matches(telRegex);
    }

    /**
     * 验证密码格式
     *
     * @param password
     * @return boolean
     */
    public static boolean isMobilePwd(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        else if (password.length() >= 6 && password.length() <= 32)
            return true;
        else
            return false;
    }

    /**
     * getMD5 加密 (这里描述这个方法适用条件 – 可选)
     *
     * @param content
     * @return String
     * @throws
     * @since 1.0.0
     */
    public static String getMD5(String content) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(content.getBytes());
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    //Des加密
    public static String getDESString(Object object){
        String DES_PRIKEY = "gomaster";
        Gson gson = new Gson();
        String result = null;
        if (null != object){
            try{
                String gsonStr = gson.toJson(object);
                result = DES.encryptDES(gsonStr, DES_PRIKEY);
            }catch (Exception e){
                LogUtil.e(TAG, "DES 加密失败", e);
            }
        }
        return result;
    }
    //Des解密
    public static String getDecryptString(String gsonStr){
        String DES_PRIKEY = "gomaster";
        String result = null;
        if (null != gsonStr){
            try{
                result = DES.decryptDES(gsonStr, DES_PRIKEY);
            }catch (Exception e){
                LogUtil.e(TAG, "DES 解密失败", e);
            }
        }
        return result;
    }
    /**
     * 用户登录成功消除页面
     *
     * @param
     * @throws
     * @since 1.0.0
     */
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }


    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void startAnimation(int index,Context context,View view){
        Animation animation = null;
        if (index==1){
            animation = AnimationUtils.loadAnimation(context, R.anim.other_choose_left_toright);
        }else {
            animation = AnimationUtils.loadAnimation(context,R.anim.other_choose_right_toleft);
        }
        view.setAnimation(animation);
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        view.startAnimation(animation);
    }

}
