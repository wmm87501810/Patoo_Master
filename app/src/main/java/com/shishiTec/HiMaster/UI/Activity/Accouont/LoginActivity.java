package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;

import com.igexin.sdk.PushManager;
import com.mob.tools.utils.UIHandler;
import com.shishiTec.HiMaster.Model.bean.RegisterAndLoginBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.AppUtils;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements PlatformActionListener, Handler.Callback {

    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.user_password)
    EditText userPassword;
    @Bind(R.id.qq)
    Button qq;
    @Bind(R.id.wechat)
    Button wechat;
    @Bind(R.id.sina)
    Button sina;
    @Bind(R.id.phone)
    Button phone;
    @Bind(R.id.exit)
    Button exit;


    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    /**
     * shareSdk第三方登录
     *
     * @param platName
     */
    public void authorize(String platName) {
        Platform plat = ShareSDK.getPlatform(this, platName);
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);
//        plat.authorize();
    }

    private String actionToString(int action) {

        switch (action) {
            case Platform.ACTION_AUTHORIZING:
                return "ACTION_AUTHORIZING";
            case Platform.ACTION_GETTING_FRIEND_LIST:
                return "ACTION_GETTING_FRIEND_LIST";
            case Platform.ACTION_FOLLOWING_USER:
                return "ACTION_FOLLOWING_USER";
            case Platform.ACTION_SENDING_DIRECT_MESSAGE:
                return "ACTION_SENDING_DIRECT_MESSAGE";
            case Platform.ACTION_TIMELINE:
                return "ACTION_TIMELINE";
            case Platform.ACTION_USER_INFOR:
                return "ACTION_USER_INFOR";
            case Platform.ACTION_SHARE:
                return "ACTION_SHARE";
            default: {
                return "UNKNOWN";
            }
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        Platform plat = (Platform) msg.obj;
        String text = actionToString(msg.arg2);
        switch (msg.arg1) {
            case 1: {
//                if (thirdName!=null&&nikename!=null&&img_top!=null){
//                }
            }
            break;
            case 2: {
                // 失败
                text = plat.getName() + " caught error";
            }
            break;
            case 3: {
                // 取消
                text = plat.getName() + " authorization canceled";
            }
            break;
        }
        return false;

    }


    @OnClick(R.id.qq) void qqLogin(){
        authorize(QQ.NAME);
    }

    @OnClick(R.id.wechat) void weChatLogin(){
        authorize(Wechat.NAME);
    }

    @OnClick(R.id.sina) void sinaLogin(){
        authorize(SinaWeibo.NAME);
    }

    @OnClick(R.id.phone)void phoneLogin(){
        login();
    }

    @OnClick(R.id.exit)void exitLogin(){
        exit();
    }

    private void exit() {
        Map<String, String> exitMap = new HashMap<>();
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(exitMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService().exit(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            SharedPreferencesUtil.getInstance().clearUserInfo();
                        }
                    }
                });
    }

    private void login() {
        final String clientId = PushManager.getInstance().getClientid(this);
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("mobile", userName.getText().toString());
        loginMap.put("password", AppUtils.getMD5(userPassword.getText().toString().trim()));
        loginMap.put("clientId", PushManager.getInstance().getClientid(this));
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken("a83e3106e998db1d626cb15f7223246a");
        BaseParams params = new BaseParams(deviceParams);
        params.setData(loginMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .Login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<RegisterAndLoginBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<RegisterAndLoginBean> registBeanBaseModel) {
                        if (registBeanBaseModel.getCode() == 200) {
                            RegisterAndLoginBean data = registBeanBaseModel.getData();
//                            SharedPreferencesUtil.getInstance().saveLoginInfo(data.data.mobile, userPassword.toString().trim(), data.data.uid,data.data.token,"localLogin",data.data.identity);
//                            SharedPreferencesUtil.getInstance().saveNikeName(data.data.nickname);
//                            SharedPreferencesUtil.getInstance().saveImgTop(data.data.img_top);
                            ToastUtils.showGravity(LoginActivity.this, "登录成功"+clientId, Gravity.CENTER, 20, 200);
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);


    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

}
