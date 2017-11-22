package com.shishiTec.HiMaster.UI.Adapter.discover;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.shishiTec.HiMaster.UI.Activity.MainActivity;

import java.util.List;

/**
 * Created by ywc
 */


public class CardBaseAdapter extends VBaseAdapter<SwipeBean, CardBaseAdapter.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    public CardBaseAdapter(Context context, List dataSource) {
        super(context, dataSource);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    protected ViewHolder createHolder(int position, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_swipe, parent,false);
        return new ViewHolder(view);
    }
    @Override
    protected void bindViewHoder(ViewHolder holder, int position, final SwipeBean data) {
        Log.i("ddd", "bindViewHoder: "+position);
        holder. nickname.setText(data.getNickname());
        //holder. num.setText(position+"/" + getCount());
        Glide.with(context)
                .load(ApiContact.IMAGE_URL2+data.getImg_0())
                .into(holder.image00);
        holder.age.setText(data.getAge());
        holder.province.setText(data.getProvince());
        holder.like_you.setText(data.getLike_you());
        holder.ranking.setText(data.getRanking());
        holder.image00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent(context,DiscoverUserDetailsInfo.class);
                intent0.putExtra("uid",data.getUid());
                context.startActivity(intent0);
            }
        });
    }

    static class ViewHolder extends VBaseAdapter.BaseViewHolder {
        private TextView nickname;
        private ImageView image00;
        private TextView age;
        private TextView province;
        private TextView like_you;
        private TextView ranking;
        public ViewHolder(View itemView) {
            super(itemView);
            nickname = getView(R.id.nickname);
            image00 = getView(R.id.image00);
            age = getView(R.id.age);
            province = getView(R.id.province);
            like_you = getView(R.id.like_you);
            ranking = getView(R.id.ranking);
        }
    }
}

