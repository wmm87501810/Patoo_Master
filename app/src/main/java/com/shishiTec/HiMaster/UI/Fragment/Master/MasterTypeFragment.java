package com.shishiTec.HiMaster.UI.fragment.master;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shishiTec.HiMaster.Adapter.RecyclerAdapter;
import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseFragment;

import java.util.List;

/**
 * Created by hyu on 2016/4/22.
 */
public class MasterTypeFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<MasterStartBean.CategoryListBean> list;
    private RecyclerAdapter recyclerAdapter;

    public MasterTypeFragment(List<MasterStartBean.CategoryListBean> list){
        this.list = list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_master_type;
    }

    @Override
    protected void onInflated(View contentView, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getContentView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new RecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
//                startActivity(new Intent(getActivity(), TwoActivity.class));
            }
        });

    }
}
