package com.shishiTec.HiMaster.UI.Adapter.face;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 仇杰 on 2015/3/28 0028.
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {

    protected List<T> list;
    protected Context context;
    protected LayoutInflater mInflater;

    public AppBaseAdapter(Context context, List<T> list){
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    public List<T> getData(){
        return list;
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) { return position;}

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
