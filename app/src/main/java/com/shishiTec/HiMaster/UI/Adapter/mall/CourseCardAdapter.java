package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CardBean;
import com.shishiTec.HiMaster.Model.bean.CourseDetailBean;
import com.shishiTec.HiMaster.Model.bean.MasterDetailBean;
import com.shishiTec.HiMaster.Model.bean.SpecialBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/5/18.
 */
public class CourseCardAdapter extends RecyclerView.Adapter<CourseCardAdapter.MyViewHolder> {

    private Context context;
    private List<CourseDetailBean.CardBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public CourseCardAdapter(Context context, List<CourseDetailBean.CardBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_detail_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CourseDetailBean.CardBean bean = list.get(position);
        if(bean!=null){
            holder.tv_ka.setText(bean.getCard_name());
            holder.tv_card_brief.setText(bean.getCard_brief());
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

        private TextView tv_ka;
        private TextView tv_card_brief;

        public MyViewHolder(View view) {
            super(view);
            tv_ka = (TextView) view.findViewById(R.id.tv_ka);
            tv_card_brief = (TextView)view.findViewById(R.id.tv_card_brief);
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
