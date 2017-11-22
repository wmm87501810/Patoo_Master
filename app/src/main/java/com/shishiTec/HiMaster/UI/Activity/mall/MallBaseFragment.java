package com.shishiTec.HiMaster.UI.Activity.mall;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.shishiTec.HiMaster.Model.bean.MasterStartBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MallBaseFragment extends Fragment {
    public DeviceParams device;
    public BaseParams params;
    private List<MasterStartBean.CategoryListBean> ms;
    protected View mMainView;
    protected static ArrayList<Map<String, Object>> mlistItems;
    protected Context mContext;
    protected ViewPager mListView;
    protected SlidingTabLayout tl_mall;
    protected LinearLayoutManager linearLayoutManager;
    List<Fragment> list;
    protected ImageView screen;
    protected MallHomeActivity activity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_one, container, false);
        initDeviceData();
        tl_mall = (SlidingTabLayout) mMainView.findViewById(R.id.tl_mall);
        mListView = (ViewPager) mMainView.findViewById(R.id.list);
        list = new ArrayList<>();
        activity =  (MallHomeActivity) getActivity();
        return mMainView;
    }
    //初始化设备信息
    private void initDeviceData(){
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }

    public void setMarginTop(int page){
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
        mListView.setLayoutParams(layoutParam);
        mListView.invalidate();
    }
}
