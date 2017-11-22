package com.shishiTec.HiMaster.Model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hyu on 2016/5/10.
 */
public class GetMasterCourseBean implements Parcelable{

    /**
     * course_id : 695
     * title : 安卓测试多日历组合课程
     * cover : uploadfile/2015/1103/20151103110945677_thumb.jpg
     * view_count : 669
     * is_mpay : 0
     * m_price : 1
     * price : 0.02
     * market_price : 0.02
     * show_market_price :
     * store : 吴中路
     * uid : 1
     * nikename : Andy Wang
     * tags : ["uploadfile/superscript/icon_6.png"]
     * distance : 4.18KM
     */

    private String course_id;
    private String cover;
    private String view_count;
    private String title;
    private String is_mpay;
    private String m_price;
    private String price;
    private String store;
    private String market_price;
    private String show_market_price;
    private String uid;
    private String nikename;
    private String distance;
    private List<String> tags;

    protected GetMasterCourseBean(Parcel in) {
        course_id = in.readString();
        title = in.readString();
        cover = in.readString();
        view_count = in.readString();
        is_mpay = in.readString();
        m_price = in.readString();
        price = in.readString();
        market_price = in.readString();
        show_market_price = in.readString();
        store = in.readString();
        uid = in.readString();
        nikename = in.readString();
        distance = in.readString();
        tags = in.createStringArrayList();
    }

    public static final Creator<GetMasterCourseBean> CREATOR = new Creator<GetMasterCourseBean>() {
        @Override
        public GetMasterCourseBean createFromParcel(Parcel in) {
            return new GetMasterCourseBean(in);
        }

        @Override
        public GetMasterCourseBean[] newArray(int size) {
            return new GetMasterCourseBean[size];
        }
    };

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getIs_mpay() {
        return is_mpay;
    }

    public void setIs_mpay(String is_mpay) {
        this.is_mpay = is_mpay;
    }

    public String getM_price() {
        return m_price;
    }

    public void setM_price(String m_price) {
        this.m_price = m_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShow_market_price() {
        return show_market_price;
    }

    public void setShow_market_price(String show_market_price) {
        this.show_market_price = show_market_price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(course_id);
        dest.writeString(cover);
        dest.writeString(view_count);
        dest.writeString(is_mpay);
        dest.writeString(m_price);
        dest.writeString(price);
        dest.writeString(market_price);
        dest.writeString(show_market_price);
        dest.writeString(store);
        dest.writeString(uid);
        dest.writeString(nikename);
        dest.writeString(distance);
        dest.writeStringList(tags);
    }
}
