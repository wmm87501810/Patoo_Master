package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.MasterListsBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.AutoSpliteTextView;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.Utils.UserPublicActionUtils;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;


/**
 * Created by hyu on 2016/4/25.
 */
public class HotMasterAdapter extends RecyclerView.Adapter<HotMasterAdapter.MyViewHolder> {

    private Context context;
    private List<MasterListsBean.MasterListBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public HotMasterAdapter(Context context, List<MasterListsBean.MasterListBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_master_hot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MasterListsBean.MasterListBean bean = list.get(position);
        if(list!=null && list.size()>0){
            holder.tv_hot_name.setText(list.get(position).getNikename());
            //替换表情字符
            SpannableString spannableString = FileUtil.replaceFace(list.get(position).getIntro());
            holder.tv_hot_content.setText(spannableString,TextView.BufferType.SPANNABLE);

            BaseApplication.getInstance().loadRoundImageView(context,holder.iv_hot_logo,list.get(position).getImg_top());
            if(list.get(position).getIs_follow().equals("1")){
                holder.tv_hot_attention.setText("已关注");
                holder.tv_hot_attention.setBackgroundResource(R.color.white);
            }else{
                holder.tv_hot_attention.setText("关注");
                holder.tv_hot_attention.setBackgroundResource(R.color.default_yellow);
            }
            holder.tv_hot_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.tv_hot_attention.getText().toString().equals("已关注")){
                        UserPublicActionUtils.getInstance().user_cancel_follow(context,list.get(position).getUid());
                        holder.tv_hot_attention.setText("关注");
                        holder.tv_hot_attention.setBackgroundResource(R.color.default_yellow);
                    }else{
                        UserPublicActionUtils.getInstance().user_follow(context,list.get(position).getUid());
                        holder.tv_hot_attention.setText("已关注");
                        holder.tv_hot_attention.setBackgroundResource(R.color.white);
                    }
                }
            });

        }

        /**
         * 调用接口回调
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_hot_logo;
        private TextView tv_hot_name;
        private TextView  tv_hot_content;
        private TextView  tv_hot_attention;

        public MyViewHolder(View view) {
            super(view);
            iv_hot_logo = (ImageView) view.findViewById(R.id.iv_hot_logo);
            tv_hot_name = (TextView) view.findViewById(R.id.tv_hot_name);
            tv_hot_content = (TextView) view.findViewById(R.id.tv_hot_content);
            tv_hot_attention = (TextView) view.findViewById(R.id.tv_hot_attention);
        }
    }

    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
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
