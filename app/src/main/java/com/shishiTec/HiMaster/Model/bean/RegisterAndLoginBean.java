package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by Pursue on 16/4/17.
 */
public class RegisterAndLoginBean {

    /**
     * uid : 用户id
     * nikename : 用户昵称
     * mobile : 手机号码
     * sex : 性别（1男2女0未知）
     * identity : 用户标识 1:普通用户,2达人
     * img_top :头像
     * intro :用户简介
     * company :企业名称
     * weixin_content : 微信分享文字
     * token : 登录token
     */

    private String uid;
    private String nikename;
    private String mobile;
    private String sex;
    private String identity;
    private String img_top;
    private String intro;
//    private String company;
//    private String weixin_content;
    private String token;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

//    public String getCompany() {
//        return company;
//    }
//
//    public void setCompany(String company) {
//        this.company = company;
//    }
//
//    public String getWeixin_content() {
//        return weixin_content;
//    }
//
//    public void setWeixin_content(String weixin_content) {
//        this.weixin_content = weixin_content;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
