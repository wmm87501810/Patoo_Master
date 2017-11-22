package com.shishiTec.HiMaster.Model.realbean;

import android.support.annotation.NonNull;

/**
 * Created by 83802 on 2017/8/25.
 */

public class ChatInfoBean{

    private String uid;
    private String m_uid;
    private String content;
    private String type;
    private String status;
    private String add_time;
    private String nickname;
    private String img_top;
    private String m_img_top;
    private String m_nickname;
    private String is_sender;//0为接收者，1为发送者

    //private Boolean is_send=false;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getM_uid() {
        return m_uid;
    }

    public void setM_uid(String m_uid) {
        this.m_uid = m_uid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg_top() {
        return img_top;
    }

    public void setImg_top(String img_top) {
        this.img_top = img_top;
    }

    public String getM_img_top() {
        return m_img_top;
    }

    public void setM_img_top(String m_img_top) {
        this.m_img_top = m_img_top;
    }

    public String getM_nickname() {
        return m_nickname;
    }

    public void setM_nickname(String m_nickname) {
        this.m_nickname = m_nickname;
    }

    public String getIs_sender() {
        return is_sender;
    }

    public void setIs_sender(String is_sender) {
        this.is_sender = is_sender;
    }


}
