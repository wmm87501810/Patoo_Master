package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.shishiTec.HiMaster.Model.bean.SoyCardBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.MyCenterBean;
import com.shishiTec.HiMaster.Model.realbean.MyShequBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Discover;
import com.shishiTec.HiMaster.UI.Activity.UserCenter.MyCardActivity;
import com.shishiTec.HiMaster.UI.Adapter.mycenter.PhotoAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.SoyCardAdapter;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.shishiTec.HiMaster.R.id.recyclerView;

/**
 * Created by 83802 on 2017/8/14.
 */

public class MyCenter extends BaseActivity {
    @Bind(R.id.rv_photo_master)
    RecyclerView rvPhotoMaster;
    @Bind(R.id.al_photo_master)
    AutoLinearLayout alPhotoMaster;
    @Bind(R.id.bt_updata)
    Button btUpdata;
    private MyCenterBean data = null;
    private List<String> list_url;
    SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance();


    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCenter.this, UpDataMyCenter.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取信息
        getMyCenterInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_center;
    }

    //获取个人详情
    private void getMyCenterInfo() {
        Map<String, String> getMyCenterMap = new HashMap<>();
        getMyCenterMap.put("type", "1");
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setData(getMyCenterMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");

        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .getMyCenter(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MyCenterBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int a = 0;
                    }

                    @Override
                    public void onNext(BaseModel<MyCenterBean> myCenterBeanBaseModel) {
                        if (myCenterBeanBaseModel.getCode() == 1004) {
                            //没有登录
                            startActivity(new Intent(MyCenter.this, RegisterActivity.class));
                            finish();
                        } else if (myCenterBeanBaseModel.getCode() == 200) {
                            //热门达人
                            if (myCenterBeanBaseModel.getData().getImg_0() == null ||
                                    myCenterBeanBaseModel.getData().getImg_0().equals("")) {
                                alPhotoMaster.setVisibility(View.GONE);
                            } else {
                                list_url.add(myCenterBeanBaseModel.getData().getImg_0());
                                list_url.add(myCenterBeanBaseModel.getData().getImg_1());
                                list_url.add(myCenterBeanBaseModel.getData().getImg_2());
                                list_url.add(myCenterBeanBaseModel.getData().getImg_3());
                                list_url.add(myCenterBeanBaseModel.getData().getImg_4());

                                PhotoAdapter photoAdapter = new PhotoAdapter(MyCenter.this, list_url);
                                rvPhotoMaster.setAdapter(photoAdapter);
                            }
                        }
                    }
                });
        addSubscription(subscribe);

    }

    private void initRecyclerView() {
        LinearLayoutManager hotManager = new LinearLayoutManager(this);
        hotManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //我的照片
        rvPhotoMaster.setLayoutManager(hotManager);
        rvPhotoMaster.setItemAnimator(new DefaultItemAnimator());
    }





}
