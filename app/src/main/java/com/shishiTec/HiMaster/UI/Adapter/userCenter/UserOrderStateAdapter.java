package com.shishiTec.HiMaster.UI.Adapter.userCenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pursue on 16/5/4.
 */
public class UserOrderStateAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ExpandableListView expandableListView;
    private ArrayList<String> parentItem;
    private UserOrderStateAdapterListener userOrderStateAdapterListener;
    public void setUserOrderStateAdapterListener(UserOrderStateAdapterListener userOrderStateAdapterListener){
        this.userOrderStateAdapterListener = userOrderStateAdapterListener;
    }

    public UserOrderStateAdapter(Context context, LayoutInflater inflater, ExpandableListView expandableListView, ArrayList<String> parentItem) {
        this.context = context;
        this.inflater = inflater;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder = null;
        if (parentViewHolder == null) {
            View view = inflater.inflate(R.layout.md_parent_layout, null);
            parentViewHolder = new ParentViewHolder(view);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (childViewHolder == null) {
            View view = inflater.inflate(R.layout.order_item, null);
            childViewHolder = new ChildViewHolder(view);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ParentViewHolder {
        @Bind(R.id.parent_title)
        TextView parentTitle;

        public ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {

        @Bind(R.id.order_user_logo)
        ImageView orderUserLogo;
        @Bind(R.id.order_user_nikeName)
        TextView orderUserNikeName;
        @Bind(R.id.click_user)
        LinearLayout clickUser;
        @Bind(R.id.t_order_state)
        TextView tOrderState;
        @Bind(R.id.jump_user)
        RelativeLayout jumpUser;
        @Bind(R.id.devide_line)
        ImageView devideLine;
        @Bind(R.id.order_image)
        ImageView orderImage;
        @Bind(R.id.order_course_name)
        TextView orderCourseName;
        @Bind(R.id.order_count_detail)
        TextView orderCountDetail;
        @Bind(R.id.normal_symbol)
        LinearLayout normalSymbol;
        @Bind(R.id.main_content)
        LinearLayout mainContent;
        @Bind(R.id.order_number)
        TextView orderNumber;
        @Bind(R.id.buy_time)
        TextView buyTime;
        @Bind(R.id.learn_time)
        TextView learnTime;
        @Bind(R.id.package_n)
        TextView packageN;
        @Bind(R.id.normal_user_item)
        LinearLayout normalUserItem;
        @Bind(R.id.delete_order)
        Button deleteOrder;
        @Bind(R.id.order_detail)
        Button orderDetail;
        @Bind(R.id.btn_wait_grade)
        Button btnWaitGrade;
        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public interface UserOrderStateAdapterListener{
        void gotoMasterCenter();
        void gotoCourseDetail();
        void deleteOrder();
        void gotoOrderDetail();
    }
}
