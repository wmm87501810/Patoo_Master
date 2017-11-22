package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.PrivateNewsListBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Accouont.PrivateLetterActivity;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by hyu on 2016/5/23.
 */
public class PrivateNewsListAdapter extends RecyclerView.Adapter<PrivateNewsListAdapter.MyViewHolder>{

    private Context context;
    private List<PrivateNewsListBean> list;
    private Resources res;
    private OnItemClickListener listener;
    private int intType;


    public PrivateNewsListAdapter(Context context,List<PrivateNewsListBean>list){
            this.list=list;
            this.context=context;
            res=context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_comment_replay,parent,false);

            return new MyViewHolder(view);
            }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position){
            final PrivateNewsListBean privateNewsListBean=list.get(position);
            BaseApplication.getInstance().loadCircleImageView(context,holder.iv_img_top,privateNewsListBean.getImg_top());
            holder.tv_nike_name.setText(privateNewsListBean.getNikename());

            SpannableString spannableString= FileUtil.replaceFace(privateNewsListBean.getMessage());
            holder.tv_content.setText(spannableString,TextView.BufferType.SPANNABLE);

            holder.tv_comment_time.setText(privateNewsListBean.getAdd_time());
            holder.tv_reply.setVisibility(View.GONE);

    //        holder.tv_from.setText("来自Master达人");
            holder.al_reply_item.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                        Intent intent=new Intent(context,PrivateLetterActivity.class);
                        intent.putExtra("other_uid",privateNewsListBean.getUid());
                        context.startActivity(intent);
                }
            });
        }

    @Override
    public int getItemCount(){
            return null==list?0:list.size();
            }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img_top;
        private TextView tv_from;
        private TextView tv_reply;
        private TextView tv_content;
        private TextView tv_nike_name;
        private TextView tv_comment_time;
        private AutoLinearLayout al_reply_item;

        public MyViewHolder(View view) {
            super(view);
            tv_from = (TextView) view.findViewById(R.id.tv_from);
            tv_reply = (TextView) view.findViewById(R.id.tv_reply);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            iv_img_top = (ImageView) view.findViewById(R.id.iv_img_top);
            tv_nike_name = (TextView) view.findViewById(R.id.tv_nike_name);
            tv_comment_time = (TextView) view.findViewById(R.id.tv_comment_time);
            al_reply_item = (AutoLinearLayout) view.findViewById(R.id.al_reply_item);
        }
    }


    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object, View view);

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
