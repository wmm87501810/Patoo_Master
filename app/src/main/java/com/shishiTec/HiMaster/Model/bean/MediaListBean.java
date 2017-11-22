package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/6.
 */
public class MediaListBean {
    private String url;
    private String intro;

    public MediaListBean(String url,String intro){
        this.url = url;
        this.intro = intro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
