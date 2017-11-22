package com.shishiTec.HiMaster.Utils;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationUtil {

	private LocationClient mLocClient;

	public void getLocation(Context con, BDLocationListener mLocationListener) {
		// 定位初始化
		mLocClient = new LocationClient(con);
		mLocClient.registerLocationListener(mLocationListener);
		LocationUtil.setLocationOption(mLocClient);
		mLocClient.start();
	}
	
	public void stop() {
		if(mLocClient!=null)
			mLocClient.stop();
	}
	
	// 设置相关参数
	public static void setLocationOption(LocationClient mLocationClient) {
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		option.setOpenGps(true); // 打开gps
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setAddrType("all");
		option.setPriority(LocationClientOption.NetWorkFirst);
//		option.setPriority(LocationClientOption.GpsFirst); // gps
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}


}
