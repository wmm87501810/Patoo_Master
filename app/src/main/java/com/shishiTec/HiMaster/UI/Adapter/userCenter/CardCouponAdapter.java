package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CardOrderBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.DateUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/5/3.
 */
public class CardCouponAdapter extends RecyclerView.Adapter<CardCouponAdapter.MallCouponViewHolder> {
    private Context context;
    private List<CardOrderBean.ConditionBean.CouponBean> mData;
    private OnItemClickListener onItemClickListener;

    public CardCouponAdapter(Context context, List<CardOrderBean.ConditionBean.CouponBean> mData) {
        this.context = context;
        this.mData = mData;
    }


    @Override
    public MallCouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.coupon_item, null);
        MallCouponViewHolder holder = new MallCouponViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MallCouponViewHolder holder, final int position) {
        CardOrderBean.ConditionBean.CouponBean couponBean = mData.get(position);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(couponBean.getPrice());
        stringBuilder.append("<font><small><small><small>元</small></small></small></font>");
        holder.couponPrice.setText(Html.fromHtml(stringBuilder.toString()));
        StringBuilder sb = new StringBuilder();
        sb.append("最低支付");
        sb.append(couponBean.getLimit_price());
        sb.append("元");
        holder.limitPrice.setText(sb.toString());
        holder.couponName.setText(couponBean.getCoupon_name());
        holder.beginTime.setText(DateUtil.getYearMonthDay(Long.parseLong(couponBean.getStart_used_time())) + "至");
        holder.endTime.setText(DateUtil.getYearMonthDay(Long.parseLong(couponBean.getEnd_used_time())) + "有效");
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

    class MallCouponViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.coupon_price)
        TextView couponPrice;
        @Bind(R.id.limit_price)
        TextView limitPrice;
        @Bind(R.id.coupon_name)
        TextView couponName;
        @Bind(R.id.xuxian)
        ImageView xuxian;
        @Bind(R.id.beginTime)
        TextView beginTime;
        @Bind(R.id.endTime)
        TextView endTime;

        public MallCouponViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
