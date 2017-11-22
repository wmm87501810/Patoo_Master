package com.shishiTec.HiMaster.UI.Adapter.discover;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ywc
 */
public class CardItemAdapter extends VBaseAdapter<String,VBaseAdapter.BaseViewHolder>{
    public CardItemAdapter(Context context, List<String> dataSource) {
        super(context, dataSource);
    }

    @Override
    protected BaseViewHolder createHolder(int position, ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindViewHoder(BaseViewHolder holder, int position, String data) {

    }
}
