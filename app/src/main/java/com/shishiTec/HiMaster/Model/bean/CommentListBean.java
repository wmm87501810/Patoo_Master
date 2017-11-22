package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/21.
 */
public class CommentListBean {

    /**
     * id : 评论id
     * share_id : 分享id
     * type : 分享类型,1为达人分享，2为牛人分享
     * uid : 评论人或回复人uid
     * content : 评论内容
     * add_time : 评论时间或回复时间
     * status : 状态(0、待审核，1、审核通过，2、审核拒绝)
     * nikename : 评论人或回复人昵称
     * img_top : 评论人或回复人头像
     * reply_content : 回复内容
     * comment_time : 评论时间或回复时间
     */

    private String id;
    private String share_id;
    private String type;
    private String uid;
    private String content;
    private String add_time;
    private String status;
    private String nikename;
    private String img_top;
    private String reply_content;
    private String comment_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
}
