package com.shishiTec.HiMaster.UI.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.MyFriendBean;
import com.shishiTec.HiMaster.Model.realbean.MyFriendMsgBean;
import com.shishiTec.HiMaster.Model.realbean.TestEvent;
import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.discover.FrgOrMsgAdapter;
import com.shishiTec.HiMaster.UI.Adapter.shequ.ShequCardAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class MyFriendOrMessage extends BaseActivity implements  View.OnClickListener{


    @Bind(R.id.back_total)
    LinearLayout backTotal;
    @Bind(R.id.rank_list_tag_friend)
    FrameLayout rankListTagFriend;
    @Bind(R.id.rank_list_tag_message)
    FrameLayout rankListTagMessage;
    @Bind(R.id.recyclerView_rv_myfrgmsg_card)
    RecyclerView recyclerViewRvMyfrgmsgCard;
    @Bind(R.id.swipeRefreshLayout_rv_myfrgmsg_card)
    SuperSwipeRefreshLayout swipeRefreshLayoutRvMyfrgmsgCard;
    private String way = "1";
    private int page = 1;
    private int page_size = 10;
    private List<MyFriendBean> mData;
    private List<MyFriendMsgBean> mData2;
    private FrgOrMsgAdapter adapter;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TestEvent event){
        if(event.getTag().equals(ApiContact.KEY_DELETEFRIEND)){
            delete_friend(event.getValue());

        }else if(event.getTag().equals(ApiContact.KEY_DELETEFRIENDMSG)){
            delete_friendmsg(event.getValue());
        }
    }
    @Override
    protected void initViews() {

        rankListTagFriend.setSelected(true);
        rankListTagFriend.getChildAt(2).setVisibility(View.VISIBLE);
        way = "1";
        rankListTagFriend.setOnClickListener(this);
        rankListTagMessage.setOnClickListener(this);
        queryCard2(1, page_size, true);

        recyclerViewRvMyfrgmsgCard.setLayoutManager(linearLayoutManager);

        swipeRefreshLayoutRvMyfrgmsgCard.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                queryCard2(1, page_size, true);
                swipeRefreshLayoutRvMyfrgmsgCard.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        swipeRefreshLayoutRvMyfrgmsgCard.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
                queryCard2(++page, page_size, false);
                swipeRefreshLayoutRvMyfrgmsgCard.setLoadMore(false);
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
        return R.layout.activity_myfriendormsg;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_total:
                this.finish();
                break;
            case R.id.rank_list_tag_friend:
                if (!rankListTagFriend.isSelected()) {
                    rankListTagFriend.setSelected(true);
                    rankListTagMessage.setSelected(false);
                    rankListTagFriend.getChildAt(2).setVisibility(View.VISIBLE);
                    rankListTagMessage.getChildAt(2).setVisibility(View.INVISIBLE);
                    way = "1";
                    queryCard2(1, page_size, true);
                }
                break;
            case R.id.rank_list_tag_message:
                if (!rankListTagMessage.isSelected()) {
                    rankListTagFriend.setSelected(false);
                    rankListTagMessage.setSelected(true);
                    rankListTagFriend.getChildAt(2).setVisibility(View.INVISIBLE);
                    rankListTagMessage.getChildAt(2).setVisibility(View.VISIBLE);
                    way = "2";
                    queryCard2(1, page_size, true);
                }
                break;
            default:
                break;
        }
    }

    private void queryCard2(int page, int page_size, final boolean b) {
        if(way.equals("1")) {
            Map<String, String> cardMap = new HashMap<>();
            cardMap.put("page", page + "");
            cardMap.put("page_size", page_size + "");
            params.setData(cardMap);
            Subscription jj = RetrofitManager.getmInstance().createService()
                    .getFriends(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseModel<List<MyFriendBean>>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseModel<List<MyFriendBean>> listBaseModel) {
//                            if (listBaseModel.getCode() == 200) {
//                                mData = listBaseModel.getData();
//                                adapter = new FrgOrMsgAdapter(MyFriendOrMessage.this, mData, way);
//                                recyclerViewRvMyfrgmsgCard.setAdapter(adapter);
//                            } else {
//                                if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
//                                    mData.addAll(listBaseModel.getData());
//                                    adapter.notifyDataSetChanged();
//                                }
//                            }
                            if (b) {
                                mData = listBaseModel.getData();
                                adapter = new FrgOrMsgAdapter(MyFriendOrMessage.this, mData, way);
                                recyclerViewRvMyfrgmsgCard.setAdapter(adapter);
                            } else {
                                if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
                                    mData.addAll(listBaseModel.getData());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
            addSubscription(jj);
        }else{
            Map<String, String> cardMap = new HashMap<>();
            cardMap.put("page", page + "");
            cardMap.put("page_size", page_size + "");
            params.setData(cardMap);
            Subscription jj = RetrofitManager.getmInstance().createService()
                    .getFriendsMsg(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseModel<List<MyFriendMsgBean>>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseModel<List<MyFriendMsgBean>> listBaseModel) {
                            if (listBaseModel.getCode() == 200) {
                                if (b) {
                                    mData2 = listBaseModel.getData();
                                    adapter = new FrgOrMsgAdapter(MyFriendOrMessage.this, mData2);
                                    recyclerViewRvMyfrgmsgCard.setAdapter(adapter);
                                } else {
                                    if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
                                        mData2.addAll(listBaseModel.getData());
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    });
            addSubscription(jj);
        }
    }

    private void delete_friend(String uid) {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("m_uid",uid);
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .delete_friend(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MyFriendBean>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        int a = 0;
                    }

                    @Override
                    public void onNext(BaseModel<MyFriendBean> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {//刷新
                            queryCard2(1, page_size, true);
                        }
                    }
                });
        addSubscription(subscription);
    }

    private void delete_friendmsg(String id) {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("id",id);
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .delete_friendmsg(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MyFriendMsgBean>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<MyFriendMsgBean> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {//刷新
                            queryCard2(1, page_size, true);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
