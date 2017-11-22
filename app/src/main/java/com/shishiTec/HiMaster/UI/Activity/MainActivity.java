package com.shishiTec.HiMaster.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.shishiTec.HiMaster.Model.bean.InfoConfBean;
import com.shishiTec.HiMaster.Model.bean.TabEntity;
import com.shishiTec.HiMaster.Model.bean.UserCenterBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Accouont.InformationActivity;
import com.shishiTec.HiMaster.UI.Activity.UserCenter.*;
import com.shishiTec.HiMaster.UI.Activity.bigManShare.ReleaseActivity;
import com.shishiTec.HiMaster.UI.Activity.mall.MallHomeActivity;
import com.shishiTec.HiMaster.UI.Activity.master.MasterSearchActivity;
import com.shishiTec.HiMaster.UI.Activity.master.MasterUploadActivity;
import com.shishiTec.HiMaster.UI.Activity.master.OrderManagerActivity;
import com.shishiTec.HiMaster.UI.Views.LoginDialogView;
import com.shishiTec.HiMaster.Utils.LoginUtil;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseFragmentActivity;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseFragmentActivity {
    @Bind(R.id.al_master_search)
    AutoLinearLayout autoLinearLayout;
    @Bind(R.id.user_icon)
    ImageButton userIcon;
    @Bind(R.id.shop)
    ImageButton shop;
    @Bind(R.id.iv_master_main_top)
    ImageButton iv_master_main_top;
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.userCenter_icon)
    ImageView userCenterIcon;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.fans_number)
    TextView fansNumber;
    @Bind(R.id.follow_number)
    TextView followNumber;
    @Bind(R.id.message)
    ImageButton message;
    @Bind(R.id.icon_red)
    ImageView iconRed;
    @Bind(R.id.show_message)
    RelativeLayout showMessage;
    @Bind(R.id.to_setting)
    ImageButton toSetting;
    @Bind(R.id.scan_order)
    TextView scanOrder;
    @Bind(R.id.scan_code)
    RelativeLayout scanCode;
    @Bind(R.id.tx_order_manager)
    TextView txOrderManager;
    @Bind(R.id.order_manager)
    RelativeLayout orderManager;
    @Bind(R.id.master_user)
    LinearLayout masterUser;
    @Bind(R.id.mNumber)
    TextView mNumber;
    @Bind(R.id.to_my_MD)
    RelativeLayout toMyMD;
    @Bind(R.id.voucher_number)
    TextView voucherNumber;
    @Bind(R.id.to_voucher)
    RelativeLayout toVoucher;
    @Bind(R.id.soyCard)
    TextView soyCard;
    @Bind(R.id.to_card)
    RelativeLayout toCard;
    @Bind(R.id.normal_user)
    LinearLayout normalUser;
    @Bind(R.id.share)
    TextView share;
    @Bind(R.id.order)
    TextView order;
    @Bind(R.id.collect)
    TextView collect;
    @Bind(R.id.my_voucher_bag)
    TextView myVoucherBag;
    @Bind(R.id.welfare)
    TextView welfare;
    @Bind(R.id.beMaster)
    TextView beMaster;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.user_center_buy_order)
    LinearLayout user_center_buy_order;
    private ArrayList<Fragment> fragments;
    private UserCenterBean userCenterBean;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.master_logo_unchecked, R.mipmap.master_logo_unchecked};
    private int[] mIconSelectIds = {
            R.mipmap.master_logo_checked, R.mipmap.master_logo_checked};
    private LoginDialogView confirmDialog;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initInfoData();
