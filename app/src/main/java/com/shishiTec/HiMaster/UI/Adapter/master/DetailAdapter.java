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

import com.shishiTec.HiMaster.Model.bean.MasterDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/4/28.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {

    private Context context;
    private List<MasterDetailBean.CommentListBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public DetailAdapter(Context context, List<MasterDetailBean.CommentListBean> list) {
        this.context = context;
        this.list = list;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_master_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MasterDetailBean.CommentListBean bean = list.get(position);
        if(bean!=null){
            holder.tv_master_user_name.setText(bean.getNikename());
            SpannableString spannableString = FileUtil.replaceFace(bean.getContent());
            holder.tv_master_user_content.setText(spannableString,TextView.BufferType.SPANNABLE);
            BaseApplication.getInstance().loadCircleImageView(context,holder.iv_master_detail_user_logo,bean.getImg_top());
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

        private ImageView iv_master_detail_user_logo;
        private TextView tv_master_user_name;
        private TextView tv_master_user_content;

        public MyViewHolder(View view) {
            super(view);
            tv_master_user_name = (TextView) view.findViewById(R.id.tv_master_user_name);
            iv_master_detail_user_logo = (ImageView) view.findViewById(R.id.iv_master_detail_user_logo);
            tv_master_user_content = (TextView)view.findViewById(R.id.tv_master_user_content);
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
