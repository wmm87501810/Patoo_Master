package com.shishiTec.HiMaster.UI.Adapter.bigman;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ItemViewHolder> {
    private Context context;
    private List<String> bigManItemBean;
    private BaseApplication baseApplication;
    private OnItemClickListener listener;
    public AddressAdapter(Context context, List<String> bigManItemBean) {
        super();
        this.context = context;
        this.bigManItemBean = bigManItemBean;
        baseApplication = BaseApplication.getInstance();
    }

    public void  addMoreData(List<String> bigManItemBean){
            this.bigManItemBean.addAll(bigManItemBean);
            notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return bigManItemBean.size();
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_address_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, final int arg1) {
        mViewHolder.item_tv.setText(bigManItemBean.get(arg1));
        /**
         * 调用接口回调
         */
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(arg1, bigManItemBean.get(arg1));
            }
        });
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;

        public ItemViewHolder(View view) {
            super(view);
            this.item_tv = (TextView) itemView.findViewById(R.id.item_tv);
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
