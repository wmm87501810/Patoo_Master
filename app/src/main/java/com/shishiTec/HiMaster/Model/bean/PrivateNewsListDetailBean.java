package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/23.
 */
public class PrivateNewsListDetailBean {

    /**
     * id : 私信id
     * sendUid : 发送方uid
     * sendNikename : 发送方昵称
     * sendImgTop : 发送方头像
     * acceptUid : 接收方uid
     * acceptNickName : 接收方昵称
     * acceptImgTop : 接收方头像
     * addtime : 发送时间
     * content : 内容
     * type : 类型，1为文字，2为图片
     */

    private String id;
    private String sendUid;
    private String sendNikename;
    private String sendImgTop;
    private String acceptUid;
    private String acceptNickName;
    private String acceptImgTop;
    private String addtime;
    private String content;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUid() {
        return sendUid;
    }

    public void setSendUid(String sendUid) {
        this.sendUid = sendUid;
    }

    public String getSendNikename() {
        return sendNikename;
    }

    public void setSendNikename(String sendNikename) {
        this.sendNikename = sendNikename;
    }

    public String getSendImgTop() {
        return sendImgTop;
    }

    public void setSendImgTop(String sendImgTop) {
        this.sendImgTop = sendImgTop;
    }

    public String getAcceptUid() {
        return acceptUid;
    }

    public void setAcceptUid(String acceptUid) {
        this.acceptUid = acceptUid;
    }

    public String getAcceptNickName() {
        return acceptNickName;
    }

    public void setAcceptNickName(String acceptNickName) {
        this.acceptNickName = acceptNickName;
    }

    public String getAcceptImgTop() {
        return acceptImgTop;
    }

    public void setAcceptImgTop(String acceptImgTop) {
        this.acceptImgTop = acceptImgTop;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
