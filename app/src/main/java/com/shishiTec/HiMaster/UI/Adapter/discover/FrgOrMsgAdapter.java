package com.shishiTec.HiMaster.UI.Adapter.discover;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.shishiTec.HiMaster.Model.realbean.MyFriendBean;
import com.shishiTec.HiMaster.Model.realbean.MyFriendMsgBean;

import com.shishiTec.HiMaster.Model.realbean.TestEvent;
import com.shishiTec.HiMaster.Net.ApiContact;
import com.shishiTec.HiMaster.R;

import com.shishiTec.HiMaster.UI.Activity.ChatActivity;
import com.shishiTec.HiMaster.UI.Activity.DiscoverUserDetailsInfo;
import com.shishiTec.HiMaster.UI.Views.CircleImageView;
import com.shishiTec.HiMaster.UI.Views.SlideDelete;


import com.shishiTec.HiMaster.base.BaseApplication;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import java.util.List;
import java.util.ListIterator;


import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Pursue on 16/5/3.
 */
public class FrgOrMsgAdapter extends RecyclerView.Adapter<FrgOrMsgAdapter.FrgOrMsgHolder> {


    private Context context;
    private List<MyFriendBean> mData = null;
    private List<MyFriendMsgBean> mData2 = null;
    private LayoutInflater inflater;
    private String way = "0";
    private List<SlideDelete> slideDeleteArrayList = new ArrayList<>();


    public FrgOrMsgAdapter(Context context, List<MyFriendBean> mData, String way) {
        this.context = context;
        this.mData = mData;
        this.way = way;
        inflater = LayoutInflater.from(context);
    }

    public FrgOrMsgAdapter(Context context, List<MyFriendMsgBean> mData2) {
        this.context = context;
        this.mData2 = mData2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FrgOrMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_myfriormsg, parent, false);
        FrgOrMsgHolder viewHolder = new FrgOrMsgHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( FrgOrMsgHolder holder, final int position) {
        if(way.equals("1")) {//好友
            MyFriendBean myFriendBean = mData.get(position);
            BaseApplication.getInstance().loadImageALLView(context, holder.userLogo, myFriendBean.getImg_top());
            holder.userName.setText(myFriendBean.getNickname());
            holder.layMyage.setVisibility(View.VISIBLE);
            holder.age.setText(myFriendBean.getAge());
            holder.diqu.setText(myFriendBean.getCity());
            holder.content.setText(myFriendBean.getContent());

            holder.mLlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new TestEvent(ApiContact.KEY_DELETEFRIEND,mData.get(position).getUid()));
                }
            });
            holder.mSlideDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ChatActivity.class);
                    intent.putExtra("uid",mData.get(position).getUid());
                    context.startActivity(intent);

                }
            });

        }else{
            MyFriendMsgBean myFriendMsgBean = mData2.get(position);
            BaseApplication.getInstance().loadImageALLView(context, holder.userLogo, myFriendMsgBean.getImg_top());
            holder.userName.setText(myFriendMsgBean.getNickname());
            holder.layMyage.setVisibility(View.GONE);
            holder.content.setText(myFriendMsgBean.getContent());
            holder.mLlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new TestEvent(ApiContact.KEY_DELETEFRIENDMSG,mData2.get(position).getId()));
                }
            });
            holder.userLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ChatActivity.class);
                    intent.putExtra("uid",mData2.get(position).getUid());
                    context.startActivity(intent);

                }
            });
        }
        holder.mSlideDelete.setOnSlideDeleteListener(new SlideDelete.OnSlideDeleteListener() {
            @Override
            public void onOpen(SlideDelete slideDelete) {
                closeOtherItem();
                slideDeleteArrayList.add(slideDelete);
            }

            @Override
            public void onClose(SlideDelete slideDelete) {
                slideDeleteArrayList.remove(slideDelete);
            }
        });

    }
    private void closeOtherItem(){
        // 采用Iterator的原因是for是线程不安全的，迭代器是线程安全的
        ListIterator<SlideDelete> slideDeleteListIterator = slideDeleteArrayList.listIterator();
        while(slideDeleteListIterator.hasNext()){
            SlideDelete slideDelete = slideDeleteListIterator.next();
            slideDelete.isShowDelete(false);
        }
        slideDeleteArrayList.clear();
    }






    @Override
    public int getItemCount() {
        if(way.equals("1")) {
            return mData != null ? mData.size() : 0;
        } else{
            return mData2 != null ? mData2.size() : 0;
        }
    }

//    c

    class FrgOrMsgHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.user_logo)
        CircleImageView userLogo;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.iv_sex)
        ImageView ivSex;
        @Bind(R.id.age)
        TextView age;
        @Bind(R.id.diqu)
        TextView diqu;
        @Bind(R.id.lay_myage)
        LinearLayout layMyage;
        @Bind(R.id.LlDelete)
        LinearLayout mLlDelete;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.mSlideDelete)
        SlideDelete mSlideDelete;

        public FrgOrMsgHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
