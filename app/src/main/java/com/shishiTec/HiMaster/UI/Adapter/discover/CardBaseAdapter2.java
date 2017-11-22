package com.shishiTec.HiMaster.UI.Adapter.discover;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishiTec.HiMaster.Model.realbean.SwipeBean;
import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.DiscoverUserDetailsInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ywc
 */


public class CardBaseAdapter2 extends RecyclerView.Adapter<CardBaseAdapter2.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<SwipeBean> mData;


    public CardBaseAdapter2(Context context, List<SwipeBean> mData) {
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_swipe, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SwipeBean data = mData.get(position);
        holder.nickname.setText(data.getNickname());
        //holder. num.setText(position+"/" + getCount());
        Glide.with(context)
                .load(ApiContact.IMAGE_URL2 + data.getImg_0())
                .into(holder.image00);
        holder.age.setText(data.getAge());
        holder.province.setText(data.getProvince());
        holder.like_you.setText(data.getLike_you());
        holder.ranking.setText(data.getRanking());
        holder.image00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent(context, DiscoverUserDetailsInfo.class);
                intent0.putExtra("uid", data.getUid());
                context.startActivity(intent0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public ImageView image00;
        public TextView age;
        public TextView province;
        public TextView like_you;
        public TextView ranking;
         public  ImageView ivLove;
         public  ImageView ivDel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            image00 = (ImageView) itemView.findViewById(R.id.image00);
            age = (TextView) itemView.findViewById(R.id.age);
            province = (TextView) itemView.findViewById(R.id.province);
            like_you = (TextView) itemView.findViewById(R.id.like_you);
            ivLove = (ImageView) itemView.findViewById(R.id.iv_love);
            ivDel = (ImageView) itemView.findViewById(R.id.iv_del);
        }
    }
}

