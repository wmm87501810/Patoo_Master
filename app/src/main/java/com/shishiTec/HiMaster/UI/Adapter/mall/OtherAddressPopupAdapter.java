package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CourseDetailBean;
import com.shishiTec.HiMaster.R;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/17.
 */
public class OtherAddressPopupAdapter extends RecyclerView.Adapter<OtherAddressPopupAdapter.ItemViewHolder> {
    private Context context;
    private List<CourseDetailBean.CourseStoreBean> infoConfBeen;
    private OnItemClickListener listener;

    public OtherAddressPopupAdapter(Context context, List<CourseDetailBean.CourseStoreBean> infoConfBeen) {
        super();
        this.context = context;
        this.infoConfBeen = infoConfBeen;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.other_address_popup_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.tv_store_name.setText(infoConfBeen.get(position).getStore());
        holder.tv_address.setText(infoConfBeen.get(position).getAddress());
        holder.tv_address1.setVisibility(View.GONE);
        holder.tv_address2.setVisibility(View.GONE);
        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(position, infoConfBeen);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (infoConfBeen != null) {
            return infoConfBeen.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_store_name, tv_address,tv_address1,tv_address2;

        public ItemViewHolder(View view) {
            super(view);
            tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_address1 = (TextView) view.findViewById(R.id.tv_address1);
            tv_address2 = (TextView) view.findViewById(R.id.tv_address2);
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