//        initDragLayout();
    }

    private void initInfoData() {
        Map<String, String> verifyMap = new HashMap<>();
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setData(verifyMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .initConfDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<InfoConfBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final BaseModel<InfoConfBean> mb) {
                        if (mb.getCode() == 200) {
                            List<InfoConfBean.OrderTypeBean> o = mb.getData().getOrder_type();
                            List<InfoConfBean.SelectTypeBean> s = mb.getData().getSelect_type();
                            List<InfoConfBean.CityListBean> c = mb.getData().getCity_list();
                            Gson gson = new Gson();
                            SharedPreferencesUtil.getInstance().saveOrderType(gson.toJson(o));
                            SharedPreferencesUtil.getInstance().saveSelectType(gson.toJson(s));
                            SharedPreferencesUtil.getInstance().saveCityList(gson.toJson(c));
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLeftData();
    }

    //获取侧边栏数据
    private void initLeftData() {
        Map<String, String> verifyMap = new HashMap<>();
        DeviceParams device = new DeviceParams();
        BaseParams params = new BaseParams(device);
        params.setDevice(device);
        params.setData(verifyMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .userCenter(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<UserCenterBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<UserCenterBean> userCenterBeanBaseModel) {
                        if (userCenterBeanBaseModel.getCode() == 200) {
                            userCenterBean = userCenterBeanBaseModel.getData();
                            if (userCenterBean != null) {
                                initUserCenterData(userCenterBean);
                            }
                        }
                    }
                });
        addSubscription(subscribe);

    }

    //初始化用户中心数据
    private void initUserCenterData(UserCenterBean data) {
        BaseApplication.getInstance().loadCircleImageView(MainActivity.this, userCenterIcon, data.getImg_top());
        userName.setText(data.getNikename());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("粉丝 : ");
        stringBuilder.append(data.getFans());
        fansNumber.setText(stringBuilder.toString());
        StringBuilder sbFollows = new StringBuilder();
        sbFollows.append("关注 : ");
        sbFollows.append(data.getFollows());
        followNumber.setText(sbFollows.toString());
        if (data.getIdentity().equals("1")) {
            masterUser.setVisibility(View.GONE);
            myVoucherBag.setVisibility(View.GONE);
            normalUser.setVisibility(View.VISIBLE);
            beMaster.setVisibility(View.VISIBLE);
            mNumber.setText(data.getM_point());
            voucherNumber.setText(data.getCoupon_sum());
            soyCard.setText(data.getCard_num());
        } else if (data.getIdentity().equals("2")) {
            masterUser.setVisibility(View.VISIBLE);
            beMaster.setVisibility(View.GONE);
            myVoucherBag.setVisibility(View.VISIBLE);
            normalUser.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    private void initDragLayout() {
//        dl.setDragListener(new DragLayout.DragListener() {
//            //界面打开的时候
//            @Override
//            public void onOpen() {
//
//            }
//
//            //界面关闭的时候
//            @Override
//            public void onClose() {
//            }
//
//            //界面滑动的时候
//            @Override
//            public void onDrag(float percent) {
//                ViewHelper.setAlpha(userIcon, 1 - percent);
//            }
//        });
//    }

    private void initView() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.lay_master, null)));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(getLayoutInflater().inflate(R.layout.lay_bigman, null)));
        final List<String> list = new ArrayList<>();
        list.add("达人");
        list.add("素人");
        for (int i = 0; i < list.size(); i++) {
            mTabEntities.add(new TabEntity(list.get(i), mIconSelectIds[i], mIconUnselectIds[i]));
        }
        fragments = new ArrayList<>();
        fragments.add(new com.shishiTec.HiMaster.UI.fragment.master.MasterFragment());
        fragments.add(new com.shishiTec.HiMaster.UI.fragment.BigMan.BigManFragment());

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
        };
        viewPager.setAdapter(adapter);
        tablayout();
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }


    private void tablayout() {
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(final int position) {
                if (position == 1) {
                    autoLinearLayout.setVisibility(View.GONE);
                } else {
                    autoLinearLayout.setVisibility(View.VISIBLE);
                }
                iv_master_main_top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toUpload(position);
                    }
                });
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    tabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                tabLayout.setCurrentTab(position);
                if (position == 1) {
                    autoLinearLayout.setVisibility(View.GONE);
                } else {
                    autoLinearLayout.setVisibility(View.VISIBLE);
                }
                iv_master_main_top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toUpload(position);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.user_icon)
    void openSlide() {
        if (SharedPreferencesUtil.getInstance().isLogin()) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            LoginUtil.getInstance().login(MainActivity.this);
        }
    }

    @OnClick(R.id.shop)
    void testRegister() {
        startActivity(new Intent(MainActivity.this, MallHomeActivity.class));
    }

    //设置
    @OnClick(R.id.to_setting)
    void toSetting() {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //搜索
    @OnClick(R.id.al_master_search)
    void search() {
        startActivity(new Intent(MainActivity.this, MasterSearchActivity.class));
    }

    //订单管理
    @OnClick(R.id.tx_order_manager)
    void orderManager() {
        startActivity(new Intent(MainActivity.this, OrderManagerActivity.class));
    }

    void toUpload(int type) {
        if (SharedPreferencesUtil.getInstance().isLogin()) {
            if (type == 1) {
                startActivity(new Intent(MainActivity.this, ReleaseActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, MasterUploadActivity.class));
            }
        } else {
            LoginUtil.getInstance().login(MainActivity.this);
        }

    }

    //M点
    @OnClick(R.id.to_my_MD)
    void toScore() {
        Intent intent = new Intent(MainActivity.this, MyMDActivity.class);
        intent.putExtra("mPoint", userCenterBean.getM_point());
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //优惠券
    @OnClick(R.id.to_voucher)
    void toCoupopn() {
        startActivity(new Intent(MainActivity.this, MyCouponActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //粉丝
    @OnClick(R.id.fans_number)
    void tomyFans() {
        Intent intent = new Intent(MainActivity.this, MyFansAndFollowsActivity.class);
        intent.putExtra("fans", "0");
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //关注
    @OnClick(R.id.follow_number)
    void toFollows() {
        Intent intent = new Intent(MainActivity.this, MyFansAndFollowsActivity.class);
        intent.putExtra("fans", "1");
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //扫码
    @OnClick(R.id.scan_code)
    void scanCode() {
        startActivity(new Intent(MainActivity.this, SweepActivity.class));
    }

    //酱油卡
    @OnClick(R.id.to_card)
    void toMyCard() {
        startActivity(new Intent(MainActivity.this, MyCardActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //个人分享
    @OnClick(R.id.share)
    void toShare() {
        startActivity(new Intent(MainActivity.this, MyShareActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //个人资料编辑
    @OnClick(R.id.userCenter_icon)
    void toUserInfo() {
        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
        intent.putExtra("userBean", (Serializable) userCenterBean);
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //消息
    @OnClick(R.id.show_message)
    void toMessage() {
        startActivity(new Intent(MainActivity.this, InformationActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //我的订单
    @OnClick(R.id.user_center_buy_order)
    void buyOrder() {
        startActivity(new Intent(MainActivity.this, BuyOrderActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //兑换码领福利
    @OnClick(R.id.welfare)
    void welfares() {
        startActivity(new Intent(MainActivity.this, WelfareActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    //我的卡券包
    @OnClick(R.id.my_voucher_bag)
    void cardPackage() {
        Intent intent = new Intent();
        intent.putExtra("num", userCenterBean.getM_point());
        intent.setClass(MainActivity.this, CardPackageActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.collect)
    void setCollect() {
        startActivity(new Intent(MainActivity.this, MyCollectionActivity.class));
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
