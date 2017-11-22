package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shishiTec.HiMaster.Model.bean.PrivateNewsListDetailBean;
import com.shishiTec.HiMaster.Model.bean.ShareDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.CircleImageView;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/5/23.
 */
public class PrivateLetterAdapter extends RecyclerView.Adapter<PrivateLetterAdapter.MyViewHolder>{

    private Context context;
    private List<PrivateNewsListDetailBean> list;
    private Resources res;
    private OnItemClickListener listener;
    private int intType;


    public PrivateLetterAdapter(Context context,List<PrivateNewsListDetailBean>list){
        this.list=list;
        this.context=context;
        res=context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_private_letter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position){
        PrivateNewsListDetailBean  privateNewsListDetailBean = list.get(position);
//        ToastUtils.show(context,"UID:"+SharedPreferencesUtil.getInstance(),ToastUtils.LENGTH_LONG);
        Log.i("Uid","UID:"+SharedPreferencesUtil.getInstance());
        Log.i("Uid","BeanUID："+privateNewsListDetailBean.getSendUid());
        if(privateNewsListDetailBean.getSendUid().equals(SharedPreferencesUtil.getInstance().getUid())){
            holder.send_layout.setVisibility(View.VISIBLE);
            holder.accept_layout.setVisibility(View.GONE);
            BaseApplication.getInstance().loadImageView(context,holder.send_head,  privateNewsListDetailBean.getSendImgTop());
            BaseApplication.getInstance().loadImageView(context,holder.accept_head,  privateNewsListDetailBean.getAcceptImgTop());
            if (privateNewsListDetailBean.getType().equals("1")) {
                //文字
                /** 替换表情字符 */
                String content = privateNewsListDetailBean.getContent();
                SpannableString spannableString = FileUtil.replaceFace(content);
                holder.send_content.setText(spannableString,TextView.BufferType.SPANNABLE);
//                holder.send_content.setText(spannableString,TextView.BufferType.SPANNABLE);

                holder.send_content.setVisibility(View.VISIBLE);
                holder.send_img.setVisibility(View.GONE);
            } else {
                //图片
//                Bitmap smallBitmap = ImageUtils.getSmallBitmap( privateUserMessageBean.getContent());
//                holder.send_img.setImageBitmap(smallBitmap);
                BaseApplication.getInstance().loadImageView(context,holder.send_img,privateNewsListDetailBean.getContent());
                holder.send_content.setVisibility(View.INVISIBLE);
                holder.send_img.setVisibility(View.VISIBLE);
                //点击图片放大
                holder.send_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }else{

            holder.send_layout.setVisibility(View.GONE);
            holder.accept_layout.setVisibility(View.VISIBLE);
            BaseApplication.getInstance().loadImageView(context,holder.send_head,privateNewsListDetailBean.getAcceptImgTop());
            BaseApplication.getInstance().loadImageView(context,holder.accept_head,privateNewsListDetailBean.getSendImgTop());
            if (privateNewsListDetailBean.getType().equals("1")) {
                //文字

                /** 替换表情字符 */
                String content = privateNewsListDetailBean.getContent();
                SpannableString spannableString = FileUtil.replaceFace(content);
                holder.accept_content.setText(spannableString,TextView.BufferType.SPANNABLE);
                holder.accept_content.setVisibility(View.VISIBLE);
                holder.accept_img.setVisibility(View.GONE);
            } else {
                //图片
                BaseApplication.getInstance().loadImageView(context,holder.accept_img,  privateNewsListDetailBean.getContent());
                holder.accept_content.setVisibility(View.GONE);
                holder.accept_img.setVisibility(View.VISIBLE);
                holder.accept_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount(){
        return null==list?0:list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout accept_layout;
        private RelativeLayout send_layout;
        private CircleImageView accept_head;
        private CircleImageView send_head;
        private TextView accept_content;
        private TextView send_content;
        private ImageView send_img;
        private ImageView accept_img;

        public MyViewHolder(View view) {
            super(view);
            accept_layout = (RelativeLayout) view.findViewById(R.id.accept_layout);
            send_layout = (RelativeLayout) view.findViewById(R.id.send_layout);
            accept_head = (CircleImageView) view.findViewById(R.id.accept_head);
            send_head = (CircleImageView) view.findViewById(R.id.send_head);
            accept_content = (TextView) view.findViewById(R.id.accept_content);
            send_content = (TextView) view.findViewById(R.id.send_content);
            send_img = (ImageView) view.findViewById(R.id.send_img);
            accept_img = (ImageView) view.findViewById(R.id.accept_img);
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