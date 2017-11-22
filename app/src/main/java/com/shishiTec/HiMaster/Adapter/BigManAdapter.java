package com.shishiTec.HiMaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.BigManBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.bigManShare.BigManShareDetailsActivity;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.Utils.xImage;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BigManAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    private Context context;
    private Map<String, Object> bigMap;
    private List<BigManBean> bigManBean;
    private BigManItemAdapter mAdapter;
    private BaseApplication baseApplication;

    public BigManAdapter(Context context, List<BigManBean> bigManBean) {
        super();
        this.context = context;
        this.bigManBean = bigManBean;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return bigManBean.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //进行判断显示类型，来创建返回不同的View
        View view = View.inflate(viewGroup.getContext(), R.layout.bigman_fragment_layout_item, null);
        MViewHolder holder = new MViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
        if (mViewHolder instanceof MViewHolder) {
            ((MViewHolder) mViewHolder).user_name.setText(bigManBean.get(arg1).getNikename());
            ((MViewHolder) mViewHolder).user_number.setText(bigManBean.get(arg1).getBrowse_count() + "人看过");
            ((MViewHolder) mViewHolder).user_title.setText(bigManBean.get(arg1).getContent());
//            ((MViewHolder)mViewHolder).Zambia_num.setText(bigManBean.get(arg1).getLike_count());
            ((MViewHolder) mViewHolder).user_address.setText(bigManBean.get(arg1).getProvince() + " " + bigManBean.get(arg1).getCity());
            baseApplication.loadRoundImageView(context, ((MViewHolder) mViewHolder).user_logo, bigManBean.get(arg1).getImg_top());
            ((MViewHolder) mViewHolder).bigman_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BigManShareDetailsActivity.class);
                    intent.putExtra("share_id",bigManBean.get(arg1).getShare_id());
//                    Bundle bundle = new Bundle();
//                    bundle.putString("share_id", bigManBean.get(arg1).getShare_id());
//                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            if (bigManBean.get(arg1).getis_like() != "" && bigManBean.get(arg1).getis_like() != null) {
                int is_like = Integer.parseInt(bigManBean.get(arg1).getis_like());
                if (is_like == 0) {
                    ((MViewHolder) mViewHolder).zombia_status.setText("0");
                } else {
                    ((MViewHolder) mViewHolder).zombia_status.setText("1");
                }
            }
