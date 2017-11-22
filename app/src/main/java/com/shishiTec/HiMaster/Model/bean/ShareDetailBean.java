package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by Pursue on 16/4/18.
 */
public class ShareDetailBean {


    /**
     * share_id : 1
     * title : 牛人分享标题
     * content : 牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题牛人分享标题
     * comment_count : 3
     * is_like : 0
     * detail : [{"media_type":"1","url":"uploadfile/2016/0421/20160421065011156_tmp.jpg","intro":""}]
     */

    private ShareBean share;
    /**
     * uid : 321
     * nikename : 文冬
     * img_top : uploadfile/2014/1015/5张文冬头.jpg
     * is_follow : 0
     */

    private UserBean user;
    /**
     * course_id : 313
     * title : 1对1钢琴速成课
     * cover : uploadfile/2015/0322/20150322115737985_thumb.jpg
     * store : 大望路
     * distance : 1,462.61KM
     */

    private CourseBean course;
    /**
     * comment_id : 13
     * content : 牛人分享评论测试
     * uid : 1
     * nikename : Andy Wang
     * img_top : uploadfile/2015/0820/1440055621434_thumb.jpg
     */

    private List<CommentListBean> comment_list;

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }

    public static class ShareBean {
        private String share_id;
        private String title;
        private String content;
        private String comment_count;
        private String is_like;
        /**
         * media_type : 1
         * url : uploadfile/2016/0421/20160421065011156_tmp.jpg
         * intro :
         */

        private List<DetailBean> detail;

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            private String media_type;
            private String url;
            private String intro;

            public String getMedia_type() {
                return media_type;
            }

            public void setMedia_type(String media_type) {
                this.media_type = media_type;
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
    }

    public static class UserBean {
        private String uid;
        private String nikename;
        private String img_top;
        private String is_follow;

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

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }
    }

    public static class CourseBean {
        private String course_id;
        private String title;
        private String cover;
        private String store;
        private String distance;

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }

    public static class CommentListBean {
        private String comment_id;
        private String content;
        private String uid;
        private String nikename;
        private String img_top;

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
    }
}
