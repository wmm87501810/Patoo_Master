package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.SoyCardBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.MallCardDetailActtivity;
import com.shishiTec.HiMaster.Utils.DateUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/5/3.
 */
public class SoyCardAdapter extends RecyclerView.Adapter<SoyCardAdapter.SoyCardViewHolder> {

    private Context context;
    private List<SoyCardBean> mData;
    private OnItemClickListener onItemClickListener;
    private OnFlipClickListener onFlipClickListener;
    private OnFlipInfoClickListener onFlipInfoClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnFlipClickListener(OnFlipClickListener onFlipClickListener) {
        this.onFlipClickListener = onFlipClickListener;
    }

    public void setOnFlipInfoClickListener(OnFlipInfoClickListener onFlipInfoClickListener) {
        this.onFlipInfoClickListener = onFlipInfoClickListener;
    }

    public SoyCardAdapter(Context context, List<SoyCardBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public SoyCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.soy_card_item, null);
        SoyCardViewHolder viewHolder = new SoyCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SoyCardViewHolder holder, final int position) {
        SoyCardBean soyCardBean = mData.get(position);
        BaseApplication.getInstance().loadImageALLView(context, holder.cardImg, soyCardBean.getCardUrl());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("使用期限:");
        stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(soyCardBean.getBeginTime())));
        stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(soyCardBean.getEndTime())));
        holder.dateLimit.setText(stringBuilder.toString());
        holder.useIntro.setText(soyCardBean.getCardDesc());
        holder.flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFlipClickListener != null) {
                    onFlipClickListener.OnFlipClick(position, holder.showFront, holder.showNegative);
                }
            }
        });
        holder.flipIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFlipInfoClickListener != null) {
                    onFlipInfoClickListener.OnFlipInfoClick(position, holder.showFront, holder.showNegative);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(position,holder.itemView);
                }
            }
        });
        holder.cardImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("card_id", mData.get(position).getCardId());
                intent.setClass(context, MallCardDetailActtivity.class);
                context.startActivity(intent);
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

    public interface OnFlipClickListener {
        void OnFlipClick(int position, RelativeLayout showFront, RelativeLayout showNegative);
    }

    public interface OnFlipInfoClickListener {
        void OnFlipInfoClick(int position, RelativeLayout showFront, RelativeLayout showNegative);
    }

    class SoyCardViewHolder extends RecyclerView.ViewHolder {
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

        public SoyCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
