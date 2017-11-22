package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MCourseBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/5/4.
 */
public class MpAdapter extends RecyclerView.Adapter<MpAdapter.MDViewHolder> {
    private List<MCourseBean> mData;
    private Context context;

    public MpAdapter(Context context, List<MCourseBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.m_course_item, null);
        MDViewHolder holder = new MDViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MDViewHolder holder, int position) {
        MCourseBean mCourseBean = mData.get(position);
        BaseApplication.getInstance().loadRoundImageView(context,holder.courseImg,mCourseBean.getCover());
        holder.courseName.setText(mCourseBean.getTitle());
        holder.price.setText(mCourseBean.getM_price()+"M");
        holder.address.setText(mCourseBean.getStore());
        holder.distance.setText(mCourseBean.getDistance());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MDViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.course_img)
        ImageView courseImg;
        @Bind(R.id.course_name)
        TextView courseName;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.distance)
        TextView distance;
        @Bind(R.id.address)
        TextView address;
        public MDViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
