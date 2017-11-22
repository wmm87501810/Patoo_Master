package com.shishiTec.HiMaster.getui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pursue on 2015/3/16.
 */
public class PushMasterReceiver extends BroadcastReceiver{
    private String cid;
    private Context context;
    private Gson gson = new Gson();

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            /**
             * 得到当前手机的clientID
             */
            case PushConsts.GET_CLIENTID:
//                cid = bundle.getString("clientid");
//                SharedPreferencesUtil.getInstance().putString("clientid",cid);
//                Log.e("master", cid);
                break;
            case PushConsts.GET_MSG_DATA:
                // 获取透传（payload）数据
                /**
                 * 1：自己被关注 跳转到我的个人中心
                 2：发布课程 跳转到课程详情
                 3：评论，回复 跳转到课程评论列表
                 4：推送达人 跳转到达人详情
                 5：推送达人集合  跳转到达人集合界面
                 6：推送课程集合 跳转到课程集合界面
                 7：
                 9：达人课程用户报名  跳转到课程详情的订单界面
                 10：用户订单使用  跳转到我的个人中心我的订单
                 11：私信  私信聊天界面
                 15：
                 16:订单已使用
                 17:web页面跳转
                 18：关键字搜索
                 21:完成订单弹窗
                 * @param context
                 */
//                byte[] payloads = bundle.getByteArray("payload");
//
//                String taskid = bundle.getString("taskid");
//                String messageid = bundle.getString("messageid");
//
//                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
//                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
//                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                byte[] payload = bundle.getByteArray("payload");
                if (payload != null)
                {
                    String data = new String(payload);
                    HashMap<String,Object> pushmMap = null;
                    try {
                        pushmMap = gson.fromJson(data, HashMap.class);
                        Log.d("getuidata",data);
                        final String tag = (String)pushmMap.get("tag");
                        BaseApplication application = BaseApplication.getInstance() ;
                        if(application!=null){
//                            if(tag!=null && tag.length()>0){
//                                application.getDatabaseHelper().insertAppLog("6", tag);
//                            }else{
//                                application.getDatabaseHelper().insertAppLog("6", "0");
//                            }
                        }

                        Intent startIntent = null;
                        if (tag!=null){
                            if (tag.equalsIgnoreCase("1")){//用户关注
//                                if (SharedPreferencesUtil.getInstance().isLogin()) {
//                                    startIntent=new Intent(context,MainActivity.class);
//                                    startIntent.putExtra("isNotification", true);
//                                    startIntent.putExtra("isAttention", true);
//                                }
                            }else if (tag.equalsIgnoreCase("2")){//发布课程
//                                startIntent=new Intent(context,CoursesDetailActivity.class);
//                                startIntent.putExtra("cid",(String)pushmMap.get("cid"));
                            }else if (tag.equalsIgnoreCase("3")){//评论，回复
//                                startIntent=new Intent(context,CommentListActivity.class);
//                                startIntent.putExtra("cid",(String)pushmMap.get("cid"));
//                                startIntent.putExtra("title",(String)pushmMap.get("title"));
//                                startIntent.putExtra("currentTime",(String)pushmMap.get("currentTime"));
                            }else if (tag.equalsIgnoreCase("4")){//推送达人
//                                startIntent=new Intent(context,OtherUserCenterActivity.class);
//                                startIntent.putExtra("fid",(String)pushmMap.get("uid"));
                            }else if (tag.equalsIgnoreCase("5")){//推送达人集合
//                                startIntent=new Intent(context,MasterCollectionActivity.class);
//                                startIntent.putExtra("cardId",(String)pushmMap.get("cardId"));
                            }else if (tag.equalsIgnoreCase("6")){//推送课程集合
//                                startIntent=new Intent(context,CourseCollectionActivity.class);
//                                startIntent.putExtra("cardId",(String)pushmMap.get("cardId"));
                            }else if (tag.equals("7")){
//                                startIntent = new Intent(context,MainActivity.class);
                            }
                            else if (tag.equalsIgnoreCase("9")){//达人课程用户报名
//                                if (SharedPreferencesUtil.getInstance().isLogin()) {
//                                    startIntent = new Intent(context, MasterOrderManagerActivity.class);
//                                }
                            }else if (tag.equalsIgnoreCase("10")){//订单使用
//                                if (SharedPreferencesUtil.getInstance().isLogin()) {
//                                    startIntent=new Intent(context,AllOrderStateActivity.class);
//                                }
                            }else if (tag.equalsIgnoreCase("11")){//私信
//                                if (SharedPreferencesUtil.getInstance().isLogin()) {
//                                    startIntent=new Intent(context,PrivateLetterActivity.class);
//                                    startIntent.putExtra("isNotification",true);
//                                    startIntent.putExtra("acceptUid",(String)pushmMap.get("uid"));
//                                    startIntent.putExtra("addtime", (String) pushmMap.get("addtime"));
//                                }
                            }else if (tag.equals("17")){
//                                startIntent = new Intent(context, WebViewActivity.class);
//                                startIntent.putExtra("url","jumpUrl");
//                                startIntent.putExtra("notificationUrl",(String)pushmMap.get("content"));
                            }else if (tag.equals("18")){
//                                startIntent = new Intent(context, SearchActivity.class);
//                                startIntent.putExtra("key",(String)pushmMap.get("content"));
                            }else if (tag.equals("21")){
//                                LinkedTreeMap oid = (LinkedTreeMap) pushmMap.get("oid");
//                                final String title = (String) oid.get("title");
//                                final String id = (String) oid.get("id");
//                                final String cid = (String) oid.get("cid");
////                                Gson gson = new Gson();
//                                final CustomDialog customDialog = new CustomDialog(getGlobleActivity(),true,false);
//                                customDialog.setCanceledOnTouchOutside(false);
//                                customDialog.setTitle("课后评分",true);
//                                customDialog.setMessage("你已经上完Master达人的兴趣课程,感觉怎么样?", true);
//                                customDialog.setContent("留下你的课程感受吧,对他人帮助很大哦!", true);
//                                customDialog.setNegativeButton("取消", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        customDialog.dismiss();
//                                    }
//                                });
//                                customDialog.setPositiveButton("打个分吧", new View.OnClickListener() {
//                                    @TargetApi(Build.VERSION_CODES.KITKAT)
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = null;
//                                        try {
//                                            intent = new Intent(getGlobleActivity(), GradeActivity.class);
//                                            intent.putExtra("orderId", id);
//                                            intent.putExtra("cid", cid);
//                                            intent.putExtra("title", title);
//                                            getGlobleActivity().startActivity(intent);
//                                            customDialog.dismiss();
//                                        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException e) {
//                                            e.printStackTrace();
//                                        } catch (InvocationTargetException e) {
//                                            e.printStackTrace();
//                                        } catch (NoSuchFieldException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                                customDialog.show();
//                                break;
                            }
                        }
                        if(startIntent!=null){
                            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(startIntent);
                        }else{
                            startApp(context);
                        }
                    }catch (Exception e){
                        startApp(context);
                    }
                    Log.d("GetuiSdkDemo", "Got Payload:" + data);
                    // TODO:接收处理透传（payload）数据
                }
                break;
            default:
                break;
        }
    }
    //获得当前运行的activity对象
    public static Activity getGlobleActivity() throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException{
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
        activitiesField.setAccessible(true);
        Map activities = (Map) activitiesField.get(activityThread);
        for(Object activityRecord:activities.values()){
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if(!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                return activity;
            }
        }
        return null;
    }


    private void startApp(Context context){
//        if (isAppAlive(context,"com.shishiTec.HiMaster")){
//
//        }
        if (isBackground(context)) {
            Intent mainIntent = context.getPackageManager().getLaunchIntentForPackage("com.shishiTec.HiMaster");
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(mainIntent);
        }
    }
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
}
