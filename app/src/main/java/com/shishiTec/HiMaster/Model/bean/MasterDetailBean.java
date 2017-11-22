package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu on 2016/4/27.
 */
public class MasterDetailBean {

    /**
     * share_id : 4
     * title : 撒发生发生法恩彤
     * content : 内容介绍: 撒发生发生法 主题精选 Channel Me 精选全部评论 条评论 我来说点啥 验证码: 同步到: 猜你喜欢 上升最快 一大波模特 视频 酷6制造 Channel...
     * cover : uploadfile/2016/0523/20160523031418748_thumb.jpg
     * tag_id : 5
     * tag_name : test
     * browse_count : 1190
     * comment_count : 23
     * is_like : 0
     * detail : [{"media_type":"1","url":"uploadfile/2016/0523/20160523031444604_tmp.jpg","intro":""},{"media_type":"1","url":"uploadfile/2016/0523/20160523031452286_tmp.jpg","intro":""}]
     * next_share_id : 3
     * share_data : {"title":"撒发生发生法恩彤","desc":"内容介绍: 撒发生发生法 主题精选 Channel Me 精选全部评论 条评论 ...","link":"http://kaifa.gomaster.cn/mobile/index.php?c=share&a=master_detail&share_id=4","imgUrl":"uploadfile/2016/0523/20160523031418748_small.jpg"}
     */

    private ShareBean share;
    /**
     * course_id : 309
     * title : 空中瑜伽
     * cover : uploadfile/2015/0322/20150322093229154_thumb.jpg
     * view_count : 1412
     * is_mpay : 0
     * m_price : 1
     * price : 1000.00
     * market_price : 80
     * show_market_price :
     * store : 都市路
     * uid : 432
     * nikename : M先森
     * user_img_top : uploadfile/2014/1217/20141217101250786.jpg
     * tags : ["明星课","酱油卡","全免单","新","热门"]
     * distance :
     */

    private CourseBean course;
    /**
     * uid : 1
     * nikename : Andy Wang
     * img_top : uploadfile/2016/0511/20160511054900810_thumb.jpg
     * is_follow : 0
     * intro : 哟哟，莱次够！！
     */

    private UserBean user;
    /**
     * comment_id : 25
     * content : Shutdown kills. Koala lad. Lolls [尴尬][睡][微笑][微笑][撇嘴][色][得意][呲牙]
     * uid : 497
     * nikename : 青漓
     * img_top : uploadfile/2016/0523/20160523061112355_thumb.jpg
     */

    private List<CommentListBean> comment_list;

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
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
        private String cover;
        private String tag_id;
        private String tag_name;
        private String browse_count;
        private String comment_count;
        private String is_like;
        private String next_share_id;
        /**
         * title : 撒发生发生法恩彤
         * desc : 内容介绍: 撒发生发生法 主题精选 Channel Me 精选全部评论 条评论 ...
         * link : http://kaifa.gomaster.cn/mobile/index.php?c=share&a=master_detail&share_id=4
         * imgUrl : uploadfile/2016/0523/20160523031418748_small.jpg
         */

        private ShareDataBean share_data;
        /**
         * media_type : 1
         * url : uploadfile/2016/0523/20160523031444604_tmp.jpg
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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getBrowse_count() {
            return browse_count;
        }

        public void setBrowse_count(String browse_count) {
            this.browse_count = browse_count;
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

        public String getNext_share_id() {
            return next_share_id;
        }

        public void setNext_share_id(String next_share_id) {
            this.next_share_id = next_share_id;
        }

        public ShareDataBean getShare_data() {
            return share_data;
        }

        public void setShare_data(ShareDataBean share_data) {
            this.share_data = share_data;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class ShareDataBean {
            private String title;
            private String desc;
            private String link;
            private String imgUrl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
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

    public static class CourseBean {
        private String course_id;
        private String title;
        private String cover;
        private String view_count;
        private String is_mpay;
        private String m_price;
        private String price;
        private String market_price;
        private String show_market_price;
        private String store;
        private String uid;
        private String nikename;
        private String user_img_top;
        private String distance;
        private List<String> tags;

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

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getIs_mpay() {
            return is_mpay;
        }

        public void setIs_mpay(String is_mpay) {
            this.is_mpay = is_mpay;
        }

        public String getM_price() {
            return m_price;
        }

        public void setM_price(String m_price) {
            this.m_price = m_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getShow_market_price() {
            return show_market_price;
        }

        public void setShow_market_price(String show_market_price) {
            this.show_market_price = show_market_price;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
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

        public String getUser_img_top() {
            return user_img_top;
        }

        public void setUser_img_top(String user_img_top) {
            this.user_img_top = user_img_top;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    public static class UserBean {
        private String uid;
        private String nikename;
        private String img_top;
        private String is_follow;
        private String intro;

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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
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
