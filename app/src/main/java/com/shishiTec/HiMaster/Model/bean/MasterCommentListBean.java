package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/3.
 */
public class MasterCommentListBean {
    /**
     * uid : 9081
     * nikename : m_365416
     * img_top :
     * comment_id : 11
     * content : test1
     * reply : [{"uid":"9057","nikename":"1865***2510","img_top":"","reply_id":"8","content":"回复Andy Wang：我是楼主"},{"uid":"1","nikename":"Andy Wang","img_top":"uploadfile/2015/0820/1440055621434_thumb.jpg","reply_id":"5","content":"回复m_365416：回复一下下"},{"uid":"9057","nikename":"1865***2510","img_top":"","reply_id":"4","content":"回复m_365416：回复一下下"},{"uid":"9057","nikename":"1865***2510","img_top":"","reply_id":"3","content":"回复m_365416：hahah哈哈"}]
     */

    private String uid;
    private String nikename;
    private String img_top;
    private String comment_id;
    private String content;
    /**
     * uid : 9057
     * nikename : 1865***2510
     * img_top :
     * reply_id : 8
     * content : 回复Andy Wang：我是楼主
     */

    private List<ReplyBean> reply;

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

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }

    public static class ReplyBean {
        private String uid;
        private String nikename;
        private String img_top;
        private String reply_id;
        private String content;

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

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
