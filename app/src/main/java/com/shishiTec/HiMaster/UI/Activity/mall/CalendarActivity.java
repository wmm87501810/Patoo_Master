package com.shishiTec.HiMaster.UI.Activity.mall;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.bean.CalendarBean;
import com.shishiTec.HiMaster.Model.bean.DateNumberBean;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.mall.CalendarUtil.CalendarCard;
import com.shishiTec.HiMaster.UI.Activity.mall.CalendarUtil.CalendarViewAdapter;
import com.shishiTec.HiMaster.UI.Activity.mall.CalendarUtil.CustomDate;
import com.shishiTec.HiMaster.UI.Views.pickerview.OptionsPickerView;
import com.shishiTec.HiMaster.Utils.DateUtils;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CalendarActivity extends Activity implements OnClickListener, CalendarCard.OnCellClickListener {
    private ViewPager mViewPager;
    private int mCurrentIndex = 498;
    private CalendarCard[] mShowViews;
    private CalendarViewAdapter<CalendarCard> adapter;
    private SildeDirection mDirection = SildeDirection.NO_SILDE;
    private AutoLinearLayout RL1, RL2;
    private String course_cfg_id;
    public DeviceParams device;
    public BaseParams params;
    private List<CalendarBean> calendarBeen;
    private ArrayList<DateNumberBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private View vMasker;
    private OptionsPickerView pvOptions;
    private String course_id;

    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;
    }

    private TextView left_back, tvCurrentMonth1, tvCurrentMonth2;

    //初始化设备信息
    private void initDeviceData() {
        device = new DeviceParams();
        params = new BaseParams(device);
        params.setDevice(device);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);
        initDeviceData();
        Intent intent = getIntent();
        vMasker = findViewById(R.id.vMasker);
        course_cfg_id = intent.getStringExtra("course_cfg_id");
        course_id = intent.getStringExtra("course_id");
        RL1 = (AutoLinearLayout) findViewById(R.id.RL1);
        RL2 = (AutoLinearLayout) findViewById(R.id.RL2);
        left_back = (TextView) this.findViewById(R.id.left_back);
        tvCurrentMonth1 = (TextView) this.findViewById(R.id.tvCurrentMonth1);
        tvCurrentMonth2 = (TextView) this.findViewById(R.id.tvCurrentMonth2);
        left_back.setOnClickListener(this);
        initData();
        //选项选择器
        pvOptions = new OptionsPickerView(CalendarActivity.this);

        WindowManager wm = (WindowManager) CalendarActivity.this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        width = (int) (width * 1.0 * 5 / 7);
        ViewGroup.LayoutParams params = RL1.getLayoutParams();
        params.height = width;
        RL1.setLayoutParams(params);

        params = RL2.getLayoutParams();
        params.height = width;
        RL2.setLayoutParams(params);
    }

    /**
     * 获取接口日历数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("course_cfg_id", course_cfg_id);
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .calendar(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<CalendarBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<List<CalendarBean>> mb) {
                        if (mb.getCode() == 200) {
                            calendarBeen = mb.getData();
                            CalendarCard[] views = new CalendarCard[2];
                            for (int i = 0; i < 2; i++) {
                                views[i] = new CalendarCard(CalendarActivity.this, CalendarActivity.this, calendarBeen, null);
                            }
                            mShowViews = views;
//                            RL1.addView(mShowViews[0]);
                            WindowManager wm = (WindowManager) CalendarActivity.this
                                    .getSystemService(Context.WINDOW_SERVICE);
                            int width = wm.getDefaultDisplay().getWidth();
                            RL1.addView(mShowViews[0], width, width);
                            mShowViews[1].rightSlide();
                            RL2.addView(views[1], width, width);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void clickDate(CustomDate date) {
        RL1.removeView(mShowViews[0]);
        RL2.removeView(mShowViews[1]);
        CalendarCard[] views = new CalendarCard[2];
        for (int i = 0; i < 2; i++) {
            views[i] = new CalendarCard(CalendarActivity.this, CalendarActivity.this, calendarBeen, date);
        }
        mShowViews = views;
        WindowManager wm = (WindowManager) CalendarActivity.this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        RL1.addView(mShowViews[0], width, width);
        mShowViews[1].rightSlide();
        RL2.addView(views[1], width, width);
        chooses(date);
    }

    private void chooses(final CustomDate date) {
        Map<String, String> map = new HashMap<>();
        map.put("course_cfg_id", course_cfg_id);
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .calendar(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<CalendarBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<List<CalendarBean>> mb) {
                        if (mb.getCode() == 200) {
                            String d = DateUtils.getTime(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
                            for (int w = 0; w < mb.getData().size(); w++) {
                                String s = mb.getData().get(w).getStart_date();
                                if (s.equals(d)) {
                                    chooseTime(mb.getData().get(w).getTime_list(), date);
                                    pvOptions.show();
                                    break;
                                }
                            }
                        }
                    }
                });

    }

    private void chooseTime(List<CalendarBean.TimeListBean> time_list, final CustomDate date) {
        options1Items.clear();
        options2Items.clear();
        for (int w = 0; w < time_list.size(); w++) {
            ArrayList<String> options2Items_0 = new ArrayList<String>();
            //选项1
            options1Items.add(new DateNumberBean(Integer.parseInt(time_list.get(w).getId()), time_list.get(w).getStart_time() + "-" + time_list.get(w).getEnd_time(), "", ""));
            if (time_list.get(w).getRemain_num().equals("0")) {
                options2Items_0.add("0");
            } else {
                for (int m = 1; m <= Integer.parseInt(time_list.get(w).getRemain_num()); m++) {
                    options2Items_0.add(m + "");
                }
            }
            options2Items.add(options2Items_0);
        }
        //联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        pvOptions.setTitle("");
        pvOptions.setCyclic(false, false, false);
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                Intent intent = new Intent(CalendarActivity.this, OrderPaymentActivity.class);
                intent.putExtra("course_id", course_id);
                intent.putExtra("course_cfg_id", course_cfg_id);
                intent.putExtra("dates", date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                intent.putExtra("choose_time", options1Items.get(options1).getPickerViewText());
                intent.putExtra("num", options2Items.get(options1).get(option2) + "");
                intent.putExtra("time_id", options1Items.get(options1).getId() + "");
                intent.putExtra("is_mpay", "0");
                startActivity(intent);
                CalendarActivity.this.finish();
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(option2);
//                Toast.makeText(CalendarActivity.this,tx,Toast.LENGTH_SHORT).show();
                vMasker.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            CalendarActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void changeDate(CustomDate date) {
        tvCurrentMonth1.setText(date.month - 1 + "月");
        tvCurrentMonth2.setText((date.month) + "月");
    }
}
