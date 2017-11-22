package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shishiTec.HiMaster.Model.bean.OtherStudyBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallOtherStudyAdapter;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MallOtherStudyActivity extends BaseActivity {
    private String course_id;
    private int page = 1, pagesize = 10;
    private RecyclerView recycler_other_study;
    private List<OtherStudyBean> studentBean;
    private MallOtherStudyAdapter mallOtherStudyAdapter;
    private SwipeRefreshLayout swipeLayout_other_study;
    private int lastVisibleItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        recycler_other_study = (RecyclerView) findViewById(R.id.recycler_other_study);
        recycler_other_study.setLayoutManager(linearLayoutManager);
        recycler_other_study.setHasFixedSize(true);
        mallOtherStudyAdapter = new MallOtherStudyAdapter(this, studentBean);
        swipeLayout_other_study = (SwipeRefreshLayout) findViewById(R.id.swipeLayout_other_study);
        initData(page, pagesize, true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeLayout_other_study.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(1, pagesize, true);
            }
        });
        recycler_other_study.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mallOtherStudyAdapter.getItemCount()) {
                    initData(++page, pagesize, false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData(int page, int pagesize, final boolean b) {
        Map<String, String> map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("page", page + "");
        map.put("pagesize", pagesize + "");
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .otherStudy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<OtherStudyBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<List<OtherStudyBean>> mb) {
                        if (mb.getCode() == 200) {
                            studentBean = mb.getData();
                            if (b == true) {
                                mallOtherStudyAdapter = new MallOtherStudyAdapter(MallOtherStudyActivity.this, studentBean);
                                recycler_other_study.setAdapter(mallOtherStudyAdapter);
                                swipeLayout_other_study.setRefreshing(false);
                            } else {
                                studentBean.addAll(mb.getData());
                                mallOtherStudyAdapter.addMoreItem(studentBean);
                                mallOtherStudyAdapter.changeMoreStatus(mallOtherStudyAdapter.NO_MORE_DATA);
                            }
                        }
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mall_other_study;
    }
}
