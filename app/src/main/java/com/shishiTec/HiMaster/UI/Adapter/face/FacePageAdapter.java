package com.shishiTec.HiMaster.UI.Adapter.face;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FacePageAdapter extends PagerAdapter {

    private final List<View> pageViews;

    public FacePageAdapter(List<View> pageViews){

        this.pageViews = pageViews;
    }
    @Override
    public int getCount() {
        return pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = pageViews.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pageViews.get(position));
    }
}
