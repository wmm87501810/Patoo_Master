package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shishiTec.HiMaster.Model.bean.CollectsListBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.MyCollectsAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyCollectionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView left_back;
    private RecyclerView collection_recycler;
    private int page = 1;
    private int page_size = 10;
    private SuperSwipeRefreshLayout swipeLayout;
    private List<CollectsListBean> collectsListBeen;
    private MyCollectsAdapter myCollectsAdapter;

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
        left_back = (ImageView) findViewById(R.id.left_back);
        left_back.setOnClickListener(this);
        swipeLayout = (SuperSwipeRefreshLayout) findViewById(R.id.collection_super_refresh);
        collection_recycler = (RecyclerView) findViewById(R.id.collection_recycler);
        collection_recycler.setLayoutManager(linearLayoutManager);
        myCollectsAdapter = new MyCollectsAdapter(MyCollectionActivity.this, collectsListBeen);
        collection_recycler.setHasFixedSize(true);
        swipeLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                initData(1, 10, false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        swipeLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                initData(++page, page_size, true);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
        initData(page, page_size, false);
    }

    private void initData(int page, int page_size, final boolean b) {
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("page", page + "");
        cardMap.put("page_size", page_size + "");
        params.setData(cardMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .collectsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<CollectsListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel<List<CollectsListBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                if (listBaseModel.getData().size() > 0) {
                                    collectsListBeen.addAll(listBaseModel.getData());
                                    myCollectsAdapter.addMore(collectsListBeen);
                                    swipeLayout.setLoadMore(false);
                                } else {
                                    Toast.makeText(MyCollectionActivity.this, "暂无最新数据", Toast.LENGTH_SHORT).show();
                                    swipeLayout.setLoadMore(false);
                                }
                            } else {
                                collectsListBeen = listBaseModel.getData();
                                myCollectsAdapter = new MyCollectsAdapter(MyCollectionActivity.this, collectsListBeen);
                                collection_recycler.setAdapter(myCollectsAdapter);
                                swipeLayout.setRefreshing(false);
                            }
                        } else {
                            ToastUtils.showGravity(MyCollectionActivity.this, listBaseModel.getMsg(), Gravity.CENTER, 0, 0);
                        }

                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                this.finish();
                break;
        }
    }
}
