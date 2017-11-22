package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.InfoConfBean;
import com.shishiTec.HiMaster.R;

import java.util.List;

/**
 * Created by Pursue on 16/5/3.
 */
public class MallCityAdapter extends RecyclerView.Adapter<MallCityAdapter.MallCardViewHolder> {

    private Context context;
    private List<InfoConfBean.CityListBean> mData;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MallCityAdapter(Context context, List<InfoConfBean.CityListBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MallCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_mall_city_item, null);
        MallCardViewHolder viewHolder = new MallCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MallCardViewHolder holder, final int position) {
        holder.popupWindow_city.setText(mData.get(position).getCity_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(position, view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position, View view);
    }

    class MallCardViewHolder extends RecyclerView.ViewHolder {
        TextView popupWindow_city;

        public MallCardViewHolder(View itemView) {
            super(itemView);
            popupWindow_city = (TextView) itemView.findViewById(R.id.popupWindow_city);
        }
    }
}
