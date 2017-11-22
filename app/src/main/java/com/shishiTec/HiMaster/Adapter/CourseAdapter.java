package com.shishiTec.HiMaster.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.shishiTec.HiMaster.Model.bean.GetMasterCourseBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/5/10.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private Context context;
    private List<GetMasterCourseBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public CourseAdapter(Context context, List<GetMasterCourseBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_course, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final GetMasterCourseBean bean = list.get(position);
        BaseApplication.getInstance().loadImageView(context,holder.iv_course_cover,bean.getCover());
        holder.tv_store_name.setText(bean.getStore());
        holder.tv_course_name.setText(bean.getTitle());
        holder.tv_course_price.setText("￥"+bean.getPrice());
        holder.tv_km.setText(bean.getDistance());

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

        private ImageView iv_course_cover;
        private TextView tv_course_name;
        private TextView tv_course_price;
        private TextView tv_store_name;
        private TextView tv_km;

        public MyViewHolder(View view) {
            super(view);
            iv_course_cover = (ImageView) view.findViewById(R.id.iv_course_cover);
            tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
            tv_course_price = (TextView) view.findViewById(R.id.tv_course_price);
            tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
            tv_km = (TextView) view.findViewById(R.id.tv_km);
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
