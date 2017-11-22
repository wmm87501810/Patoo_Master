package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.PaySureBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.AppUtils;
import com.shishiTec.HiMaster.Utils.DateUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/5/3.
 */
public class MallCardAdapter extends RecyclerView.Adapter<MallCardAdapter.MallCardViewHolder> {

    private Context context;
    private List<PaySureBean.ConditionBean.CardBean> mData;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MallCardAdapter(Context context, List<PaySureBean.ConditionBean.CardBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MallCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.soy_card_item, null);
        MallCardViewHolder viewHolder = new MallCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MallCardViewHolder holder, final int position) {
        PaySureBean.ConditionBean.CardBean soyCardBean = mData.get(position);
        BaseApplication.getInstance().loadImageALLView(context, holder.cardImg, soyCardBean.getCardUrl());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("使用期限:");
        stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(soyCardBean.getBeginTime())));
        stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(soyCardBean.getEndTime())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.OnItemClick(position,view);
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
        @Bind(R.id.flip_intro)
        ImageButton flipIntro;
        @Bind(R.id.use_intro)
        TextView useIntro;
        @Bind(R.id.show_negative)
        RelativeLayout showNegative;
        @Bind(R.id.card_img)
        ImageView cardImg;
        @Bind(R.id.flip)
        TextView flip;
        @Bind(R.id.date_limit)
        TextView dateLimit;
        @Bind(R.id.show_front)
        RelativeLayout showFront;
        public MallCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
