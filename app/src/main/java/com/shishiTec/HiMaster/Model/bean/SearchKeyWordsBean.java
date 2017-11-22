package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/17.
 */
public class SearchKeyWordsBean {

    /**
     * course_id : 390
     * title : 网球初级私教月卡
     * cover : uploadfile/2015/0323/20150323070709927_thumb.jpg
     * view_count : 67
     * is_mpay : 0
     * m_price : 1
     * price : 2000.00
     * market_price : 2000
     * show_market_price :
     * store : 奥体中心
     * uid : 432
     * nikename : M先森
     * tags : ["uploadfile/superscript/icon_7.png","uploadfile/superscript/icon_6.png"]
     * distance : 730.86KM
     */

    private String course_id;
    private String title;
    private String cover;
    private String view_count;
    private String is_mpay;
    private String m_price;
    private String price;
    private String market_price;
    private String show_market_price;
    private String store;
    private String uid;
    private String nikename;
    private String distance;
    private List<String> tags;

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
}
