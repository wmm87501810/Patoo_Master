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
 * Created by hyu_anzhuo on 2016/5/17.
 */
public class ScreenPopupRightAdapter extends RecyclerView.Adapter<ScreenPopupRightAdapter.ItemViewHolder> {
    private Context context;
    private List<InfoConfBean.SelectTypeBean> selectTypeBeen;
    private OnItemClickListener listener;

    public ScreenPopupRightAdapter(Context context, List<InfoConfBean.SelectTypeBean> selectTypeBeen) {
        super();
        this.context = context;
        this.selectTypeBeen = selectTypeBeen;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.screen_popup_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.tv_popup_item.setText(selectTypeBeen.get(position).getItem_name());
        /**
         * 调用接口回调
         */
        holder.tv_popup_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(position, holder.tv_popup_item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectTypeBeen == null ? 0 : selectTypeBeen.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_popup_item;

        public ItemViewHolder(View view) {
            super(view);
            tv_popup_item = (TextView) view.findViewById(R.id.tv_popup_item);
        }
    }

    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, TextView v);
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
