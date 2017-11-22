package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by Pursue on 16/4/18.
 */
public class BigManBean {


    /**
     * share_id : 3
     * title : 粉丝_百度百科
     * content : 粉丝是中国常见的食品之一，是一种用绿豆、红薯淀粉等做成的丝状食品，故名粉丝。往往又叫做粉条丝、冬粉（主要在台湾），日本称春雨，朝鲜半岛称唐面，越南称面...
     * like_count : 0
     * browse_count : 0
     * province : 上海市
     * city : 上海市
     * uid : 320
     * nikename : 偷懒鱼婆
     * img_top : uploadfile/2015/0528/20150528034816628_thumb.png
     * detail : [{"media_type":"1","url":"uploadfile/2016/0420/20160420061302513_tmp.jpg","intro":""},{"media_type":"1","url":"uploadfile/2016/0420/20160420061306342_tmp.jpg","intro":""},{"media_type":"1","url":"uploadfile/2016/0420/20160420061313386_tmp.png","intro":""},{"media_type":"1","url":"uploadfile/2016/0420/20160420061319533_tmp.jpg","intro":""}]
     */

    private String share_id;
    private String title;
    private String content;
    private String like_count;
    private String browse_count;
    private String province;
    private String city;
    private String uid;
    private String nikename;
    private String img_top;
    private String is_like;

    /**
     * media_type : 1
     * img_url : uploadfile/2016/0420/20160420061302513_tmp.jpg
     * intro :
     */

    private List<DetailBean> detail;

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

    public String getis_like() {
        return is_like;
    }

    public void setis_like(String is_like) {
        this.is_like = is_like;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public class DetailBean {
        private String media_type;
        private String img_url;
        private String intro;

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public String getUrl() {
            return img_url;
        }

        public void setUrl(String img_url) {
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
