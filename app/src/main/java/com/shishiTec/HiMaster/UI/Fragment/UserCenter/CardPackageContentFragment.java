package com.shishiTec.HiMaster.UI.fragment.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CouponBean;
import com.shishiTec.HiMaster.Model.bean.MCourseBean;
import com.shishiTec.HiMaster.Model.bean.SoyCardBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.UserCenter.MDetailActivity;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.BuyOrderContentAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.CouponAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.MDClassAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.SoyCardAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hyu_anzhuo on 2016/5/27.
 */


public class CardPackageContentFragment extends BaseFragment {
    private RecyclerView buyOrderRecycler;
    private LinearLayoutManager layoutManager;
    private BuyOrderContentAdapter buyOrderContentAdapter;
    private SuperSwipeRefreshLayout super_buy_order;
    private int page = 1,page1 = 1,page2 = 1;
    private int pagesize = 10;
    private String types;
    private List<CouponBean> couponBeen = new ArrayList<>();
    private List<MCourseBean> mCourseBeen = new ArrayList<>();
    private List<SoyCardBean> soyCardBeen = new ArrayList<>();
    private CouponAdapter couponAdapter;
    private MDClassAdapter mpAdapter;
    private SoyCardAdapter soyCardAdapter;
    private AutoLinearLayout MD_al;
    private TextView my_m;

    public CardPackageContentFragment() {
        super();
    }

    public static CardPackageContentFragment newInstance(String type, String num) {
        CardPackageContentFragment mallFragment = new CardPackageContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("num", num);
        mallFragment.setArguments(bundle);
        return mallFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_buy_order_content;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        types = getType();
        MD_al = (AutoLinearLayout) contentView.findViewById(R.id.MD_al);
        my_m = (TextView) contentView.findViewById(R.id.my_m);
        super_buy_order = (SuperSwipeRefreshLayout) contentView.findViewById(R.id.super_buy_order);
        buyOrderRecycler = (RecyclerView) contentView.findViewById(R.id.buyOrderRecycler);
        layoutManager = new LinearLayoutManager(getActivity());
        couponAdapter = new CouponAdapter(getActivity(), couponBeen);
        mpAdapter = new MDClassAdapter(getActivity(), mCourseBeen);
        soyCardAdapter = new SoyCardAdapter(getActivity(), soyCardBeen);
        buyOrderRecycler.setLayoutManager(layoutManager);
        if (types.equals("0")) {
            MD_al.setVisibility(View.GONE);
            getCoupon(page, pagesize, true);
        } else if (types.equals("2")) {
            MD_al.setVisibility(View.GONE);
            queryCard(1, pagesize, true);
        } else {
            MD_al.setVisibility(View.VISIBLE);
            my_m.setText(getArguments().getString("num"));
            MD_al.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), MDetailActivity.class));
                }
            });
            queryMCourse(1, pagesize, true);
        }
        super_buy_order.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                if (types.equals("0")) {
                    getCoupon(1, pagesize, true);
                } else if (types.equals("2")) {
                    queryCard(1, pagesize, true);
                } else {
                    queryMCourse(1, pagesize, true);
                }
                super_buy_order.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        super_buy_order.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (types.equals("0")) {
                    getCoupon(++page, pagesize, false);
                } else if (types.equals("2")) {
                    queryCard(++page1, pagesize, false);
                } else {
                    queryMCourse(++page2, pagesize, false);
                }
                super_buy_order.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    //我的代金券
    private void getCoupon(int page, int page_size, final boolean b) {
        Map<String, String> scoreMap = new HashMap<>();
        scoreMap.put("page", page + "");
        scoreMap.put("page_size", page_size + "");
        params.setData(scoreMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .getCoupon(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<CouponBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(getActivity(), "请求失败", Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onNext(BaseModel<List<CouponBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                couponBeen = listBaseModel.getData();
                                couponAdapter = new CouponAdapter(getActivity(), couponBeen);
                                buyOrderRecycler.setAdapter(couponAdapter);
                            } else if (listBaseModel.getData().size() > 0) {
                                couponBeen.addAll(listBaseModel.getData());
                                couponAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });

        addSubscription(subscribe);
    }

    //我的M点
    private void queryMCourse(int page, int page_size, final boolean b) {
        Map<String, String> mCourseMap = new HashMap<>();
        mCourseMap.put("page", page + "");
        mCourseMap.put("page_size", page_size + "");
        params.setData(mCourseMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .queryMCourse(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MCourseBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<MCourseBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                mCourseBeen = listBaseModel.getData();
                                mpAdapter = new MDClassAdapter(getActivity(), mCourseBeen);
                                buyOrderRecycler.setAdapter(mpAdapter);
                            } else if (listBaseModel.getData().size() > 0) {
                                mCourseBeen.addAll(listBaseModel.getData());
                                mpAdapter.addMoreItem(mCourseBeen);
                            }
                        } else {
                            ToastUtils.showCenter(getActivity(), listBaseModel.getMsg());
                        }
                    }
                });
        addSubscription(subscribe);
    }

    //我的酱油卡
    private void queryCard(int page, int page_size, final boolean b) {
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("page", page + "");
        cardMap.put("page_size", page_size + "");
        params.setData(cardMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .getCard(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<SoyCardBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(getActivity(), "请求失败", Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onNext(BaseModel<List<SoyCardBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                soyCardBeen = listBaseModel.getData();
                                soyCardAdapter = new SoyCardAdapter(getActivity(), soyCardBeen);
                                buyOrderRecycler.setAdapter(soyCardAdapter);
                            } else if (listBaseModel.getData().size() > 0) {
                                soyCardBeen.addAll(listBaseModel.getData());
                                soyCardAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtils.showGravity(getActivity(), listBaseModel.getMsg(), Gravity.CENTER, 0, 0);
                        }

                    }
                });
        addSubscription(subscribe);
    }

    public String getType() {
        String type = "";
        if (getArguments().getString("type") != null) {
            if (getArguments().getString("type").equals("代金券")) {
                type = "0";
            } else if (getArguments().getString("type").equals("酱油卡")) {
                type = "2";
            } else if (getArguments().getString("type").equals("M点")) {
                type = "1";
            }
        }
        return type;
    }
}
