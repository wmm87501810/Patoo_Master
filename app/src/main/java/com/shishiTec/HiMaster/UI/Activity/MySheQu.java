package com.shishiTec.HiMaster.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shishiTec.HiMaster.Model.realbean.MyShequBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.shequ.ShequCardAdapter;
import com.shishiTec.HiMaster.UI.Views.BannerView;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.ToastUtils;
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



/**
 * Created by 83802 on 2017/8/21.
 */

public class MySheQu extends BaseActivity {
    @Bind(R.id.banner)
    BannerView banner;
    @Bind(R.id.rv_my_shequ)
    RecyclerView rvMyShequ;
    @Bind(R.id.bt_message)
    Button btMessage;
    @Bind(R.id.recyclerView_rv_my_shequ_card)
    RecyclerView recyclerViewRvMyShequCard;
    @Bind(R.id.swipeRefreshLayout_rv_my_shequ_card)
    SuperSwipeRefreshLayout swipeRefreshLayoutRvMyShequCard;
    private ShequCardAdapter adapter;

    private int[] imgs = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    private List<View> viewList;
    private int page = 1;
    private int page_size = 10;
    private List<MyShequBean> mData;


    @Override
    public String[] getPermissions() {
        return new String[0];
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryCard(1, page_size, true);
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        viewList = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        banner.startLoop(true);
        banner.setViewList(viewList);

        swipeRefreshLayoutRvMyShequCard.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                queryCard(1, page_size, true);
                swipeRefreshLayoutRvMyShequCard.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        swipeRefreshLayoutRvMyShequCard.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
                queryCard(++page, page_size, false);
                swipeRefreshLayoutRvMyShequCard.setLoadMore(false);
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
        return R.layout.activity_shequ;
    }


    private void initRecyclerView() {
        LinearLayoutManager hotManager = new LinearLayoutManager(this);
        hotManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //我的照片
        rvMyShequ.setLayoutManager(hotManager);
        rvMyShequ.setItemAnimator(new DefaultItemAnimator());

        recyclerViewRvMyShequCard.setLayoutManager(linearLayoutManager);
    }
    private void queryCard(int page, int page_size, final boolean b) {
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("page", page + "");
        cardMap.put("page_size", page_size + "");
        params.setData(cardMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .getShequ(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MyShequBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel<List<MyShequBean>> listBaseModel) {

                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                mData = listBaseModel.getData();
                                adapter = new ShequCardAdapter(MySheQu.this, mData);
                                recyclerViewRvMyShequCard.setAdapter(adapter);
                            } else {
                                if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
                                    mData.addAll(listBaseModel.getData());
                                    adapter.notifyDataSetChanged();
                                }
                            }
//                            adapter.setOnFlipClickListener(new SoyCardAdapter.OnFlipClickListener() {
//                                @Override
//                                public void OnFlipClick(int position, RelativeLayout showFront, RelativeLayout showNegative) {
//                                    flipit(showFront, showNegative);
//                                }
//                            });
//                            adapter.setOnFlipInfoClickListener(new SoyCardAdapter.OnFlipInfoClickListener() {
//                                @Override
//                                public void OnFlipInfoClick(int position, RelativeLayout showFront, RelativeLayout showNegative) {
//                                    flipit(showFront, showNegative);
//                                }
//                            });
                        } else {
                            ToastUtils.showGravity(MySheQu.this, listBaseModel.getMsg(), Gravity.CENTER, 0, 0);
                        }
                    }
                });
        addSubscription(subscribe);
    }


}
