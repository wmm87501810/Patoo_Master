package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/23.
 */
public class PrivateNewsListBean {

    /**
     * id : id
     * nikename : 发送方昵称
     * img_top : 发送方头像
     * message : 信息内容
     * uid : 发送方uid
     * add_time : 发送时间
     * num : 未读消息数
     */

    private String id;
    private String nikename;
    private String img_top;
    private String message;
    private String uid;
    private String add_time;
    private String num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
