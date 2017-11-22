package com.shishiTec.HiMaster.UI.Activity.bigManShare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.baidu.location.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.*;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.bigman.AddressAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PeripheryActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    public LocationClient mLocationClient = null;
    private List<String> mlist = new ArrayList<String>();
    public BDLocationListener myListener = new MyLocationListener();
    private LinearLayoutManager linearLayoutManager;
    private AddressAdapter addressAdapter;
    private TextView tv_search;
    private EditText edit_search;
    //    private SuggestionSearch mSuggestionSearch;
//    private PoiSearch poiSearch;
    private GeoCoder mSearch;
    private ImageView img_left, img_search;
    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private String city, p, a;
    private double la, lo;
    private LinearLayout ll_img_search, ll_search;
    private List<String> stringList = new ArrayList<String>();
    private int lastVisibleItem;
    private ReverseGeoCodeResult r;
    private int n = 1;
    private TextView not_address, now_city;
    private PoiCitySearchOption mPoiSearch;
    private SuggestionSearch mSuggestionSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }


    @Override
    protected void initViews() {
        mSuggestionSearch = SuggestionSearch.newInstance();
        OnGetSuggestionResultListener onGetSuggestionResultListener = new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {
                if (res == null || res.getAllSuggestions() == null) {
                    Toast.makeText(PeripheryActivity.this, "未找到相关结果", Toast.LENGTH_SHORT).show();
                    return;
                    //未找到相关结果
                } else {
                    for (int i = 0; i < res.getAllSuggestions().size(); i++) {
                        stringList.add(res.getAllSuggestions().get(i).district + "(" + res.getAllSuggestions().get(i).key + ")");
                    }
                    addressAdapter = new AddressAdapter(PeripheryActivity.this, stringList);
                    recyclerView.setAdapter(addressAdapter);
                }
                //获取在线建议检索结果
            }
        };
        mSuggestionSearch.setOnGetSuggestionResultListener(onGetSuggestionResultListener);
        mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    result.toString();
                }
                //获取地理编码结果
                result.toString();
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                }
                r = result;
                if (n == 1) {
                    mlist.clear();
                    for (int i = 0; i < result.getPoiList().size(); i++) {
                        mlist.add(result.getPoiList().get(i).address + "(" + result.getPoiList().get(i).name + ")");
                    }

                    addressAdapter = new AddressAdapter(PeripheryActivity.this, mlist);
                    recyclerView.setAdapter(addressAdapter);
                    addressAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, Object object) {
                            Intent intent = new Intent();
                            intent.putExtra("city", city);
                            intent.putExtra("province", p);
                            intent.putExtra("longitude", lo + "");
                            intent.putExtra("latitude", la + "'");
                            intent.putExtra("address", mlist.get(position));
                            PeripheryActivity.this.setResult(RESULT_OK, intent);
                            PeripheryActivity.this.finish();
                        }
                    });
                } else {
                    for (int i = 0; i < result.getPoiList().size(); i++) {
                        mlist.add(result.getPoiList().get(i).address + "(" + result.getPoiList().get(i).name + ")");
                    }
                    if (mlist != null && mlist.size() > 0) {
                        addressAdapter.addMoreData(mlist);
                    } else {
                        return;
                    }
                }
            }
        };
        now_city = (TextView) findViewById(R.id.now_city);
        not_address = (TextView) findViewById(R.id.not_address);
        now_city.setOnClickListener(this);
        not_address.setOnClickListener(this);
        mSearch.setOnGetGeoCodeResultListener(listener);
        ll_img_search = (LinearLayout) findViewById(R.id.ll_img_search);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        edit_search = (EditText) findViewById(R.id.edit_search);
        img_left = (ImageView) findViewById(R.id.img_left);
        img_search = (ImageView) findViewById(R.id.img_search);
        tv_search = (TextView) findViewById(R.id.tv_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.periphery_swiperefreshlayout);
        //在些处添加
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
        linearLayoutManager = new LinearLayoutManager(PeripheryActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        img_left.setOnClickListener(this);
        img_search.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        initClick();
    }

    private void initClick() {
        swipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                n = 2;
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(r.getPoiList().get(5).location.latitude, r.getPoiList().get(5).location.longitude)));
                swipeRefreshLayout.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                n = 1;
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(r.getPoiList().get(9).location.latitude, r.getPoiList().get(9).location.longitude)));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_big_man_periphery;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_search:
                String s = edit_search.getText().toString().trim();
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(s).city(city));
                break;
            case R.id.img_left:
                this.finish();
                break;
            case R.id.img_search:
                ll_img_search.setVisibility(View.GONE);
                ll_search.setVisibility(View.VISIBLE);
                break;
            case R.id.now_city:
                intent.putExtra("city", city);
                intent.putExtra("province", p);
                intent.putExtra("longitude", lo + "");
                intent.putExtra("latitude", la + "'");
                intent.putExtra("address", city);
                PeripheryActivity.this.setResult(RESULT_OK, intent);
                PeripheryActivity.this.finish();
                break;
            case R.id.not_address:
                intent.putExtra("city", "");
                intent.putExtra("province", "");
                intent.putExtra("longitude", lo + "");
                intent.putExtra("latitude", la + "'");
                intent.putExtra("address", "");
                PeripheryActivity.this.setResult(RESULT_OK, intent);
                PeripheryActivity.this.finish();
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            city = location.getCity();
            now_city.setText(city);
            p = location.getProvince();
            la = location.getLatitude();
            lo = location.getLongitude();
            a = location.getAddrStr();
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    mlist.add(p.getName());
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(la, lo)));
            }


            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
