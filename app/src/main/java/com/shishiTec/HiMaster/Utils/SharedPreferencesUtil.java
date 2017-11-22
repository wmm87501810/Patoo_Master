package com.shishiTec.HiMaster.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shishiTec.HiMaster.base.BaseApplication;

import java.util.ArrayList;

/**
 * Created by Pursue on 16/1/18.
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil mInstance;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    //存储的sharedpreferences文件名
    private static final String SHAREDPREFERENCE_NAME = "save_master_file";
    //是否第一次打开页面
    private static final String IS_FIRST_USE = "is_first_open";//是否第一次打开界面
    private static final String LONGTUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String IMGTOP = "imgtop";
    private static final String LOGINSTYLE = "loginStyle";
    private static final String SCREEN = "screen";            //设备分辨率
    private static final String USERNAME = "userName";//用户名
    private static final String PASSWORD = "passwrod";//密码
    private static final String IDENTITY = "1";//用户标示。1、素人。2、达人
    private static final String UID = "uid";//用户ID
    private static final String TOKEN = "token";
    private static final String ORDERTYPE = "OrderType";
    private static final String SELECTTYPE = "SelectType";
    private static final String CITYLIST = "cityList";
    private static final String MOBILE = "mobile";
    private static final String CODE = "security_code";
    public synchronized static SharedPreferencesUtil getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPreferencesUtil(BaseApplication.getInstance());
        }
        return mInstance;
    }

    public SharedPreferencesUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public synchronized boolean putString(String key, String value) {
        mEditor.putString(key, value);
        return mEditor.commit();
    }
    public synchronized boolean putStringArrayList(String key,ArrayList<String> value) {
        String keySizeStr = key+"Size";
        int keySize = mSharedPreferences.getInt(keySizeStr,0);
        if(keySize>0){
            for (int i=0;i<keySize;i++){
                mEditor.remove(key+"_"+i);
            }
        }
        keySize = value.size();
        for (int i = 0; i <value.size(); i++) {
            mEditor.putString(key+"_"+i,value.get(i));
        }
        mEditor.putInt(keySizeStr, value.size());
        return mEditor.commit();
    }

    public synchronized boolean putInt(String key, int value) {
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public synchronized boolean putLong(String key, long value) {
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    public synchronized boolean putFloat(String key, float value) {
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }
    public synchronized boolean putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    public String getString(String key, String value) {
        return mSharedPreferences.getString(key, value);
    }

    public ArrayList<String> getStringArrayList(String key) {
        ArrayList<String> al = new ArrayList<>();
        String keySizeStr = key+"Size";
        int keySize = mSharedPreferences.getInt(keySizeStr,0);
        if(keySize>0){
            for (int i=0;i<keySize;i++){
                al.add(mSharedPreferences.getString(key + "_" + i, ""));
            }
        }
        return al;
    }

    public int getInt(String key, int value) {
        return mSharedPreferences.getInt(key, value);
    }

    public long getLong(String key, long value) {
        return mSharedPreferences.getLong(key, value);
    }

    public float getFloat(String key, float value) {
        return mSharedPreferences.getFloat(key, value);
    }

    public boolean getBoolean(String key, boolean value) {
        return mSharedPreferences.getBoolean(key, value);
    }

    public boolean remove(String key) {
        mEditor.remove(key);
        return mEditor.commit();
    }
    public boolean isFirstUse(){
        return mSharedPreferences.getBoolean(IS_FIRST_USE, false);
    }

    public void  writeFirstUse(){
        mEditor.putBoolean(IS_FIRST_USE, true);
        mEditor.commit();
    }


    public boolean isLogin(){
        if (getUid()!=null&&getUid().length()>0){
            return true;
        }
        return false;
    }

    public String getLoginStyle(){
        return mSharedPreferences.getString(LOGINSTYLE,"");
    }

    public void saveLatitude(String latitude){
        if (latitude.equals("4.9E-324")){
            latitude = "";
        }
        mEditor.putString(LATITUDE,latitude);
        mEditor.commit();
    }

    public String getLatitude(){
        return mSharedPreferences.getString(LATITUDE,"");
    }

    public void saveLongitude(String longitude){
        if (longitude.equals("4.9E-324")){
            longitude = "";
        }
        mEditor.putString(LONGTUDE,longitude);
        mEditor.commit();
    }

    public void saveNikeName(String nikename){
        mEditor.putString("nikeName",nikename);
        mEditor.commit();
    }

    public String getNikeName(){
        return mSharedPreferences.getString("nikeName","");
    }

    public void saveSex(String sex){
        mEditor.putString("sex",sex);
        mEditor.commit();
    }
    public String getSex(){
        return mSharedPreferences.getString("sex","");
    }

    public void saveImgTop(String img_top){
        mEditor.putString(IMGTOP,img_top);
        mEditor.commit();
    }

    public String getImgTop(){
        return mSharedPreferences.getString(IMGTOP,"");
    }

    public String getLongtude(){
        return mSharedPreferences.getString(LONGTUDE,"");
    }

    public void saveScreen(String screen){
        mEditor.putString(SCREEN,screen);
        mEditor.commit();
    }

    public String getScreen(){
        return mSharedPreferences.getString(SCREEN,"");
    }

    public void saveOrderType(String OrderType){
        mEditor.putString(ORDERTYPE,OrderType);
        mEditor.commit();
    }

    public String getOrderType(){
        return mSharedPreferences.getString(ORDERTYPE,"");
    }

    public void saveSelectType(String SelectType){
        mEditor.putString(SELECTTYPE,SelectType);
        mEditor.commit();
    }

    public String getSelectType(){
        return mSharedPreferences.getString(SELECTTYPE,"");
    }

    public void saveCityList(String CityList){
        mEditor.putString(CITYLIST,CityList);
        mEditor.commit();
    }


    public String getCityList(){
        return mSharedPreferences.getString(CITYLIST,"");
    }

    public void saveMobNum(String CityList){
        mEditor.putString(MOBILE,CityList);
        mEditor.commit();
    }
    public void saveSecurity_code(String CityList){
        mEditor.putString(CODE,CityList);
        mEditor.commit();
    }
    public String getMobilenum(){
        return mSharedPreferences.getString(MOBILE,"");
    }
    public String getSecurity_code(){
        return mSharedPreferences.getString(CODE,"");
    }

    public void saveLoginInfo(String loginStyle,String userName,String password,String uid,String token,String identity){
        assert (mEditor != null);
        mEditor.putString(USERNAME,userName);
        mEditor.putString(PASSWORD,password);
        mEditor.putString(UID,uid);
        mEditor.putString(TOKEN,token);
        mEditor.putString(LOGINSTYLE,loginStyle);
        mEditor.putString(IDENTITY,identity);
        mEditor.commit();
    }


    public String getUsername(){
        return mSharedPreferences.getString(USERNAME,"");
    }

    public String getPassword(){
        return mSharedPreferences.getString(PASSWORD,"");
    }

    public String getUid(){
        return mSharedPreferences.getString(UID,"");
    }

    public String getToken(){
        return mSharedPreferences.getString(TOKEN,"");
    }

    public void clearUserInfo(){
        assert (mEditor != null);
        mEditor.putString(USERNAME,null);
        mEditor.putString(PASSWORD,null);
        mEditor.putString(UID,null);
        mEditor.putString(TOKEN,null);
        mEditor.commit();
    }

}
