package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/11.
 */
public class ClassBean {

    /**
     * course_id : 394
     * title : Yoga Therapy年卡
     * cover : uploadfile/2015/0323/20150323101204964_thumb.jpg
     * view_count : 93
     * is_mpay : 0
     * m_price : 1
     * price : 6983.00
     * market_price : 6963
     * show_market_price :
     * store : 奉贤路
     * uid : 432
     * nikename : M先森
     * tags : ["uploadfile/superscript/icon_6.png","uploadfile/superscript/icon_7.png"]
     * distance : 1.96KM
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
    private List<?> subject_list;
    /**
     * course_id : 321
     * title : DIY甜点4选1把握健康的味道
     * cover : uploadfile/2015/0322/20150322125938633_thumb.jpg
     * view_count : 6
     * is_mpay : 0
     * m_price : 1
     * price : 49.00
     * market_price : 49
     * show_market_price :
     * store : 中山北路
     * uid : 432
     * nikename : M先森
     * tags : ["新","酱油卡"]
     * distance : 1.89KM
     */

    private List<CourseListBean> course_list;

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

    public List<?> getSubject_list() {
        return subject_list;
    }

    public void setSubject_list(List<?> subject_list) {
        this.subject_list = subject_list;
    }

    public List<CourseListBean> getCourse_list() {
        return course_list;
    }

    public void setCourse_list(List<CourseListBean> course_list) {
        this.course_list = course_list;
    }

    public static class CourseListBean {
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
}
