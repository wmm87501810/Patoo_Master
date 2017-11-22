package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MCourseBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.MDClassAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMDActivity extends BaseActivity {


    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.show_t)
    TextView showT;
    @Bind(R.id.my_m)
    TextView myM;
    @Bind(R.id.to_detail)
    RelativeLayout toDetail;
    @Bind(R.id.recyclerView_md)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_md)
    SuperSwipeRefreshLayout refresh_md;
    private int page = 1;
    private int page_size = 10;
    private List<MCourseBean> mData = new ArrayList<>();
    private MDClassAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryMCourse(page, page_size, true);

    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        String mPotion = getIntent().getStringExtra("mPoint");
        topTitle.setText(getString(R.string.my_m_number));
        rightTitle.setText(getString(R.string.how_use));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
        myM.setText(mPotion);
        adapter = new MDClassAdapter(MyMDActivity.this, mData);
        recyclerView.setLayoutManager(linearLayoutManager);
        refresh_md.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                queryMCourse(page, page_size, true);
                refresh_md.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        refresh_md.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                queryMCourse(page, page_size, false);
                refresh_md.setLoadMore(false);
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
        return R.layout.activity_my_md;
    }

    @OnClick(R.id.to_detail)
    void setToDetail() {
        startActivity(new Intent(MyMDActivity.this, MDetailActivity.class));
    }
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
                                mData = listBaseModel.getData();
                                adapter = new MDClassAdapter(MyMDActivity.this, mData);
                                recyclerView.setAdapter(adapter);
                            } else {
                                if (listBaseModel.getData().size() > 0) {
                                    mData.addAll(listBaseModel.getData());
                                    adapter.addMoreItem(mData);
                                } else {
                                    ToastUtils.showCenter(MyMDActivity.this, "暂无任何数据");
                                }
                            }
                        } else {
                            ToastUtils.showCenter(MyMDActivity.this, listBaseModel.getMsg());
                        }

                    }
                });
        addSubscription(subscribe);
    }

}
