package com.shishiTec.HiMaster.Model.params;

/**
 * Created by Pursue on 16/4/15.
 */
public class DeviceParams {
    private String client; //设备系统（android）
    private String app_version; //app版本号
    private String d_brand;     //设备厂家
    private String d_model;     //手机型号
    private String os_version;  //设备当前系统版本
    private String screen;      //设备分辨率
    private String network_type;//当前网络模式
    private String uuid;        //用户ID
    private String token;
    private String lng;         //当前经度
    private String lat;         //当前纬度
    private String city_code; //当前城市
    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getD_brand() {
        return d_brand;
    }

    public void setD_brand(String d_brand) {
        this.d_brand = d_brand;
    }

    public String getD_model() {
        return d_model;
    }

    public void setD_model(String d_model) {
        this.d_model = d_model;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getNetwork_type() {
        return network_type;
    }

    public void setNetwork_type(String network_type) {
        this.network_type = network_type;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_code() {
        return city_code;
    }
}
