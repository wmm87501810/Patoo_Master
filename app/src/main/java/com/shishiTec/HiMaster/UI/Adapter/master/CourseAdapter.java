package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterDetailBean;
import com.shishiTec.HiMaster.Model.bean.MasterListsBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.master.MasterDetailActivity;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by hyu on 2016/6/2.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private Context context;
    private List<MasterDetailBean.ShareBean.DetailBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public CourseAdapter(Context context, List<MasterDetailBean.ShareBean.DetailBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_course_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MasterDetailBean.ShareBean.DetailBean detailBean = list.get(position);
        if(list!=null && list.size()>0){
            if(detailBean.getIntro().equals("")){
                holder.course_info_tv.setVisibility(View.GONE);
            }else{
                holder.course_info_tv.setText(detailBean.getIntro());
            }
            //内容类型 1、图片 2、视频
            if (detailBean.getMedia_type().equals("1")){
                BaseApplication.getInstance().loadImageALLView(context,holder.course_cover_iv,detailBean.getUrl());
            }
        }

        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, detailBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView course_cover_iv;
        private TextView  course_info_tv;

        public MyViewHolder(View view) {
            super(view);
            course_cover_iv = (ImageView) view.findViewById(R.id.course_cover_iv);
            course_info_tv = (TextView) view.findViewById(R.id.course_info_tv);
        }
    }

    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
