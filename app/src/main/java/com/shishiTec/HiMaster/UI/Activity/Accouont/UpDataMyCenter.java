package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.content.Intent;

import com.shishiTec.HiMaster.Model.realbean.MyCenterBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 83802 on 2017/8/14.
 */

public class UpDataMyCenter extends BaseActivity {
    private MyCenterBean data = null;


    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_updata_mycenter;
    }

//    //获取个人详情
//    private void getMyCenterInfo() {
//        Map<String, String> getMyCenterMap = new HashMap<>();
//        getMyCenterMap.put("type", "1");
//        DeviceParams device = new DeviceParams();
//        BaseParams params = new BaseParams(device);
//        params.setDevice(device);
//        params.setData(getMyCenterMap);
//        params.setSign(params.paramsSign());
//        params.setRest_version("3.0");
//
//        Subscription subscribe = RetrofitManager.getmInstance().createService()
//                .getMyCenter(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseModel<MyCenterBean>>() {
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                       int a =0 ;
//                    }
//
//                    @Override
//                    public void onNext(BaseModel<MyCenterBean> myCenterBeanBaseModel) {
//                        if(myCenterBeanBaseModel.getCode() == 1004){
//                            //没有登录
//                            startActivity(new Intent(UpDataMyCenter.this,RegisterActivity.class));
//                            finish();
//                        }
//                        else if (myCenterBeanBaseModel.getCode() == 200) {
//                            data =  myCenterBeanBaseModel.getData();
//                        }
//                    }
//                });
//        addSubscription(subscribe);

//    }
}
