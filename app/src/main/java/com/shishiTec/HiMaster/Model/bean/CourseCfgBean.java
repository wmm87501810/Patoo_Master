package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/18.
 */
public class CourseCfgBean {
    private String course_cfg_id;
    private String custom_spec_name;
    private String price;
    private String market_price;
    private String show_market_price;

    public String getCourse_cfg_id() {
        return course_cfg_id;
    }

    public void setCourse_cfg_id(String course_cfg_id) {
        this.course_cfg_id = course_cfg_id;
    }

    public String getCustom_spec_name() {
        return custom_spec_name;
    }

    public void setCustom_spec_name(String custom_spec_name) {
        this.custom_spec_name = custom_spec_name;
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
}
