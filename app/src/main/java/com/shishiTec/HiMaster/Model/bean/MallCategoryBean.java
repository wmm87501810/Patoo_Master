package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu on 2016/4/21.
 */
public class MallCategoryBean {


    /**
     * id : 分类id
     * name : 分类名称
     * pic_url : 分类图片地址
     */

    private List<CategoryListBean> category_list;
    /**
     * ads_data_id :  	广告id
     * title : 广告标题
     * pic_url : 广告图片
     * type : 广告类型(1:课程;2:达人;3:课程卡片;4:达人卡片;5:广告通卡;6:html5页面;7内部网页)
     * content : 广告内容
     * is_login : 是否需要登录(1需要,0不需要)
     * is_open : 是否原生浏览器打开(1需要,0不需要)
     */

    private List<BannerListBean> banner_list;

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
    }

    public List<BannerListBean> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(List<BannerListBean> banner_list) {
        this.banner_list = banner_list;
    }

    public static class CategoryListBean {
        private String id;
        private String name;
        private String pic_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }

    public static class BannerListBean {
        private String ads_data_id;
        private String title;
        private String pic_url;
        private String type;
        private String content;
        private String is_login;
        private String is_open;

        public String getAds_data_id() {
            return ads_data_id;
        }

        public void setAds_data_id(String ads_data_id) {
            this.ads_data_id = ads_data_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_login() {
            return is_login;
        }

        public void setIs_login(String is_login) {
            this.is_login = is_login;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }
    }
}
