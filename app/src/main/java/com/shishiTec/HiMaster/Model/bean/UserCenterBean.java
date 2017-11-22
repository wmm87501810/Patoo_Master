package com.shishiTec.HiMaster.Model.bean;

import java.io.Serializable;

/**
 * Created by Pursue on 16/4/18.
 */
public class UserCenterBean implements Serializable{

    /**
     * uid : 用户id
     * identity : 用户标识 1:普通用户,2达人
     * nikename : 用户昵称
     * img_top : 头像
     * sex:  性别：1、男，2、女
     * m_point : 用户M点数
     * intro: 个性签名
     * fans : 粉丝数
     * follows : 关注数
     * master_order_num : 达人未查看订单数量
     * msg_num : 未读消息数
     * coupon_sum : 代金券总金额
     * card_num : 酱油卡数量
     */

    private String uid;
    private String sex;
    private String intro;
    private String identity;
    private String nikename;
    private String img_top;
    private String m_point;
    private String fans;
    private String follows;
    private String master_order_num;
    private String msg_num;
    private String coupon_sum;
    private String card_num;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getM_point() {
        return m_point;
    }

    public void setM_point(String m_point) {
        this.m_point = m_point;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    public String getMaster_order_num() {
        return master_order_num;
    }

    public void setMaster_order_num(String master_order_num) {
        this.master_order_num = master_order_num;
    }

    public String getMsg_num() {
        return msg_num;
    }

    public void setMsg_num(String msg_num) {
        this.msg_num = msg_num;
    }

    public String getCoupon_sum() {
        return coupon_sum;
    }

    public void setCoupon_sum(String coupon_sum) {
        this.coupon_sum = coupon_sum;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
