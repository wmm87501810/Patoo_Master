package com.shishiTec.HiMaster.UI.Adapter.master;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.shishiTec.HiMaster.Model.bean.MediaListBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/5/6.
 */
public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.MyViewHolder> {

    private Context context;
    private List<MediaListBean> list;
    private Resources res;
    private OnItemClickListener listener;

    public UploadAdapter(Context context, List<MediaListBean> list) {
        this.list = list;
        this.context = context;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_master_upload, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MediaListBean bean = list.get(position);
        if(bean!=null && !bean.getUrl().equals("")){
            BaseApplication.getInstance().loadImageALLView(context,holder.iv_master_upload_content,bean.getUrl());
            holder.et_master_upload_content.setText(bean.getIntro());
        }

        /**
         * 调用接口回调
         */

        holder.iv_master_upload_content.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onItemClick(position, bean,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_master_upload_content;
        private EditText et_master_upload_content;

        public MyViewHolder(View view) {
            super(view);
            et_master_upload_content = (EditText) view.findViewById(R.id.et_master_upload_content);
            iv_master_upload_content = (ImageView) view.findViewById(R.id.iv_master_upload_content);
        }
    }



    /**
     * 内部接口回调方法
     */
    public interface OnItemClickListener {
        void onItemClick(int position, Object object,View view);
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void onChageDataList(List<MediaListBean> listBeen){
        this.list = listBeen;
    }

    public void addData(int position) {
        list.add(position,new MediaListBean("",""));
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

}
