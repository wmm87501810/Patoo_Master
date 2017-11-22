package com.shishiTec.HiMaster.UI.Activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.ChatInfoBean;
import com.shishiTec.HiMaster.Model.realbean.MyFriendMsgBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.discover.ChatAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 83802 on 2017/8/25.
 */

public class ChatActivity extends BaseActivity {
    @Bind(R.id.swipeRefreshLayout_chat)
    SuperSwipeRefreshLayout swipeRefreshLayoutChat;
    @Bind(R.id.recylerView_chat)
    RecyclerView recylerView;
    @Bind(R.id.et)
    EditText et;
    @Bind(R.id.tvSend)
    Button tvSend;
    private String m_uid;
    private ChatAdapter adapter;
    private List<ChatInfoBean> mData = new ArrayList<ChatInfoBean>();
    private int page = 1;
    private int page_size = 10;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        recylerView.setLayoutManager(linearLayoutManager);
        recylerView.setHasFixedSize(true);
        swipeRefreshLayoutChat.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                //加载更多
                get_chatinfo(++page, page_size, false);
                swipeRefreshLayoutChat.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        swipeRefreshLayoutChat.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多
//                get_chatinfo(++page, page_size, false);
               swipeRefreshLayoutChat.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et.getText().toString().trim();
                post_msg(text);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_uid = getIntent().getStringExtra("uid");
        get_chatinfo(1, page_size, true);
    }
    private void get_chatinfo(int page, int page_size, final boolean b) {
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("m_uid",m_uid);
        cardMap.put("page", page + "");
        cardMap.put("page_size", page_size + "");
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(cardMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .get_chatinfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<ChatInfoBean>>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        int a = 0;
                    }

                    @Override
                    public void onNext(BaseModel<List<ChatInfoBean>> listBaseModel) {
                        if(listBaseModel.getCode()==200){
                            if (b) {
                                if(mData!=null&&mData.size()>0){
                                    //再次提交MSG
                                    mData.clear();
                                    for(int i=listBaseModel.getData().size()-1;i>=0;i--)
                                    {
                                        mData.add(listBaseModel.getData().get(i));
                                    }
                                    adapter.notifyDataSetChanged();
                                    return;
                                }
                                //第一次
                                for(int i=listBaseModel.getData().size()-1;i>=0;i--)
                                {
                                    mData.add(listBaseModel.getData().get(i));
                                }
                                adapter = new ChatAdapter(ChatActivity.this, mData);
                                recylerView.setAdapter(adapter);
                            } else {
                                if (listBaseModel.getData() != null && listBaseModel.getData().size() > 0) {
                                    for(int i=0;i<listBaseModel.getData().size();i++) {
                                        //数据不用倒序往list前插入
                                        mData.add(0, listBaseModel.getData().get(i));
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
        addSubscription(subscription);
    }
    private void post_msg(String text) {
        Map<String, String> cardMap = new HashMap<>();
        cardMap.put("m_uid",m_uid);
        cardMap.put("type", "1");
        cardMap.put("content", text);
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken(SharedPreferencesUtil.getInstance().getToken());
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(cardMap);
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
                        if(listBaseModel.getCode()==200){
                            //刷新
                            get_chatinfo(1, page_size, true);
                        }
                    }
                });
        addSubscription(subscription);
    }

}
