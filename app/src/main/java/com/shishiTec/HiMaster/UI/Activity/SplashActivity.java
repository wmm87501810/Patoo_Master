package com.shishiTec.HiMaster.UI.Activity;

import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.AutoLoginBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.show)
    ImageView show;


    //检查是否第一次打开
    private void checkIsFirstUse() {

            SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();
            if (!instance.isFirstUse())
            {
                //第一次打開APP
                instance.writeFirstUse();
                //startActivity(new Intent(SplashActivity.this,MainActivity.class));
                gotoLeaderActivity();

            } else {
                //startActivity(new Intent(SplashActivity.this,MainActivity.class));
                //gotoMainActivity();
                //第一次登录
                if (SharedPreferencesUtil.getInstance().isLogin()) {
                    autoLogin();
                } else {
                    gotoMainActivity();
                }
            }

    }

    //自动登录
    private void autoLogin() {
//        String loginToken;
//        String loginStyle = SharedPreferencesUtil.getInstance().getLoginStyle();
//        String username = SharedPreferencesUtil.getInstance().getUsername();
//        String password = SharedPreferencesUtil.getInstance().getPassword();
//        String token = SharedPreferencesUtil.getInstance().getToken();
//        if (token!=null){
//             loginToken = token;
//        }else {
//            loginToken = "";
//        }
//        if (loginStyle.equals("localLogin")){
//            phoneLogin(loginToken,username,password);
//        }else if (loginStyle.equals(QQ.NAME)){
//
//        }else if (loginStyle.equals(Wechat.NAME)){
//
//        }else if (loginStyle.equals(SinaWeibo.NAME)){
//
//        }
        Map<String,String> loginMap = new HashMap<>();
        loginMap.put("mobile",SharedPreferencesUtil.getInstance().getMobilenum());
        loginMap.put("security_code",SharedPreferencesUtil.getInstance().getSecurity_code().equals("")?
                "0":SharedPreferencesUtil.getInstance().getSecurity_code());
        loginMap.put("clientId", PushManager.getInstance().getClientid(this));
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .autoLogin(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<AutoLoginBean>>() {
                    @Override
                    public void onCompleted() {
                        gotoMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(SplashActivity.this, "自动登录失败", Gravity.CENTER, 0, 0);
                       // gotoMainActivity();
                    }

                    @Override
                    public void onNext(BaseModel<AutoLoginBean> autoLoginBean) {
                        if (autoLoginBean.getCode() == 200) {
//                            RegisterAndLoginBean data = autoLoginBean.getData();
//                            SharedPreferencesUtil.getInstance().saveLoginInfo("localLogin", data.getMobile(), password, data.getUid(), data.getToken(),data.getIdentity());
//                            gotoMainActivity();
                            ToastUtils.showGravity(SplashActivity.this, "自动登录成功", Gravity.CENTER, 0, 0);
                            startActivity(new Intent(SplashActivity.this,MianUIActivity.class));
                        }
                    }
                });
        addSubscription(subscribe);

    }

    /**
     * 手机号码登录
     * @param loginToken
     * @param username
     * @param password
     */
    private void phoneLogin(String loginToken, String username, final String password) {
//        Map<String,String> loginMap = new HashMap<>();
//        loginMap.put("mobile",username);
//        loginMap.put("password", AppUtils.getMD5(password));
//        DeviceParams deviceParams = new DeviceParams();
//        deviceParams.setToken(loginToken);
//        BaseParams params = new BaseParams(deviceParams);
//        params.setDevice(deviceParams);
//        params.setData(loginMap);
//        params.setRest_version("3.0");
//        params.setSign(params.paramsSign());
//        Subscription subscribe = RetrofitManager.getmInstance().createService()
//                .Login(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseModel<RegisterAndLoginBean>>() {
//                    @Override
//                    public void onCompleted() {
//                        gotoMainActivity();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        ToastUtils.showGravity(SplashActivity.this, "登录失败", Gravity.CENTER, 0, 0);
//                        gotoMainActivity();
//                    }
//
//                    @Override
//                    public void onNext(BaseModel<RegisterAndLoginBean> registerAndLoginBeanBaseModel) {
//                        if (registerAndLoginBeanBaseModel.getCode() == 200) {
//                            RegisterAndLoginBean data = registerAndLoginBeanBaseModel.getData();
//                            SharedPreferencesUtil.getInstance().saveLoginInfo("localLogin", data.getMobile(), password, data.getUid(), data.getToken(),data.getIdentity());
//                            gotoMainActivity();
//                        }
//                    }
//                });
//        addSubscription(subscribe);
    }

    private void gotoMainActivity(){
        startActivity(new Intent(SplashActivity.this,MianUIActivity.class));
    }
    private void gotoLeaderActivity(){
        startActivity(new Intent(SplashActivity.this,LeaderActivity.class));
        finish();
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        //全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIsFirstUse();
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}
