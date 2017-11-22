package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/21.
 */
public class NotificationBean {

    /**
     * id : 通知编号
     * add_time : 通知时间
     * cover : 封面
     * title : 标题
     * pic_url :图片地址
     * content : 通知内容，简介
     * target_type : 跳转类型（1：课程；2：达人；3：课程卡片；4：达人卡片；5：html5页面；6：课程关键字）
     * target_content : 跳转内容
     * detail : 图文详情
     * is_read : 是否已读，0，未读，1，已读
     */

    private String id;
    private String add_time;
    private String cover;
    private String title;
    private String pic_url;
    private String content;
    private String target_type;
    private String target_content;
    private String detail;
    private String is_read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getTarget_content() {
        return target_content;
    }

    public void setTarget_content(String target_content) {
        this.target_content = target_content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}

