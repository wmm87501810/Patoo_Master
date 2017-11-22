package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shishiTec.HiMaster.Model.bean.CommentListBean;
import com.shishiTec.HiMaster.Model.bean.NotificationBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.CommentReplayAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.NotificationAdapter;
import com.shishiTec.HiMaster.Utils.DividerItemDecoration;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommentReplayActivity extends BaseActivity {
    private RecyclerView rv_comment_replay;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        rv_comment_replay = (RecyclerView) findViewById(R.id.rv_comment_replay);
        LinearLayoutManager contentManager = new LinearLayoutManager(this);
        contentManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        rv_comment_replay.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));
        rv_comment_replay.setLayoutManager(contentManager);
        rv_comment_replay.setItemAnimator(new DefaultItemAnimator());
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_replay;
    }

    public void initData(){
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("page_size","100");
        params.setData(map);
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .getCommentList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<CommentListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<CommentListBean>> listBaseModel) {
                        CommentReplayAdapter commentReplayAdapter = new CommentReplayAdapter(CommentReplayActivity.this,listBaseModel.getData());
                        rv_comment_replay.setAdapter(commentReplayAdapter);
                        //创建默认的线性LayoutManager
                    }
                });
        addSubscription(subscription);
    }
}
