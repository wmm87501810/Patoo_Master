package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.ScoreDetailBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.MyMdDetailAdapter;
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

public class MDetailActivity extends BaseActivity {
    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.all_md)
    TextView allMd;
    @Bind(R.id.main_expandablelistview)
    ExpandableListView mainExpandablelistview;
    private int page = 1;
    private int page_size = 10;
    private ArrayList<String> parentItem = new ArrayList<>();
    private Map<String, List<ScoreDetailBean.Credit>> childItemMap = new HashMap<>();
    private MyMdDetailAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScore(page, page_size);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        topTitle.setText(getString(R.string.my_m_number));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mdetail;
    }


    /**
     * 获得M点详情明细
     *
     * @param page
     * @param page_size
     */
    private void getScore(int page, int page_size) {
        Map<String, String> scoreMap = new HashMap<>();
        scoreMap.put("page", page + "");
        scoreMap.put("page_size", page_size + "");
        params.setData(scoreMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .ScorceList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<ScoreDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel<ScoreDetailBean> scoreDetailBeanBaseModel) {
                        if (scoreDetailBeanBaseModel.getCode() == 200) {
                            ScoreDetailBean data = scoreDetailBeanBaseModel.getData();
                            initData(data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        addSubscription(subscribe);
    }

    private void initData(ScoreDetailBean data) {
        if (!data.getScore().equals("")) {
            allMd.setText(data.getScore());
        } else {
            allMd.setVisibility(View.GONE);
        }

        if (page == 1) {
            childItemMap.clear();
            parentItem.clear();
        }
        for (int i = 0; i < data.getDetail().size(); i++) {
            List<ScoreDetailBean.Credit> list = childItemMap.get(data.getDetail().get(i).getMonth());
            if (list == null) {
                list = new ArrayList<ScoreDetailBean.Credit>();
                childItemMap.put(data.getDetail().get(i).getMonth(), list);
                parentItem.add(data.getDetail().get(i).getMonth());
            }
            list.addAll(data.getDetail().get(i).getCredit());
        }
        adapter = new MyMdDetailAdapter(MDetailActivity.this, childItemMap, parentItem, mainExpandablelistview);
        mainExpandablelistview.setAdapter(adapter);
    }
}
