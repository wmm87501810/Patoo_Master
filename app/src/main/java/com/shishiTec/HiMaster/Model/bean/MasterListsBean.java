package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu on 2016/4/25.
 */
public class MasterListsBean {

    /**
     * ads_data_id : 97
     * title : ad2
     * pic_url : uploadfile/2016/0421/20160421103932724.jpg
     * type : 1
     * content : 340
     * is_login : 0
     * is_open : 0
     */

    private List<BannerListBean> banner_list;
    /**
     * uid : 2971
     * nikename : convse
     * img_top : uploadfile/2015/0601/20150601114211262_thumb.png
     * intro : asdasdads
     * fans : 21
     * is_follow : 0
     */

    private List<MasterListBean> master_list;
    /**
     * share_id : 4
     * title : 撒发生发生法
     * content : 内容介绍: 撒发生发生法 主题精选 Channel Me 精选全部评论 条评论 我来说点啥 验证码: 同步到: 猜你喜欢 上升最快 一大波模特 视频 酷6制造 ...
     * cover : uploadfile/2016/0418/20160418042703642_thumb.jpg
     * tag_id : 5
     * tag_name : test
     * like_count : 12
     * browse_count : 330
     * uid : 1
     * nikename : Andy Wang
     * img_top : uploadfile/2015/0820/1440055621434_thumb.jpg
     * is_like : 0
     */

    private List<ShareListBean> share_list;

    public List<BannerListBean> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(List<BannerListBean> banner_list) {
        this.banner_list = banner_list;
    }

    public List<MasterListBean> getMaster_list() {
        return master_list;
    }

    public void setMaster_list(List<MasterListBean> master_list) {
        this.master_list = master_list;
    }

    public List<ShareListBean> getShare_list() {
        return share_list;
    }

    public void setShare_list(List<ShareListBean> share_list) {
        this.share_list = share_list;
    }

    public static class BannerListBean {
        private String ads_data_id;
        private String title;
        private String pic_url;
        private String type;
        private String content;
        private String is_login;
        private String is_open;
        private String pfurl;

        public void setPfurl(String pfurl) {
            this.pfurl = pfurl;
        }

        public String getPfurl() {
            return pfurl;
        }

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

    public static class MasterListBean {
        private String uid;
        private String nikename;
        private String img_top;
        private String intro;
        private String fans;
        private String is_follow;

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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }
    }

    public static class ShareListBean {
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
        private String is_like;

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

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }
    }
}
