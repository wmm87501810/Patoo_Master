package com.shishiTec.HiMaster.UI.Activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.UserdetailsInfo;
import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.BannerView;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 83802 on 2017/8/16.
 */

public class DiscoverUserDetailsInfo extends BaseActivity {


    @Bind(R.id.banner)
    BannerView banner;
    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.age)
    TextView age;
    @Bind(R.id.province)
    TextView province;
    @Bind(R.id.like_you)
    TextView likeYou;
    @Bind(R.id.ranking)
    TextView ranking;
    @Bind(R.id.show_next)
    ImageView showNext;
    @Bind(R.id.my_sign)
    TextView mySign;
    @Bind(R.id.rv_hot_master)
    RecyclerView rvHotMaster;
    private Context context;
    private String uid = "";
    private List<View> viewList;
    private List<String> imgs = null;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        uid = getIntent().getStringExtra("uid");
        context = this;
        getUserDetailinfo();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_discoveruser;
    }

    private void getUserDetailinfo() {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("uid", uid);
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken("");
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .getUserDetailinfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<UserdetailsInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<UserdetailsInfo> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            //轮播数据
                            initBanner(listBaseModel);
                            //卡片展示
                            initDetail(listBaseModel);
                            //她的玩具(没做呢)
                            listBaseModel.getData().getToys_list();
                        }
                    }

                });
        addSubscription(subscription);

    }

    private void initDetail(BaseModel<UserdetailsInfo> listBaseModel) {
        UserdetailsInfo.Detail info = listBaseModel.getData().getDetail();
        nickname.setText(info.getNickname());
        age.setText(info.getAge());
        province.setText(info.getProvince());
        likeYou.setText(info.getLike_you());
        ranking.setText(info.getRanking());
        mySign.setText(info.getSignature());
    }

    private void initBanner(BaseModel<UserdetailsInfo> listBaseModel) {
        viewList = new ArrayList<View>();
        List<String> imgs = getImgs(listBaseModel);
        for (int i = 0; i < imgs.size(); i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            BaseApplication.getInstance().loadImageALLView(context, image, imgs.get(i));
            viewList.add(image);
        }
        banner.startLoop(true);
        banner.setViewList(viewList);
    }

    public List<String> getImgs(BaseModel<UserdetailsInfo> listBaseModel) {
        imgs = new ArrayList<>(5);
        imgs.add(ApiContact.IMAGE_URL2 + listBaseModel.getData().getImg().getImg_0());
        imgs.add(ApiContact.IMAGE_URL2 + listBaseModel.getData().getImg().getImg_1());
        imgs.add(ApiContact.IMAGE_URL2 + listBaseModel.getData().getImg().getImg_2());
        imgs.add(ApiContact.IMAGE_URL2 + listBaseModel.getData().getImg().getImg_3());
        imgs.add(ApiContact.IMAGE_URL2 + listBaseModel.getData().getImg().getImg_4());
        return imgs;
    }

}
