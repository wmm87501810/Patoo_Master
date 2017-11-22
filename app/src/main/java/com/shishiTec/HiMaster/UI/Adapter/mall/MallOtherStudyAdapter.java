package com.shishiTec.HiMaster.UI.Adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.OtherStudyBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.bigManShare.BigManHomePageActivity;
import com.shishiTec.HiMaster.Utils.UserPublicActionUtils;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by Pursue on 16/5/3.
 */
public class MallOtherStudyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态-默认为0
    //加载完成
    public static final int NO_MORE_DATA = 2;
    private int load_more_status = 2;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView
    private Context context;
    private List<OtherStudyBean> studyBeen;
    private OnItemClickListener onItemClickListener;
    private int is_fllow =0;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MallOtherStudyAdapter(Context context, List<OtherStudyBean> studyBeen) {
        this.context = context;
        this.studyBeen = studyBeen;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        //进行判断显示类型，来创建返回不同的View
        if (arg1 == TYPE_ITEM) {
            View view = View.inflate(parent.getContext(), R.layout.adapter_other_study, null);
            MallOtherStudyViewHolder viewHolder = new MallOtherStudyViewHolder(view);
            return viewHolder;
        } else if (arg1 == TYPE_FOOTER) {
            View foot_view = View.inflate(parent.getContext(), R.layout.load_more_footview_layout, null);
            MallOtherStudyAdapter.FootViewHolder footViewHolder = new MallOtherStudyAdapter.FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MallOtherStudyViewHolder) {
            BaseApplication.getInstance().loadImageALLView(context, ((MallOtherStudyViewHolder) holder).img_cover, studyBeen.get(position).getImg_top());
            ((MallOtherStudyViewHolder) holder).tv_study_title.setText(studyBeen.get(position).getNikename());
            ((MallOtherStudyViewHolder) holder).tv_study_content.setText(studyBeen.get(position).getIntro());
            if (studyBeen.get(position).getIs_follow().equals("0")) {
                is_fllow = 0;
                ((MallOtherStudyViewHolder) holder).study_people_img.setImageResource(R.mipmap.yellow_people);
            } else {
                is_fllow = 1;
                ((MallOtherStudyViewHolder) holder).study_people_img.setImageResource(R.mipmap.blue_people);
            }
            ((MallOtherStudyViewHolder)holder).rl_study_people.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (is_fllow == 0) {
                        is_fllow = 1;
                        UserPublicActionUtils.getInstance().user_follow(context,studyBeen.get(position).getUid());
                        ((MallOtherStudyViewHolder) holder).study_people_img.setImageResource(R.mipmap.blue_people);
                    }else{
                        is_fllow = 0;
                        UserPublicActionUtils.getInstance().user_cancel_follow(context,studyBeen.get(position).getUid());
                        ((MallOtherStudyViewHolder) holder).study_people_img.setImageResource(R.mipmap.yellow_people);
                    }
                }
            });
            ((MallOtherStudyViewHolder)holder).item_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("uid",studyBeen.get(position).getUid());
                    intent.setClass(context, BigManHomePageActivity.class);
                    context.startActivity(intent);
                }
            });
            ((MallOtherStudyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(position, view);
                    }
                }
            });

        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.footView_AR.setVisibility(View.VISIBLE);
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.footView_AR.setVisibility(View.VISIBLE);
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                case NO_MORE_DATA:
                    footViewHolder.footView_AR.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return studyBeen.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position, View view);
    }

    class MallOtherStudyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_study_title;
        ImageView img_cover;
        ImageView study_people_img;
        TextView tv_study_content;
        RelativeLayout rl_study_people;
        RelativeLayout item_rl;
        public MallOtherStudyViewHolder(View itemView) {
            super(itemView);
            study_people_img = (ImageView) itemView.findViewById(R.id.study_people_img);
            img_cover = (ImageView) itemView.findViewById(R.id.img_cover);
            tv_study_title = (TextView) itemView.findViewById(R.id.tv_study_title);
            tv_study_content = (TextView) itemView.findViewById(R.id.tv_study_content);
            rl_study_people = (RelativeLayout) itemView.findViewById(R.id.rl_study_people);
            item_rl = (RelativeLayout) itemView.findViewById(R.id.item_rl);
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;
        private AutoRelativeLayout footView_AR;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.load_next_page_text);
            footView_AR = (AutoRelativeLayout) view.findViewById(R.id.footView_AR);
        }
    }

    //添加数据
    public void addMoreItem(List<OtherStudyBean> newDatas) {
        studyBeen.addAll(newDatas);
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
