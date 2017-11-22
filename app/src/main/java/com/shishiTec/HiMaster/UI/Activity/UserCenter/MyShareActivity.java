package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shishiTec.HiMaster.Model.bean.MyShareBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.MyShareAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyShareActivity extends BaseActivity {

    TextView top_title;
    TextView rightTitle;
    Toolbar toolbar;
    AppBarLayout appbar;
    RecyclerView recyclerView_myShare_card;
    SuperSwipeRefreshLayout swipeRefreshLayout_myShare_card;
    private int page = 1;
    private int page_size = 10;
    private MyShareAdapter myShareAdapter;
    private List<MyShareBean> list;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private Button card_pay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryMyShare(page, page_size, true);
        Refresh();

    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    private void Refresh() {
        swipeRefreshLayout_myShare_card.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                queryMyShare(1, page_size, true);
                swipeRefreshLayout_myShare_card.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        swipeRefreshLayout_myShare_card.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                queryMyShare(++page, page_size, false);
                swipeRefreshLayout_myShare_card.setLoadMore(false);
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
    protected void initViews() {
        card_pay = (Button) findViewById(R.id.card_pay);
        card_pay.setVisibility(View.GONE);
        recyclerView_myShare_card = (RecyclerView) findViewById(R.id.recyclerView_myShare_card);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        top_title = (TextView) findViewById(R.id.top_title);
        swipeRefreshLayout_myShare_card = (SuperSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_myShare_card);
        linearLayoutManager = new LinearLayoutManager(MyShareActivity.this);
        recyclerView_myShare_card.setLayoutManager(linearLayoutManager);
        top_title.setText("我的分享");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    private void queryMyShare(int page, int page_size, final boolean b) {
        Map<String, String> myShareMap = new HashMap<>();
        myShareMap.put("page", page + "");
        myShareMap.put("page_size", page_size + "");
        params.setData(myShareMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .queryShare(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MyShareBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel<List<MyShareBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                list = listBaseModel.getData();
                                myShareAdapter = new MyShareAdapter(MyShareActivity.this, list);
                                recyclerView_myShare_card.setAdapter(myShareAdapter);
                            } else {
                                if (listBaseModel.getData().size() > 0) {
                                    list.addAll(listBaseModel.getData());
                                    myShareAdapter.addMoreItem(list);
                                } else {
                                    Toast.makeText(MyShareActivity.this, "暂无最新数据", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    }
                });
        addSubscription(subscribe);
    }


}
