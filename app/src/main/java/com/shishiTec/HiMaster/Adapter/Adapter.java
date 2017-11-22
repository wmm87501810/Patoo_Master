package com.shishiTec.HiMaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.shishiTec.HiMaster.base.BaseActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyu on 2016/4/21.
 */
public abstract class Adapter<T> extends BaseAdapter {
    protected Activity mactivity;
    protected List<T> mlist;
    private int mlayout;
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }
    public Adapter(BaseActivity activity, List<T> list, int layout) {
        this.mactivity = activity;
        if(list!=null)
        {
            this.mlist = new ArrayList<T>(list);
        }
        this.mlayout = layout;
    }
    public Adapter(BaseActivity activity, List<T> list, int layout,Handler handler) {
        this(activity, list, layout);
        this.handler=handler;
    }
    @Override
    public int getCount() {
        return mlist != null ? mlist.size() : 0;
    }

    @Override
    public T getItem(int arg0) {
        return mlist != null ? mlist.get(arg0) : null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parantview) {
        ViewHolder vh = ViewHolder.get(mactivity, convertview, parantview, mlayout, position);
        getview(vh, position, mlist.get(position));
        getview(parantview, vh, position, mlist.get(position));
        return vh.getConvertView();
    };

    public abstract void getview(ViewHolder vh, int position, T T);
    public void getview(ViewGroup parantview,ViewHolder vh, int position, T T){};
    public void addFirst(T T)
    {
        if (T != null) {
            mlist.add(0,T);
            notifyDataSetChanged();
        }
    }
    public void add(T T) {
        if (T != null) {
            mlist.add(T);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<? extends T> list) {
        if (list != null && list.size() != 0) {
            mlist.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void remove(T T) {
        if (T != null) {
            mlist.remove(T);
            notifyDataSetChanged();
        }
    }

    public List<T> getList() {
        return mlist;
    }

    public void setMlist(List<T> list) {
        this.mlist = list;
        notifyDataSetChanged();
    }
    public void clear(boolean isupdate)
    {
        if(mlist!=null) {
            mlist.clear();
            if(isupdate)
                notifyDataSetChanged();
        }
    }
    public void clear()
    {
        clear(true);
    }

    public void replaceAll(List<T> list)
    {
        mlist.clear();
        addAll(list);
    }

    public static class ViewHolder {
        private final SparseArray<View> mViews;
        private View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId,
                    parent, false);
            AutoUtils.auto(mConvertView);
            // setTag
            mConvertView.setTag(this);
        }

        /**
         * 拿到一个ViewHolder对象
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return
         */
        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {

            if (convertView == null) {

                return new ViewHolder(context, parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();
        }

        /**
         * 通过控件的Id获取对于的控件，如果没有则加入views
         *
         * @param viewId
         * @return
         */
        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int viewId) {

            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }
    }

}