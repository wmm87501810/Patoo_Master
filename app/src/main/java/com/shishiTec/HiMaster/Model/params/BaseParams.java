package com.shishiTec.HiMaster.Model.params;

import android.os.Build;

import com.shishiTec.HiMaster.BuildConfig;
import com.shishiTec.HiMaster.Utils.AppContextUtil;
import com.shishiTec.HiMaster.Utils.NetUtils;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pursue on 16/4/15.
 */
public class BaseParams {
    private DeviceParams device;//设备信息
    private Map<String,String> data = new HashMap<>();
    private String sign;        //签名
    private String rest_version;//接口版本号
    String token = SharedPreferencesUtil.getInstance().getToken();

    public BaseParams(DeviceParams device) {
        String net_type ="";
        switch (NetUtils.getNetworkState(BaseApplication.getInstance())){
            case 1:
                net_type = "wifi";
                break;
            case 2:
                net_type = "2g";
                break;
            case 3:
                net_type = "3g";
                break;
            case 4:
                net_type = "4g";
                break;
        }
        device.setClient("Android");
        device.setD_brand(Build.BRAND);
        device.setD_model(Build.MODEL);
        device.setOs_version(Build.VERSION.RELEASE);
        device.setApp_version(BuildConfig.VERSION_NAME);
        device.setScreen(SharedPreferencesUtil.getInstance().getScreen());
        device.setNetwork_type(net_type);
        device.setUuid(AppContextUtil.getdeviceID());
        device.setLat(SharedPreferencesUtil.getInstance().getLatitude());
        device.setLng(SharedPreferencesUtil.getInstance().getLongtude());
        if (token!=null&&!token.equals("")){
            device.setToken(token);
        }
    }

    public Map<String,String> getData() {
        return data;
    }

    public void setData(Map<String,String> data) {
        this.data = data;
    }

    public DeviceParams getDevice() {
        return device;
    }

    public void setDevice(DeviceParams device) {
        this.device = device;
    }

    public String getRest_version() {
        return rest_version;
    }

    public void setRest_version(String rest_version) {
        this.rest_version = rest_version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    //拼接sign
    public String paramsSign(){
        if (data.size()>0){
            data.put("timetamp",String.valueOf(System.currentTimeMillis()));
            List<Map.Entry<String,String>> mappingList = null;
            mappingList = new ArrayList<>(data.entrySet());
            Collections.sort(mappingList, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
                    return mapping1.getValue().compareTo(mapping2.getValue());
                }
            });
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,String> mapping:mappingList){
//            System.out.println(mapping.getKey()+":"+mapping.getValue());
                sb.append(mapping.getValue());
            }
//        System.out.println("sbString"+":"+sb.toString());
            return sb.toString()+"master";
        }
      return data.put("timetamp",String.valueOf(System.currentTimeMillis()));
    }



}
