package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.NotificationBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.DateUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.List;

/**
 * Created by hyu on 2016/5/21.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context context;
    private List<NotificationBean> list;
    private Resources res;
    private OnItemClickListener listener;
    private int intType;


    public NotificationAdapter(Context context, List<NotificationBean> list) {
        this.list = list;
        this.context = context;
        res = context.getResources();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_notification, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final NotificationBean notificationListBean = list.get(position);

        if (notificationListBean.getPic_url()==null||notificationListBean.getPic_url().equals("")){
            holder.official_image.setVisibility(View.GONE);
        }else {
            holder.official_image.setVisibility(View.VISIBLE);
            BaseApplication.getInstance().loadImageView(context,holder.official_image,notificationListBean.getPic_url());
        }

//        String time = DateUtil.getMonthDay(Long.parseLong(notificationListBean.getAdd_time()));
        holder.official_time.setText(notificationListBean.getAdd_time());
        holder.official_title.setText(notificationListBean.getTitle());
        holder.official_intro.setText(notificationListBean.getContent());
        if(notificationListBean.getTarget_type() .equals("")||notificationListBean.getTarget_type() .equals("0") ){
            holder.show_about_more.setVisibility(View.GONE);
        }else{
            holder.show_about_more.setVisibility(View.VISIBLE);
        }

        /**
         * 调用接口回调
         */

        holder.ll_notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (notificationListBean.getTarget_type()!=null&&!notificationListBean.getTarget_type().equals("")){
                    intType = Integer.parseInt(notificationListBean.getTarget_type());
                }
//                Toast.makeText(context, intType+"", Toast.LENGTH_SHORT).show();
                Intent intent = null;
                /**
                 * intType
                 * 1：课程；2：达人；3：课程卡片；4：达人卡片；5：html5页面；6：课程关键字
                 */
                switch (intType){
                    case 1:
//                        intent = new Intent(context, CoursesDetailActivity.class);
//                        intent.putExtra("cid",notificationListBean.getTargetContent());
//                        context.startActivity(intent);
                        break;
                    case 2:
//                        intent = new Intent(context, OtherUserCenterActivity.class);
//                        intent.putExtra("fid",notificationListBean.getTargetContent());
//                        context.startActivity(intent);
                        break;
                    case 3:
//                        intent = new Intent(context, CourseCollectionActivity.class);
//                        intent.putExtra("cardId",notificationListBean.getTargetContent());
//                        context.startActivity(intent);
                        break;
                    case 4:
//                        intent = new Intent(context, MasterCollectionActivity.class);
//                        intent.putExtra("cardId",notificationListBean.getTargetContent());
//                        context.startActivity(intent);
                        break;
                    case 5:
//                        intent = new Intent(context, WebViewActivity.class);
//                        intent.putExtra("urlLink",notificationListBean.getTargetContent());
//                        intent.putExtra("url","officalUrl");
//                        intent.putExtra("title",notificationListBean.getTitle());
//                        context.startActivity(intent);
                        break;
                    case 6:
//                        intent = new Intent(context, SearchActivity.class);
//                        intent.putExtra("key",notificationListBean.getTargetContent());
//                        context.startActivity(intent);
                        break;
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView official_time;//官方消息时间
        private TextView official_title;//消息标题
        private ImageView official_image;//消息封面
        private TextView official_intro;//消息简介
        private RelativeLayout show_about_more;//了解更多
        private LinearLayout ll_notification;

        public MyViewHolder(View view) {
            super(view);
            official_time = (TextView) view.findViewById(R.id.official_time);
            official_title = (TextView) view.findViewById(R.id.official_title);
            official_intro = (TextView) view.findViewById(R.id.official_intro);
            ll_notification = (LinearLayout)view.findViewById(R.id.ll_notification);
            show_about_more = (RelativeLayout) view.findViewById(R.id.show_about_more);
            official_image = (ImageView) view.findViewById(R.id.official_image);

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

}
