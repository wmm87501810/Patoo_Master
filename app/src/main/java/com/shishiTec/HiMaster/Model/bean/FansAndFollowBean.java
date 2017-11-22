package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by Pursue on 16/4/18.
 */
public class FansAndFollowBean {

    /**
     * uid : 用户ID
     * fid : 被关注者ID
     * identity : 用户标识 1:普通用户,2达人
     * nikename : 用户昵称
     * img_top : 用户头像
     * name : 分类名称
     * intro : 用户简介
     * fans : 粉丝数
     * jy_value : 经验值
     * is_follow : 是否关注
     * last_login_time : 最后登录时间
     */

    private String uid;
    private String fid;
    private String identity;
    private String nikename;
    private String img_top;
    private String name;
    private String intro;
    private String fans;
    private String jy_value;
    private String is_follow;
    private String last_login_time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getJy_value() {
        return jy_value;
    }

    public void setJy_value(String jy_value) {
        this.jy_value = jy_value;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }
}
