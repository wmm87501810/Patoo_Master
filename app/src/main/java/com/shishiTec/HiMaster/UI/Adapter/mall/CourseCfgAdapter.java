package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CourseCfgBean;
import com.shishiTec.HiMaster.Model.bean.CourseDetailBean;
import com.shishiTec.HiMaster.R;

import java.util.List;

/**
 * Created by hyu on 2016/5/18.
 */
public class CourseCfgAdapter extends RecyclerView.Adapter<CourseCfgAdapter.MyViewHolder> {

    private Context context;
    private List<CourseDetailBean.CourseCfgBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public CourseCfgAdapter(Context context, List<CourseDetailBean.CourseCfgBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_course_cfg, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CourseDetailBean.CourseCfgBean bean = list.get(position);
        if(bean!=null){
            holder.tv_price.setText("￥ "+bean.getPrice());
            holder.tv_custom_spec_name.setText(bean.getCustom_spec_name());
        }


        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_price;
        private TextView tv_custom_spec_name;

        public MyViewHolder(View view) {
            super(view);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_custom_spec_name = (TextView)view.findViewById(R.id.tv_custom_spec_name);
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
