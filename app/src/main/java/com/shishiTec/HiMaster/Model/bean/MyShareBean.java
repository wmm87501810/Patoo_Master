package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by Pursue on 16/4/27.
 */
public class MyShareBean {
    /**
     * master : 分享类型 "master"为达人，“user"为牛人
     * share_id : 分享id
     * title : 分享标题
     * content :分享内容
     * cover : 封面图
     * tag_id : 标签id
     * tag_name : 标签名
     * like_count : 点赞次数
     * browse_count : 浏览次数
     * uid : 分享人id
     * nikename : 分享人昵称
     * img_top : 分享人头像
     * province :省份
     * city : 城市
     * add_time : 分享时间
     */

    private String master;
    private String share_id;
    private String title;
    private String content;
    private String cover;
    private String tag_id;
    private String tag_name;
    private String like_count;
    private String browse_count;
    private String uid;
    private String nikename;
    private String img_top;
    private String province;
    private String city;
    private String add_time;
    private List<Detail> detail;
    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getBrowse_count() {
        return browse_count;
    }

    public void setBrowse_count(String browse_count) {
        this.browse_count = browse_count;
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

    public String getImg_top() {
        return img_top;
    }

    public void setImg_top(String img_top) {
        this.img_top = img_top;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public class Detail {

        /**
         * media_type : 分享类型
         * img_url : 图片地址
         * intro : 介绍
         */

        private String media_type;
        private String img_url;
        private String intro;

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }
}
