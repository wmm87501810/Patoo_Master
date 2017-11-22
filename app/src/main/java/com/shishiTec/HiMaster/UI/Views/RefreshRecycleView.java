package com.shishiTec.HiMaster.UI.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hyu_anzhuo on 2016/5/4.
 */
public class RefreshRecycleView extends RecyclerView implements RecyclerView.RecyclerListener {


    public RefreshRecycleView(Context context) {
        super(context);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {

    }
}
