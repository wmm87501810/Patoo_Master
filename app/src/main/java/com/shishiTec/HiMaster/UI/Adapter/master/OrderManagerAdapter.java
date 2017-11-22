package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterOrderBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/6/4.
 */
public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.ItemViewHolder> {
    private Context context;
    private List<MasterOrderBean> list;

    public OrderManagerAdapter(Context context, List<MasterOrderBean> bigManBean) {
        super();
        this.context = context;
        this.list = bigManBean;
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_order_manager, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, int arg1) {
        //订单状态
        switch (Integer.parseInt(list.get(arg1).getOrderStatus())){
            case 0:
                mViewHolder.t_order_state.setText("待验单");
                break;
            case 1:
                mViewHolder.t_order_state.setText("已使用");
                break;
            case 2:
                mViewHolder.t_order_state.setText("已过期");
                break;
            case 3:
                mViewHolder.t_order_state.setText("无效");
                break;
            case 4:
                mViewHolder.t_order_state.setText("待接单");
                break;
            case 5:
                mViewHolder.t_order_state.setText("已拒单");
                break;
        }
        mViewHolder.order_user_nikeName.setText(list.get(arg1).getNikeName());
        mViewHolder.order_course_name.setText(list.get(arg1).getTitle());
        BaseApplication.getInstance().loadRoundImageView(context,mViewHolder.order_user_logo,list.get(arg1).getImgTop());
        BaseApplication.getInstance().loadRoundImageView(context,mViewHolder.order_image,list.get(arg1).getCover());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView t_order_state;
        private TextView order_course_name;
        private TextView order_count_detail;
        private TextView order_user_nikeName;
        private ImageView order_user_logo;
        private ImageView order_image;

        public ItemViewHolder(View view) {
            super(view);
            t_order_state = (TextView) view.findViewById(R.id.t_order_state);
            order_course_name = (TextView) view.findViewById(R.id.order_course_name);
            order_count_detail = (TextView) view.findViewById(R.id.order_count_detail);
            order_user_nikeName = (TextView) view.findViewById(R.id.order_user_nikeName);

            order_image = (ImageView) view.findViewById(R.id.order_image);
            order_user_logo = (ImageView) view.findViewById(R.id.order_user_logo);
        }
    }
}