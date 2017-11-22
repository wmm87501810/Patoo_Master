package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CourseDetailBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.xImage;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CourseShareItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MASTER = 100;  //MASTER View
    private static final int BIGMAN = 200;  //BIGMAN View
    private Context context;
    private Map<String, Object> bigMap;
    private List<CourseDetailBean.ShareBean.ListBean> myShareBeen = new ArrayList<>();
    private CourseShareImageItemAdapter mAdapter;
    private BaseApplication baseApplication;
    private List<String> list;

    public CourseShareItemAdapter(Context context, List<CourseDetailBean.ShareBean.ListBean> myShareBeen) {
        super();
        this.context = context;
        this.myShareBeen = myShareBeen;
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public int getItemCount() {
        return myShareBeen == null ? 0 : myShareBeen.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (myShareBeen.get(position).getMaster() == "master" || myShareBeen.get(position).getMaster().equals("master")) {
            return MASTER;
        } else {
            return BIGMAN;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        //进行判断显示类型，来创建返回不同的View
        if (arg1 == MASTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_master_comment, viewGroup, false);
            MasterViewHolder masterHolder = new MasterViewHolder(view);
            return masterHolder;
        } else if (arg1 == BIGMAN) {
            View view = View.inflate(viewGroup.getContext(), R.layout.bigman_fragment_layout_item, null);
            BigManViewHolder bigManHolder = new BigManViewHolder(view);
            return bigManHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mViewHolder, final int arg1) {
        if (mViewHolder instanceof MasterViewHolder) {
            //内容区图片
            BaseApplication.getInstance().loadRoundImageView(context, ((MasterViewHolder) mViewHolder).iv_content_img, myShareBeen.get(arg1).getCover());
            //头像
            BaseApplication.getInstance().loadRoundImageView(context, ((MasterViewHolder) mViewHolder).iv_content_logo, myShareBeen.get(arg1).getImg_top());
            ((MasterViewHolder) mViewHolder).tv_content_nikname.setText(myShareBeen.get(arg1).getNikename());
            ((MasterViewHolder) mViewHolder).tv_content_type_name.setText(myShareBeen.get(arg1).getTag_name());
            ((MasterViewHolder) mViewHolder).tv_content_liulan.setText(myShareBeen.get(arg1).getBrowse_count());
            ((MasterViewHolder) mViewHolder).tv_content_info.setText(myShareBeen.get(arg1).getContent());
            ((MasterViewHolder) mViewHolder).tv_content_title.setText(myShareBeen.get(arg1).getTitle());
        } else if (mViewHolder instanceof BigManViewHolder) {
            ((BigManViewHolder) mViewHolder).user_name.setText(myShareBeen.get(arg1).getNikename());
            ((BigManViewHolder) mViewHolder).user_title.setText(myShareBeen.get(arg1).getTitle());
            baseApplication.loadRoundImageView(context, ((BigManViewHolder) mViewHolder).user_logo, myShareBeen.get(arg1).getImg_top());
            final String imgUrl = myShareBeen.get(arg1).getImg_url();
            String[] arrayStr = new String[]{};
            arrayStr = imgUrl.split(",");
            list = java.util.Arrays.asList(arrayStr);
            if (list != null && list.size() > 1) {
                ((BigManViewHolder) mViewHolder).recyclerView.setVisibility(View.VISIBLE);
                ((BigManViewHolder) mViewHolder).user_img_width.setVisibility(View.GONE);
                ((BigManViewHolder) mViewHolder).user_img_height.setVisibility(View.GONE);
                //创建默认的线性LayoutManager
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((BigManViewHolder) mViewHolder).recyclerView.setLayoutManager(mLayoutManager);
                //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
                ((BigManViewHolder) mViewHolder).recyclerView.setHasFixedSize(true);
                //创建并设置Adapter
                mAdapter = new CourseShareImageItemAdapter(context, list);
                ((BigManViewHolder) mViewHolder).recyclerView.setAdapter(mAdapter);
            } else if (list != null && list.size() == 1) {
                ((BigManViewHolder) mViewHolder).url_img.setVisibility(View.VISIBLE);
                xImage.setImage(((BigManViewHolder) mViewHolder).url_img, list.get(0), new Callback.CommonCallback<Drawable>() {
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
                        int dw = ((BigManViewHolder) mViewHolder).url_img.getDrawable().getBounds().width();
                        int dh = ((BigManViewHolder) mViewHolder).url_img.getDrawable().getBounds().height();
                        Log.d("lxy", "drawable_X = " + dw + ", drawable_Y = " + dh);
                        if (dw > dh) {
                            ((BigManViewHolder) mViewHolder).url_img.setVisibility(View.GONE);
                            ((BigManViewHolder) mViewHolder).recyclerView.setVisibility(View.GONE);
                            ((BigManViewHolder) mViewHolder).user_img_width.setVisibility(View.VISIBLE);
                            ((BigManViewHolder) mViewHolder).user_img_height.setVisibility(View.GONE);
                            xImage.setImage(((BigManViewHolder) mViewHolder).user_img_width, list.get(arg1));
                        } else {
                            ((BigManViewHolder) mViewHolder).url_img.setVisibility(View.GONE);
                            ((BigManViewHolder) mViewHolder).recyclerView.setVisibility(View.GONE);
                            ((BigManViewHolder) mViewHolder).user_img_width.setVisibility(View.GONE);
                            ((BigManViewHolder) mViewHolder).user_img_height.setVisibility(View.VISIBLE);
                            xImage.setImage(((BigManViewHolder) mViewHolder).user_img_height, list.get(arg1));
                        }
                    }
                });
            }
        }
    }


    public class MasterViewHolder extends RecyclerView.ViewHolder {
        private AutoLinearLayout ll_master_content;
        private ImageView iv_content_img;
        private ImageView iv_content_logo;
        private ImageView iv_content_liulan;
        private TextView tv_content_liulan;
        private TextView tv_content_info;
        private TextView tv_content_title;
        private TextView tv_content_nikname;
        private TextView tv_content_type_name;

        public MasterViewHolder(View view) {
            super(view);
            ll_master_content = (AutoLinearLayout) view.findViewById(R.id.ll_master_content);
            iv_content_img = (ImageView) view.findViewById(R.id.iv_content_img);
            iv_content_logo = (ImageView) view.findViewById(R.id.iv_content_logo);
            iv_content_liulan = (ImageView) view.findViewById(R.id.iv_content_liulan);
            tv_content_info = (TextView) view.findViewById(R.id.tv_content_info);
            tv_content_title = (TextView) view.findViewById(R.id.tv_content_title);
            tv_content_nikname = (TextView) view.findViewById(R.id.tv_content_nikname);
            tv_content_liulan = (TextView) view.findViewById(R.id.tv_content_liulanshu);
            tv_content_type_name = (TextView) view.findViewById(R.id.tv_content_type_name);
        }
    }

    public static class BigManViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, user_title, user_number, user_address, zombia_status;
        public ImageView user_logo, user_img_width, user_img_height, url_img;
        public RecyclerView recyclerView;
        public RelativeLayout Zambia;
        public LinearLayout bigman_item;

        public BigManViewHolder(View view) {
            super(view);
            this.user_name = (TextView) view.findViewById(R.id.user_name);
            this.user_title = (TextView) view.findViewById(R.id.user_title);
            this.user_number = (TextView) view.findViewById(R.id.user_number);
            this.user_address = (TextView) view.findViewById(R.id.user_address);
            this.zombia_status = (TextView) view.findViewById(R.id.zombia_status);
            this.user_logo = (ImageView) itemView.findViewById(R.id.user_logo);
            this.url_img = (ImageView) itemView.findViewById(R.id.url_img);
            this.user_img_width = (ImageView) itemView.findViewById(R.id.user_img_width);
            this.user_img_height = (ImageView) itemView.findViewById(R.id.user_img_height);
            this.recyclerView = (RecyclerView) itemView.findViewById(R.id.user_imglist);
            this.bigman_item = (LinearLayout) itemView.findViewById(R.id.bigman_item);
        }
    }

}
