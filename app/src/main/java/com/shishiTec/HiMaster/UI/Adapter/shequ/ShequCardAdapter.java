package com.shishiTec.HiMaster.UI.Adapter.shequ;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.shishiTec.HiMaster.Model.realbean.MyShequBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.CircleImageView;
import com.shishiTec.HiMaster.Utils.DateUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Pursue on 16/5/3.
 */
public class ShequCardAdapter extends RecyclerView.Adapter<ShequCardAdapter.ShequCardViewHolder> {


    private Context context;
    private List<MyShequBean> mData;
    private LayoutInflater inflater;
//    private OnItemClickListener onItemClickListener;
//    private OnFlipClickListener onFlipClickListener;
//    private OnFlipInfoClickListener onFlipInfoClickListener;

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public void setOnFlipClickListener(OnFlipClickListener onFlipClickListener) {
//        this.onFlipClickListener = onFlipClickListener;
//    }
//
//    public void setOnFlipInfoClickListener(OnFlipInfoClickListener onFlipInfoClickListener) {
//        this.onFlipInfoClickListener = onFlipInfoClickListener;
//    }

    public ShequCardAdapter(Context context, List<MyShequBean> mData) {
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ShequCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.myshequ_layout_item,parent,false);
        ShequCardViewHolder viewHolder = new ShequCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ShequCardViewHolder holder, final int position) {
        MyShequBean myshequcarddBean = mData.get(position);
        BaseApplication.getInstance().loadImageALLView(context, holder.userLogo, myshequcarddBean.getImg_top());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(myshequcarddBean.getAdd_time())));
        holder.userRiqi.setText(stringBuilder.toString());
        holder.userName.setText(myshequcarddBean.getNickname());
        holder.userTitle.setText(myshequcarddBean.getContent());
        BaseApplication.getInstance().loadImageALLView(context, holder.userImgWidth, myshequcarddBean.getOther_img_0());
        //图片集
        holder.badge.setBadgeNumber(Integer.parseInt(myshequcarddBean.getImg_nums()));
        holder.userPinlunNum.setText(myshequcarddBean.getComment_count());
        holder.userLikeNum.setText(myshequcarddBean.getLikes());

//        holder.flip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onFlipClickListener != null) {
//                    onFlipClickListener.OnFlipClick(position, holder.showFront, holder.showNegative);
//                }
//            }
//        });
//        holder.flipIntro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onFlipInfoClickListener != null) {
//                    onFlipInfoClickListener.OnFlipInfoClick(position, holder.showFront, holder.showNegative);
//                }
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.OnItemClick(position,holder.itemView);
//                }
//            }
//        });
//        holder.cardImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putExtra("card_id", mData.get(position).getCardId());
//                intent.setClass(context, MallCardDetailActtivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

//    c

    class ShequCardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.user_logo)
        CircleImageView userLogo;
        @Bind(R.id.user_title)
        TextView userTitle;
        @Bind(R.id.user_img_width)
        ImageView userImgWidth;
        @Bind(R.id.user_riqi)
        TextView userRiqi;
        @Bind(R.id.user_pinlun_num)
        TextView userPinlunNum;
        @Bind(R.id.imageView5)
        ImageView imageView5;
        @Bind(R.id.user_like_num)
        TextView userLikeNum;
        @Bind(R.id.imageView6)
        ImageView imageView6;

        Badge badge;

        public ShequCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            badge = new QBadgeView(context).bindTarget(userImgWidth);
            badge.setBadgeGravity(Gravity.BOTTOM| Gravity.END);

        }
    }
}
