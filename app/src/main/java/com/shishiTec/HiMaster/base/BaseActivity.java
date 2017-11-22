package com.shishiTec.HiMaster.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import android.view.MenuItem;
import com.igexin.sdk.PushManager;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Pursue on 16/1/5.
 */
public abstract class BaseActivity extends AutoLayoutActivity{
    private CompositeSubscription mCompositeSubscription;
    public BaseApplication baseApplication;
    public LinearLayoutManager linearLayoutManager;
    public DeviceParams device;
    public BaseParams params;
    public Boolean pessmion_on = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        //requestPermissions();
        PushManager.getInstance().initialize(this);
        int resId = getLayoutId();
        if(resId!=0){
            setContentView(resId);
        }
        ButterKnife.bind(this);
        baseApplication = new BaseApplication();
        linearLayoutManager = new LinearLayoutManager(BaseActivity.this);
        initDeviceData();
        initViews();
        initListener();
    }

    private void checkPermission() {
        List<String> permissionList = new ArrayList<>();
        Collections.addAll(permissionList, getPermissions());
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.SEND_SMS);
        permissionList.add(Manifest.permission.RECEIVE_SMS);
        permissionList.add(Manifest.permission.READ_SMS);
        permissionList.add(Manifest.permission.RECEIVE_WAP_PUSH);
        permissionList.add(Manifest.permission.RECEIVE_MMS);
        permissionList.add(Manifest.permission.WRITE_CONTACTS);
        permissionList.add(Manifest.permission.READ_CONTACTS);
        permissionList.add(Manifest.permission.GET_ACCOUNTS);
        permissionList.add(Manifest.permission.WRITE_SETTINGS);
        //android.permission.WRITE_SETTINGS
        RxPermissions.getInstance(this).request(permissionList.toArray(new String[permissionList.size()])).
                subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            pessmion_on = false;
                        } else {
                            pessmion_on = true;
                        }
                    }
                });
    }

    public abstract String[] getPermissions();



    //初始化设备信息
    private void initDeviceData(){
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    protected abstract void initViews();

    protected void initListener() {}

    public abstract int getLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
