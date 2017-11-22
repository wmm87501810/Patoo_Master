package com.shishiTec.HiMaster.UI.Activity.mall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shishiTec.HiMaster.Model.bean.MallCategoryBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.master.MasterSearchActivity;
import com.shishiTec.HiMaster.UI.Views.CityPopupWindow;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MallHomeActivity extends FragmentActivity implements View.OnClickListener {
    public DeviceParams device;
    public BaseParams params;
    private List<MallCategoryBean.CategoryListBean> ms;
    private List<MallCategoryBean.BannerListBean> lv;
    private RelativeLayout city;
    public ImageView left_back, screen, gift;
    private CityPopupWindow menuWindow;
    public AutoLinearLayout search_mall;
    private MallFragment mallFragment;
    private TextView tv_city;

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDeviceData();
        setContentView(R.layout.activity_mall_home);
        menuWindow = new CityPopupWindow(MallHomeActivity.this, itemsOnClick);
        initView();
        initData();
    }

    private void initView() {
        tv_city = (TextView) findViewById(R.id.tv_city);
        search_mall = (AutoLinearLayout) findViewById(R.id.search_mall);
        search_mall.setVisibility(View.GONE);
        city = (RelativeLayout) findViewById(R.id.city);
        gift = (ImageView) findViewById(R.id.gift);
        left_back = (ImageView) findViewById(R.id.left_back);
        screen = (ImageView) findViewById(R.id.screen);
        city.setOnClickListener(this);
        gift.setOnClickListener(this);
        left_back.setOnClickListener(this);
        screen.setOnClickListener(this);
        search_mall.setOnClickListener(this);
    }

    //初始化设备信息
    private void initDeviceData() {
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    @Override
    public void onClick(View view) {
        menuWindow.dismiss();
        switch (view.getId()) {
            case R.id.left_back:
                this.finish();
                break;
            case R.id.city:
                if (null == menuWindow) {
                    menuWindow = new CityPopupWindow(MallHomeActivity.this, itemsOnClick);
                }
                menuWindow.showAsDropDown(MallHomeActivity.this.findViewById(R.id.test));
                break;
            case R.id.gift:
                break;
            case R.id.search_mall:
                startActivity(new Intent(MallHomeActivity.this, MasterSearchActivity.class));
                break;
            case R.id.screen: {
                if (mallFragment != null) {
                    mallFragment.showPopWindow();
                }
            }
            break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                default:
                    break;
            }
        }
    };

    public interface MyOnTouchListener {
        public boolean dispatchTouchEvent(MotionEvent ev);
    }

    /**
     * 商城首页数据
     */
    private void initData() {
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .mallStart(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MallCategoryBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<MallCategoryBean> mb) {
                        if (mb.getCode() == 200) {
                            ms = mb.getData().getCategory_list();
                            lv = mb.getData().getBanner_list();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            Gson gson = new Gson();
                            mallFragment = MallFragment.newInstance(gson.toJson(ms), gson.toJson(lv));
                            fragmentTransaction.replace(R.id.fragment, mallFragment);
                            fragmentTransaction.commit();
                        }
                    }
                });
    }

    public void control(boolean b) {
        if (b) {
            search_mall.setVisibility(View.GONE);
            city.setVisibility(View.VISIBLE);
        } else {
            search_mall.setVisibility(View.VISIBLE);
            city.setVisibility(View.GONE);
        }
    }

    public void search(boolean b) {
        if (b) {
            screen.setVisibility(View.VISIBLE);
        } else {
            screen.setVisibility(View.GONE);
        }
    }

    public void cityRefresh(String city_code, String city_name) {
        tv_city.setText(city_name);
        mallFragment.cityRefresh(city_code);
    }
}

