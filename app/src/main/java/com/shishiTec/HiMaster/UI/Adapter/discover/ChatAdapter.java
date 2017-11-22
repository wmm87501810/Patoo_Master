package com.shishiTec.HiMaster.UI.Adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.realbean.ChatInfoBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.CircleImageView;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 83802 on 2017/8/25.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseAdapter> {

    private List<ChatInfoBean> dataList = new ArrayList<>();
    private Context context;

    public ChatAdapter(Context context, List<ChatInfoBean> mlist) {
        this.context = context;

        this.dataList =mlist;

    }

    @Override
    public ChatAdapter.BaseAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ChatAViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_a, parent, false));
            case 1:
                return new ChatBViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_b, parent, false));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(ChatAdapter.BaseAdapter holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(dataList.get(position).getIs_sender());
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseAdapter extends RecyclerView.ViewHolder {

        public BaseAdapter(View itemView) {
            super(itemView);
        }

        void setData(Object object) {

        }
    }

    private class ChatAViewHolder extends BaseAdapter {
        private CircleImageView ic_user;
        private TextView tv;

        public ChatAViewHolder(View view) {
            super(view);
            ic_user = (CircleImageView) itemView.findViewById(R.id.ic_user);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatInfoBean chatinfo = (ChatInfoBean) object;
            BaseApplication.getInstance().loadImageALLView(context, ic_user, chatinfo.getImg_top());
            //Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            tv.setText(chatinfo.getContent());
        }
    }

    private class ChatBViewHolder extends BaseAdapter {
        private CircleImageView ic_user;
        private TextView tv;

        public ChatBViewHolder(View view) {
            super(view);
            ic_user = (CircleImageView) itemView.findViewById(R.id.ic_user);
            tv = (TextView) itemView.findViewById(R.id.tv);

        }

        @Override
        void setData(Object object) {
            super.setData(object);
            ChatInfoBean chatinfo = (ChatInfoBean) object;
            BaseApplication.getInstance().loadImageALLView(context, ic_user, chatinfo.getImg_top());
            //Picasso.with(itemView.getContext()).load(model.getIcon()).placeholder(R.mipmap.ic_launcher).into(ic_user);
            tv.setText(chatinfo.getContent());
        }
    }
}
