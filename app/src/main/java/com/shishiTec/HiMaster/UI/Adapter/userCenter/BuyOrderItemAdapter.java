package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shishiTec.HiMaster.Model.bean.OrderListBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CourseDetailActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BuyOrderItemAdapter extends RecyclerView.Adapter<BuyOrderItemAdapter.ItemViewHolder> {
    private Context context;
    private List<OrderListBean.DetailBean> detailBeen;
    private BaseApplication baseApplication;
    public DeviceParams device;
    public BaseParams params;

    public BuyOrderItemAdapter(Context context, List<OrderListBean.DetailBean> detailBeen) {
        super();
        this.context = context;
        this.detailBeen = detailBeen;
        baseApplication = BaseApplication.getInstance();
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }


    @Override
    public int getItemCount() {
        return detailBeen == null ? 0 : detailBeen.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_buy_order_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder mViewHolder, final int arg1) {
        int type = Integer.parseInt(detailBeen.get(arg1).getOrderStatus());
        switch (type) {
            case 0:
                setText(mViewHolder.buy_order_item_type, "待上课", Color.RED);
                mViewHolder.buy_order_item_delete.setVisibility(View.GONE);
                mViewHolder.buy_order_item_evaluate.setVisibility(View.GONE);
                mViewHolder.buy_order_item_see_curriculum.setVisibility(View.VISIBLE);
                break;
            case 1:
                setText(mViewHolder.buy_order_item_type, "已使用", Color.GRAY);
                mViewHolder.buy_order_item_delete.setVisibility(View.VISIBLE);
                mViewHolder.buy_order_item_evaluate.setVisibility(View.VISIBLE);
                mViewHolder.buy_order_item_see_curriculum.setVisibility(View.VISIBLE);
                break;
            case 2:
                setText(mViewHolder.buy_order_item_type, "已过期", Color.GRAY);
                break;
            case 3:
                setText(mViewHolder.buy_order_item_type, "已作废", Color.GRAY);
                mViewHolder.buy_order_item_delete.setVisibility(View.VISIBLE);
                mViewHolder.buy_order_item_evaluate.setVisibility(View.VISIBLE);
                mViewHolder.buy_order_item_see_curriculum.setVisibility(View.GONE);
                break;
            case 4:
                setText(mViewHolder.buy_order_item_type, "待接单", Color.RED);
                mViewHolder.buy_order_item_delete.setVisibility(View.GONE);
                mViewHolder.buy_order_item_evaluate.setVisibility(View.GONE);
                mViewHolder.buy_order_item_see_curriculum.setVisibility(View.VISIBLE);
                break;
            case 5:
                setText(mViewHolder.buy_order_item_type, "退款", Color.GRAY);
                mViewHolder.buy_order_item_delete.setVisibility(View.VISIBLE);
                mViewHolder.buy_order_item_evaluate.setVisibility(View.VISIBLE);
                mViewHolder.buy_order_item_see_curriculum.setVisibility(View.GONE);
                break;
        }
        mViewHolder.buy_order_item_name.setText(detailBeen.get(arg1).getNikeName());
        BaseApplication.getInstance().loadImageALLView(context, mViewHolder.buy_order_item_img_top, detailBeen.get(arg1).getCover());
        mViewHolder.buy_order_item_title.setText(detailBeen.get(arg1).getTitle());
        mViewHolder.buy_order_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOrder(detailBeen.get(arg1).getOrderId());
            }
        });
        mViewHolder.buy_order_item_see_curriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeCourseDetail(detailBeen.get(arg1).getCid());
            }
        });
        mViewHolder.buy_order_item_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare(detailBeen.get(arg1));
            }
        });
    }

    /**
     * 分享
     *
     * @param detailBean
     */
    private void showShare(OrderListBean.DetailBean detailBean) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(detailBean.getTitle());
        oks.setText(detailBean.getTitle());
        oks.setSite(context.getString(R.string.app_name));
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(context);
    }

    /**
     * 删除订单
     *
     * @param orderId
     */
    private void deleteOrder(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .deleteOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "订单删除失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseModel md) {
                        if (md.getCode() == 200) {
                            Toast.makeText(context, md.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * @param course_id
     */
    private void seeCourseDetail(String course_id) {
        Intent in = new Intent();
        in.putExtra("course_id", course_id);
        in.setClass(context, CourseDetailActivity.class);
        context.startActivity(in);

    }

    private void setText(TextView buy_order_item_type, String s, int color) {
        buy_order_item_type.setText(s);
        buy_order_item_type.setTextColor(color);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView buy_order_item_img_top;
        public TextView buy_order_item_name, buy_order_item_type, buy_order_item_title, buy_order_item_content, buy_order_item_content_price, buy_order_item_delete, buy_order_item_see_curriculum, buy_order_item_evaluate;

        public ItemViewHolder(View view) {
            super(view);
            this.buy_order_item_img_top = (ImageView) itemView.findViewById(R.id.buy_order_item_img_top);
            this.buy_order_item_name = (TextView) itemView.findViewById(R.id.buy_order_item_name);
            this.buy_order_item_type = (TextView) itemView.findViewById(R.id.buy_order_item_type);
            this.buy_order_item_title = (TextView) itemView.findViewById(R.id.buy_order_item_title);
            this.buy_order_item_content = (TextView) itemView.findViewById(R.id.buy_order_item_content);
            this.buy_order_item_content_price = (TextView) itemView.findViewById(R.id.buy_order_item_content_price);
            this.buy_order_item_delete = (TextView) itemView.findViewById(R.id.buy_order_item_delete);
            this.buy_order_item_see_curriculum = (TextView) itemView.findViewById(R.id.buy_order_item_see_curriculum);
            this.buy_order_item_evaluate = (TextView) itemView.findViewById(R.id.buy_order_item_evaluate);
        }
    }
}
