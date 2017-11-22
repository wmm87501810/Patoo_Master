package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CouponBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.CouponAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyCouponActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView_myShare_card)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout_myShare_card)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.card_pay)
    Button card_pay;
    private int page = 1;
    private int page_size = 10;
    private List<CouponBean> mData = new ArrayList<>();
    private CouponAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCoupon(1, page_size, true);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        topTitle.setText(R.string.my_coupon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        card_pay.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.finish);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CouponAdapter(this, mData);
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                getCoupon(1, page_size, true);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        swipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getCoupon(++page, page_size, false);
                swipeRefreshLayout.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

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
                    }

                    @Override
                    public void onNext(BaseModel<List<CouponBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                mData = listBaseModel.getData();
                                adapter = new CouponAdapter(MyCouponActivity.this, mData);
                                recyclerView.setAdapter(adapter);
                            } else {
                                if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
                                    mData.addAll(listBaseModel.getData());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                    }
                });

        addSubscription(subscribe);

    }

}
