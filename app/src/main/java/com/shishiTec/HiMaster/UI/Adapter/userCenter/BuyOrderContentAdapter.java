package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.OrderListBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;
import java.util.Map;


public class BuyOrderContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Map<String, Object> bigMap;
    private List<OrderListBean> orderListBeen;
    private BuyOrderItemAdapter mAdapter;
    private BaseApplication baseApplication;
    private LinearLayoutManager manager;

    public BuyOrderContentAdapter(Context context, List<OrderListBean> orderListBeen) {
        super();
        this.context = context;
        this.orderListBeen = orderListBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return orderListBeen == null ? 0 : orderListBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_buy_order, null);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
        ((MViewHolder) mViewHolder).buyOrderTime.setText(orderListBeen.get(arg1).getDay());
        manager = new LinearLayoutManager(context);
        ((MViewHolder) mViewHolder).buyOrderItemRecycler.setLayoutManager(manager);
        ((MViewHolder) mViewHolder).buyOrderItemRecycler.setHasFixedSize(true);
        mAdapter = new BuyOrderItemAdapter(context, orderListBeen.get(arg1).getDetail());
        ((MViewHolder) mViewHolder).buyOrderItemRecycler.setAdapter(mAdapter);
    }


    public static class MViewHolder extends RecyclerView.ViewHolder {
        public TextView buyOrderTime;
        public RecyclerView buyOrderItemRecycler;

        public MViewHolder(View view) {
            super(view);
            buyOrderTime = (TextView) view.findViewById(R.id.buyOrderTime);
            buyOrderItemRecycler = (RecyclerView) view.findViewById(R.id.buyOrderItemRecycler);
        }
    }

    //添加数据
    public void addMoreItem(List<OrderListBean> newDatas) {
        orderListBeen.addAll(newDatas);
        notifyDataSetChanged();
    }
}