//                ((MViewHolder)mViewHolder).Zambia.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int s = Integer.parseInt(((MViewHolder)mViewHolder).zombia_status.getText().toString());
//                        if(s==0) {
//                            BigManZambia(bigManBean.get(arg1).getShare_id(),((MViewHolder)mViewHolder).Zambia_num.getText().toString().trim(),((MViewHolder)mViewHolder));
//                        }else {
//                            BigManCancle_Zambia(bigManBean.get(arg1).getShare_id(),((MViewHolder)mViewHolder).Zambia_num.getText().toString().trim(),((MViewHolder)mViewHolder));
//                        }
//                    }
//                });
            if (bigManBean.get(arg1).getDetail().size() > 1) {
                ((MViewHolder) mViewHolder).recyclerView.setVisibility(View.VISIBLE);
                ((MViewHolder) mViewHolder).user_img_width.setVisibility(View.GONE);
                ((MViewHolder) mViewHolder).user_img_height.setVisibility(View.GONE);
                //创建默认的线性LayoutManager
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((MViewHolder) mViewHolder).recyclerView.setLayoutManager(mLayoutManager);
                //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
                ((MViewHolder) mViewHolder).recyclerView.setHasFixedSize(true);
                //创建并设置Adapter
                mAdapter = new BigManItemAdapter(context, bigManBean.get(arg1).getDetail());
                ((MViewHolder) mViewHolder).recyclerView.setAdapter(mAdapter);
            } else if (bigManBean.get(arg1).getDetail().size() == 1) {
                ((MViewHolder) mViewHolder).url_img.setVisibility(View.VISIBLE);
                xImage.setImage(((MViewHolder) mViewHolder).url_img, bigManBean.get(arg1).getDetail().get(0).getUrl(), new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                        //获得ImageView中Image的真实宽高，
                        int dw = ((MViewHolder) mViewHolder).url_img.getDrawable().getBounds().width();
                        int dh = ((MViewHolder) mViewHolder).url_img.getDrawable().getBounds().height();
                        Log.d("lxy", "drawable_X = " + dw + ", drawable_Y = " + dh);
                        if (dw > dh) {
                            ((MViewHolder) mViewHolder).url_img.setVisibility(View.GONE);
                            ((MViewHolder) mViewHolder).recyclerView.setVisibility(View.GONE);
                            ((MViewHolder) mViewHolder).user_img_width.setVisibility(View.VISIBLE);
                            ((MViewHolder) mViewHolder).user_img_height.setVisibility(View.GONE);
                            xImage.setImage(((MViewHolder) mViewHolder).user_img_width, bigManBean.get(arg1).getDetail().get(0).getUrl());
                        } else {
                            ((MViewHolder) mViewHolder).url_img.setVisibility(View.GONE);
                            ((MViewHolder) mViewHolder).recyclerView.setVisibility(View.GONE);
                            ((MViewHolder) mViewHolder).user_img_width.setVisibility(View.GONE);
                            ((MViewHolder) mViewHolder).user_img_height.setVisibility(View.VISIBLE);
                            xImage.setImage(((MViewHolder) mViewHolder).user_img_height, bigManBean.get(arg1).getDetail().get(0).getUrl());
                        }
                    }
                });
            }
        }
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, user_title, user_number, user_address, Zambia_num, zombia_status;
        public ImageView user_logo, user_img_width, user_img_height, url_img;
        public RecyclerView recyclerView;
        public RelativeLayout Zambia;
        public LinearLayout bigman_item;

        public MViewHolder(View view) {
            super(view);
            this.user_name = (TextView) view.findViewById(R.id.user_name);
//            this.Zambia_num = (TextView) view.findViewById(R.id.Zambia_num);
            this.user_title = (TextView) view.findViewById(R.id.user_title);
            this.user_number = (TextView) view.findViewById(R.id.user_number);
            this.user_address = (TextView) view.findViewById(R.id.user_address);
            this.zombia_status = (TextView) view.findViewById(R.id.zombia_status);
            this.user_logo = (ImageView) itemView.findViewById(R.id.user_logo);
            this.url_img = (ImageView) itemView.findViewById(R.id.url_img);
            this.user_img_width = (ImageView) itemView.findViewById(R.id.user_img_width);
            this.user_img_height = (ImageView) itemView.findViewById(R.id.user_img_height);
            this.recyclerView = (RecyclerView) itemView.findViewById(R.id.user_imglist);
//            this.Zambia = (RelativeLayout) itemView.findViewById(R.id.Zambia);
            this.bigman_item = (LinearLayout) itemView.findViewById(R.id.bigman_item);
        }
    }

    /**
     * 用户点赞事件
     *
     * @param share_id   分享的列表项ID
     * @param like_count 点赞数量
     * @param holder     MViewHolder的对象
     */
    private void BigManZambia(String share_id, final String like_count, final MViewHolder holder) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("share_id", share_id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .BigManZambia(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            ToastUtils.showGravity(context, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 50);
                            int i = Integer.parseInt(like_count);
                            holder.Zambia_num.setText(i + 1 + "");
                            holder.zombia_status.setText("1");
                        } else {
                            ToastUtils.showGravity(context, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 50);
                        }
                    }
                });
    }

    /**
     * 用户取消点赞事件
     *
     * @param share_id   分享的列表项ID
     * @param like_count 点赞数量
     * @param holder     MViewHolder的对象
     */
    private void BigManCancle_Zambia(String share_id, final String like_count, final MViewHolder holder) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("share_id", share_id);
        DeviceParams deviceParams = new DeviceParams();
        BaseParams params = new BaseParams(deviceParams);
        params.setData(BigManMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        RetrofitManager.getmInstance().createService()
                .BigManCancle_Zambia(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            ToastUtils.showGravity(context, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 50);
                            int i = Integer.parseInt(like_count);
                            holder.Zambia_num.setText(i - 1 + "");
                            holder.zombia_status.setText("0");
                        } else {
                            ToastUtils.showGravity(context, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 50);
                        }
                    }
                });
    }

    //添加数据
    public void addMoreItem(List<BigManBean> newDatas) {
        bigManBean.addAll(newDatas);
        notifyDataSetChanged();
    }
}
