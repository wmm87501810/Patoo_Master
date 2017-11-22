package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CardListsBean;
import com.shishiTec.HiMaster.Model.bean.SoyCardBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
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

public class BuyCardListActivity extends BaseActivity {

    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.recyclerView_myShare_card)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout_myShare_card)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    private int page1 = 1;
    private int page_size = 10;
    private List<SoyCardBean> mData = new ArrayList<>();
    private List<CardListsBean> cardListsBeen = new ArrayList<>();
    private SoyBuyCardAdapter soyBuyCardAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buyCardList(1, page_size, true);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        topTitle.setText("购买列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setNavigationIcon(R.mipmap.finish);
        soyBuyCardAdapter = new SoyBuyCardAdapter(this, cardListsBeen);
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                buyCardList(1, page_size, true);
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
                buyCardList(++page1, page_size, false);
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
        return R.layout.activity_buy_card_list;
    }


    /**
     * 卡片翻转动画
     *
     * @param show_front
     * @param show_negative
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void flipit(RelativeLayout show_front, RelativeLayout show_negative) {
        Interpolator accelerator = new AccelerateInterpolator();
        Interpolator decelerator = new DecelerateInterpolator();
        final RelativeLayout visibleList, invisibleList;
        final ObjectAnimator visToInvis, invisToVis;
        if (show_front.getVisibility() == View.GONE) {
            visibleList = show_negative;
            invisibleList = show_front;
            visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
            invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY", -90f, 0f);
        } else {
            invisibleList = show_negative;
            visibleList = show_front;
            visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, -90f);
            invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY", 90f, 0f);
        }
        visToInvis.setDuration(300);
        invisToVis.setDuration(300);
        visToInvis.setInterpolator(accelerator);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }

    private void buyCardList(int page1, int page_size, final boolean b) {
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("page", page1 + "");
        cardMap.put("page_size", page_size + "");
        params.setData(cardMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .cardLists(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<CardListsBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel<List<CardListsBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            if (b) {
                                cardListsBeen = listBaseModel.getData();
                                soyBuyCardAdapter = new SoyBuyCardAdapter(BuyCardListActivity.this, cardListsBeen);
                                recyclerView.setAdapter(soyBuyCardAdapter);
                            } else {
                                if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
                                    cardListsBeen.addAll(listBaseModel.getData());
                                    soyBuyCardAdapter.notifyDataSetChanged();
                                }
                            }
                            soyBuyCardAdapter.setOnFlipClickListener(new SoyBuyCardAdapter.OnFlipClickListener() {
                                @Override
                                public void OnFlipClick(int position, RelativeLayout showFront, RelativeLayout showNegative) {
                                    flipit(showFront, showNegative);
                                }
                            });
                            soyBuyCardAdapter.setOnFlipInfoClickListener(new SoyBuyCardAdapter.OnFlipInfoClickListener() {
                                @Override
                                public void OnFlipInfoClick(int position, RelativeLayout showFront, RelativeLayout showNegative) {
                                    flipit(showFront, showNegative);
                                }
                            });
                        } else {
                            ToastUtils.showGravity(BuyCardListActivity.this, listBaseModel.getMsg(), Gravity.CENTER, 0, 0);
                        }

                    }
                });
        addSubscription(subscribe);
    }
}
