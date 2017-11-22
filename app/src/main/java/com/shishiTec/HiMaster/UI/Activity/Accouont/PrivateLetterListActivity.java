package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shishiTec.HiMaster.Model.bean.CommentListBean;
import com.shishiTec.HiMaster.Model.bean.PrivateNewsListBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.CommentReplayAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.PrivateNewsListAdapter;
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

public class PrivateLetterListActivity extends BaseActivity {
    private RecyclerView rv_private_letter;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        rv_private_letter = (RecyclerView) findViewById(R.id.rv_private_letter);
        LinearLayoutManager privateLetterManager = new LinearLayoutManager(this);
        privateLetterManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        rv_private_letter.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));
        rv_private_letter.setLayoutManager(privateLetterManager);
        rv_private_letter.setItemAnimator(new DefaultItemAnimator());
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_private_letter_list;
    }

    public void initData(){
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("page_size","100");
        params.setData(map);
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .getPrivateNewsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<PrivateNewsListBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<PrivateNewsListBean>> listBaseModel) {
                        PrivateNewsListAdapter privateNewsListAdapter = new PrivateNewsListAdapter(PrivateLetterListActivity.this,listBaseModel.getData());
                        rv_private_letter.setAdapter(privateNewsListAdapter);
                        //创建默认的线性LayoutManager
                    }
                });
        addSubscription(subscription);
    }
}
