package com.shishiTec.HiMaster.UI.fragment.master;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.master.TabFragmentAdapter;
import com.shishiTec.HiMaster.base.BaseFragment;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Pursue on 16/4/6.
 */
public class MasterFragment extends BaseFragment{
    private SlidingTabLayout tl_master;
    private ViewPager vp_master;
    private TabFragmentAdapter mta;
    private List<MasterStartBean.CategoryListBean> ms;

    @Override
    protected int getLayoutResId() {
        return R.layout.master_fragment_layout;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
            initView();
    }

    private void initView(){
        tl_master = (SlidingTabLayout) getContentView().findViewById(R.id.tl_master);
        vp_master = (ViewPager) getContentView().findViewById(R.id.vp_master);
        initMasterStart();
    }

    /**
     * 首页数据初始化
     */
    private void initMasterStart(){
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .masterStart(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MasterStartBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<MasterStartBean> mb) {
                        if (mb.getCode() == 200) {
                                ms = mb.getData().getCategory_list();
                                List<Fragment> list = new ArrayList<>();
                            //分类列表
                            if(ms!=null && ms.size()>0){
                                for (int i=0;i<ms.size();i++){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("category_id",ms.get(i).getId());
                                    com.shishiTec.HiMaster.UI.fragment.master.contentFragment mcf = new com.shishiTec.HiMaster.UI.fragment.master.contentFragment();
                                    mcf.setArguments(bundle);
                                    list.add(mcf);
                                }
                                Log.i("MasterCategory:",ms.get(0).getId());
                                mta = new TabFragmentAdapter(getFragmentManager(),list,ms);
                                vp_master.setAdapter(mta);
                                vp_master.setOffscreenPageLimit(2);
                                tl_master.setViewPager(vp_master);
                            }
                        }

                    }
                });
        addSubscription(subscribe);
    }
}
