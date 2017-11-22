package com.shishiTec.HiMaster.UI.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;

import com.mcxtzhang.layoutmanager.swipecard.CardConfig;
import com.mcxtzhang.layoutmanager.swipecard.OverLayCardLayoutManager;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.MyFriendMsgBean;
import com.shishiTec.HiMaster.Model.realbean.SwipeBean;
import com.shishiTec.HiMaster.Model.realbean.TestEvent;
import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Accouont.RegisterActivity;
import com.shishiTec.HiMaster.UI.Tantan.TanTanCallback;
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
 * Created by 83802 on 2017/8/16.
 */

public class Discover extends BaseActivity {
    //    @Bind(R.id.accept_head)
//    CircleImageView acceptHead;
//    @Bind(R.id.accept_head2)
//    CircleImageView acceptHead2;
//    @Bind(R.id.accept_layout)
//    RelativeLayout acceptLayout;
//    @Bind(R.id.swipe)
//    SwipeCardView swipe;
    @Bind(R.id.rv)
    RecyclerView rv;
    private List<SwipeBean> list_swipeBean;
    private Context context;
    //private CardBaseAdapter2 cardBaseAdapter;
    CommonAdapter<SwipeBean> mAdapter;
    List<SwipeBean> mDatas;
    private int postoin = 0 ;

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
        if(postoin<0){
            postoin = mDatas.size()-1;
        }
        if(event.getTag().equals(ApiContact.KEY_YOUHUAZAI)){//赞
            postZai(mDatas.get(postoin).getUid(),"1");
            //startActivity(new Intent(Discover.this,MyFriendOrMessage.class));
            postoin--;
        }else {
            postZai(mDatas.get(postoin).getUid(),"0");
            postoin--;

        }
    }

    @Override
    protected void initViews() {
        context = this;
        getUserlistinfo();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_discover;
    }

    private void getUserlistinfo() {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("sex", SharedPreferencesUtil.getInstance().getSex());
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .getUserlistinfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<SwipeBean>>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        int a = 0;
                    }

                    @Override
                    public void onNext(BaseModel<List<SwipeBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            init(listBaseModel);
                        }
                    }
                });
        addSubscription(subscription);
    }

    private void postZai(String m_uid,String type) {
       // '0为不喜欢，1为喜欢'
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("m_uid", m_uid);
        loginMap.put("type", type);
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(loginMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .post_msg(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<MyFriendMsgBean>>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<MyFriendMsgBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {

                        } else if (listBaseModel.getCode() == 1004) {//用户还没注册
                            startActivity(new Intent(Discover.this, RegisterActivity.class));
                        }
                    }
                });
        addSubscription(subscription);
    }

    public void init(BaseModel<List<SwipeBean>> listBaseModel) {
        CardConfig.initConfig(Discover.this);
        postoin = listBaseModel.getData().size()-1;
        if (mAdapter == null) {
            rv.setLayoutManager(new OverLayCardLayoutManager());
            mDatas = listBaseModel.getData();
            rv.setAdapter(mAdapter = new CommonAdapter<SwipeBean>(this, mDatas = listBaseModel.getData(), R.layout.item_swipe) {


                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);

                }

                @Override
                public void convert(final ViewHolder viewHolder, final SwipeBean swipeBean) {

                    viewHolder.setText(R.id.nickname, swipeBean.getNickname());
                    //holder. num.setText(position+"/" + getCount());
                    Glide.with(context)
                            .load(ApiContact.IMAGE_URL2 + swipeBean.getImg_0())
                            .into((ImageView) viewHolder.getView(R.id.image00));
                    viewHolder.setText(R.id.age, swipeBean.getAge());
                    viewHolder.setText(R.id.province, swipeBean.getProvince());
                    viewHolder.setText(R.id.like_you, swipeBean.getLike_you());
                    viewHolder.setText(R.id.ranking, swipeBean.getRanking());
                    viewHolder.setOnClickListener(R.id.image00, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Discover.this,DiscoverUserDetailsInfo.class);
                            intent.putExtra("uid",swipeBean.getUid());
                            startActivity(intent);

                        }
                    });
                   // viewHolder.setTag(10,swipeBean.getUid());
                }
            });

            final TanTanCallback callback = new TanTanCallback(rv, mAdapter, mDatas);
            //测试竖直滑动是否已经不会被移除屏幕
            //callback.setHorizontalDeviation(Integer.MAX_VALUE);
            final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(rv);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
