package com.shishiTec.HiMaster.UI.Activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Model.realbean.PawuDeviceBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Activity.Accouont.RegisterActivity;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 83802 on 2017/8/16.
 */

public class Mypapa extends BaseActivity {


//    @Bind(R.id.top_left_back_btn)
//    ImageButton topLeftBackBtn;
//    @Bind(R.id.top_title)
//    TextView topTitle;
//    @Bind(R.id.top_center)
//    TextView topCenter;
    @Bind(R.id.rv_my_papa)
    RecyclerView rvMyPapa;
//    @Bind(R.id.button)
//    Button button;
    @Bind(R.id.btn_quding)
    Button button2;
    private Context context;
    private PhotoAdapter photoAdapter;
    private List<PawuDeviceBean> data;

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
//        context = this;
//        initRecyclerView();//
//        getPawuDevice();
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               //绑定设备 拿到ID
////               String id = data.get(photoAdapter.getItemCount()).getDevice_id();
////                bindPawuDevice(id);
//            }
//        });
//        //初始化标题栏
//        topTitle.setText("");
//        topCenter.setText("绑定我的设备");


    }

    @Override
    public int getLayoutId() {
        return R.layout.mypapa;
    }


    private void getPawuDevice() {

        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken("");
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        rx.Subscription subscription = RetrofitManager.getmInstance().createService()
                .getPawuDevice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<PawuDeviceBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<PawuDeviceBean>> listBaseModel) {
                        if (listBaseModel.getCode() == 200) {
                            data = listBaseModel.getData();
                            if (photoAdapter == null) {

                                PhotoAdapter photoAdapter = new PhotoAdapter(context, listBaseModel.getData());
                                rvMyPapa.setAdapter(photoAdapter);
                            } else {
                                photoAdapter.notifyDataSetChanged();
                            }
                        } else if (listBaseModel.getCode() == 1004) {
                            startActivity(new Intent(Mypapa.this, RegisterActivity.class));
                        }
                    }


                });
        addSubscription(subscription);

    }

    private void bindPawuDevice(String device_id) {
        Map<String, String> verifyMap = new HashMap<>();
        verifyMap.put("device_id", device_id);
        DeviceParams deviceParams = new DeviceParams();
        deviceParams.setToken("");
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(verifyMap);
        params.setRest_version("3.0");
        params.setSign(params.paramsSign());
        rx.Subscription subscription = RetrofitManager.getmInstance().createService()
                .bindPawuDevice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<PawuDeviceBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<PawuDeviceBean> pawuDeviceBeanBaseModel) {
                        if(pawuDeviceBeanBaseModel.getCode()==200){
                            //绑定成功
                        }
                    }
                });
        addSubscription(subscription);

    }

    private void initRecyclerView() {
        LinearLayoutManager hotManager = new LinearLayoutManager(this);
        hotManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //我的照片
        rvMyPapa.setLayoutManager(hotManager);
        rvMyPapa.setItemAnimator(new DefaultItemAnimator());
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

        private Context context;
        private List<PawuDeviceBean> list;


        public PhotoAdapter(Context context, List<PawuDeviceBean> list) {
            this.context = context;
            this.list = list;

        }
        @Override
        public PhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_pawu_photo, parent, false);
            return new PhotoAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            BaseApplication.getInstance().loadRoundImageView(context,holder.iv_photo_logo,list.get(position).getDevice_img());
            holder.pawu_name.setText(list.get(position).getDevice_name());

//            holder.iv_photo_logo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return null == list ? 0 : list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView iv_photo_logo;
            private TextView pawu_name;


            public MyViewHolder(View view) {
                super(view);
                iv_photo_logo = (ImageView) view.findViewById(R.id.iv_photo_logo);
                pawu_name = (TextView) view.findViewById(R.id.pawu_name);

            }
        }


    }
}
