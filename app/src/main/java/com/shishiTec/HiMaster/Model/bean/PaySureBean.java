package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/19.
 */
public class PaySureBean {
    /**
     * course_id : 681
     * master_uid : 2971
     * is_group : 1
     * title : 阿拉斯加啊
     * cover : uploadfile/2015/0901/20150901040435692_thumb.jpg
     * custom_spec_name : 高级,团队,年卡,大班课
     * address : address1463390455
     * is_mpay : 0
     * m_price : 1
     * price : 1000.00
     * market_price : 20000.00
     * can_buy_num : 99
     * my_mpoints : 0
     * money : 0
     * mobile : 13917410050
     * condition : {"card":[{"id":"522","sharePic":"uploadfile/2015/0828/20150828051143937_thumb.png","shareMsg":"此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！！！","cardId":"15","cardTitle":"酱油3次卡1","cardDesc":"测试测试","cardUrl":"uploadfile/2015/0828/20150828051123409_thumb.png","cardPrice":"0.03","beginTime":"1461202071","endTime":"1463794071","validNum":"3","courseLimit":"1","masterLimit":"3","totalNum":"3","remainNum":"2","isGiveaway":"0","condition_id":"522","condition_type":"1"},{"id":"521","sharePic":"uploadfile/2015/0824/20150824065959950_thumb.png","shareMsg":"此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！","cardId":"14","cardTitle":"课程任选无限次","cardDesc":"啊","cardUrl":"uploadfile/2015/1021/20151021050600476_thumb.png","cardPrice":"0.01","beginTime":"1461202031","endTime":"1463794031","validNum":"0","courseLimit":"1","masterLimit":"3","totalNum":"0","remainNum":"0","isGiveaway":"0","condition_id":"521","condition_type":"1"}],"coupon":[{"user_coupon_id":"157961","coupon_id":"61","coupon_name":"50券","intro":"50券","type":"1","price":"50.00","limit_price":"300.00","start_used_time":"1452567900","end_used_time":"1486782300","is_all_course":"1","is_all_card":"0","condition_id":"157961","condition_type":"2"}],"new_user":[{"condition_id":"0","condition_type":"4","title":"新用户购课减2.00元","price":"2.00","valid_time":"2016-05-20"}]}
     */

    private String course_id;
    private String master_uid;
    private String is_group;
    private String title;
    private String cover;
    private String custom_spec_name;
    private String address;
    private String is_mpay;
    private String m_price;
    private String price;
    private String market_price;
    private String can_buy_num;
    private String my_mpoints;
    private String money;
    private String mobile;
    private ConditionBean condition;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getMaster_uid() {
        return master_uid;
    }

    public void setMaster_uid(String master_uid) {
        this.master_uid = master_uid;
    }

    public String getIs_group() {
        return is_group;
    }

    public void setIs_group(String is_group) {
        this.is_group = is_group;
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

    public String getCustom_spec_name() {
        return custom_spec_name;
    }

    public void setCustom_spec_name(String custom_spec_name) {
        this.custom_spec_name = custom_spec_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCan_buy_num() {
        return can_buy_num;
    }

    public void setCan_buy_num(String can_buy_num) {
        this.can_buy_num = can_buy_num;
    }

    public String getMy_mpoints() {
        return my_mpoints;
    }

    public void setMy_mpoints(String my_mpoints) {
        this.my_mpoints = my_mpoints;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ConditionBean getCondition() {
        return condition;
    }

    public void setCondition(ConditionBean condition) {
        this.condition = condition;
    }

    public static class ConditionBean {
        /**
         * id : 522
         * sharePic : uploadfile/2015/0828/20150828051143937_thumb.png
         * shareMsg : 此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！！！
         * cardId : 15
         * cardTitle : 酱油3次卡1
         * cardDesc : 测试测试
         * cardUrl : uploadfile/2015/0828/20150828051123409_thumb.png
         * cardPrice : 0.03
         * beginTime : 1461202071
         * endTime : 1463794071
         * validNum : 3
         * courseLimit : 1
         * masterLimit : 3
         * totalNum : 3
         * remainNum : 2
         * isGiveaway : 0
         * condition_id : 522
         * condition_type : 1
         */

        private List<CardBean> card;
        /**
         * user_coupon_id : 157961
         * coupon_id : 61
         * coupon_name : 50券
         * intro : 50券
         * type : 1
         * price : 50.00
         * limit_price : 300.00
         * start_used_time : 1452567900
         * end_used_time : 1486782300
         * is_all_course : 1
         * is_all_card : 0
         * condition_id : 157961
         * condition_type : 2
         */

        private List<CouponBean> coupon;
        /**
         * condition_id : 0
         * condition_type : 4
         * title : 新用户购课减2.00元
         * price : 2.00
         * valid_time : 2016-05-20
         */

        private List<NewUserBean> new_user;

        public List<CardBean> getCard() {
            return card;
        }

        public void setCard(List<CardBean> card) {
            this.card = card;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public List<NewUserBean> getNew_user() {
            return new_user;
        }

        public void setNew_user(List<NewUserBean> new_user) {
            this.new_user = new_user;
        }

        public static class CardBean {
            private String id;
            private String sharePic;
            private String shareMsg;
            private String cardId;
            private String cardTitle;
            private String cardDesc;
            private String cardUrl;
            private String cardPrice;
            private String beginTime;
            private String endTime;
            private String validNum;
            private String courseLimit;
            private String masterLimit;
            private String totalNum;
            private String remainNum;
            private String isGiveaway;
            private String condition_id;
            private String condition_type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSharePic() {
                return sharePic;
            }

            public void setSharePic(String sharePic) {
                this.sharePic = sharePic;
            }

            public String getShareMsg() {
                return shareMsg;
            }

            public void setShareMsg(String shareMsg) {
                this.shareMsg = shareMsg;
            }

            public String getCardId() {
                return cardId;
            }

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public String getCardTitle() {
                return cardTitle;
            }

            public void setCardTitle(String cardTitle) {
                this.cardTitle = cardTitle;
            }

            public String getCardDesc() {
                return cardDesc;
            }

            public void setCardDesc(String cardDesc) {
                this.cardDesc = cardDesc;
            }

            public String getCardUrl() {
                return cardUrl;
            }

            public void setCardUrl(String cardUrl) {
                this.cardUrl = cardUrl;
            }

            public String getCardPrice() {
                return cardPrice;
            }

            public void setCardPrice(String cardPrice) {
                this.cardPrice = cardPrice;
            }

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getValidNum() {
                return validNum;
            }

            public void setValidNum(String validNum) {
                this.validNum = validNum;
            }

            public String getCourseLimit() {
                return courseLimit;
            }

            public void setCourseLimit(String courseLimit) {
                this.courseLimit = courseLimit;
            }

            public String getMasterLimit() {
                return masterLimit;
            }

            public void setMasterLimit(String masterLimit) {
                this.masterLimit = masterLimit;
            }

            public String getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(String totalNum) {
                this.totalNum = totalNum;
            }

            public String getRemainNum() {
                return remainNum;
            }

            public void setRemainNum(String remainNum) {
                this.remainNum = remainNum;
            }

            public String getIsGiveaway() {
                return isGiveaway;
            }

            public void setIsGiveaway(String isGiveaway) {
                this.isGiveaway = isGiveaway;
            }

            public String getCondition_id() {
                return condition_id;
            }

            public void setCondition_id(String condition_id) {
                this.condition_id = condition_id;
            }

            public String getCondition_type() {
                return condition_type;
            }

            public void setCondition_type(String condition_type) {
                this.condition_type = condition_type;
            }
        }

        public static class CouponBean {
            private String user_coupon_id;
            private String coupon_id;
            private String coupon_name;
            private String intro;
            private String type;
            private String price;
            private String limit_price;
            private String start_used_time;
            private String end_used_time;
            private String is_all_course;
            private String is_all_card;
            private String condition_id;
            private String condition_type;

            public String getUser_coupon_id() {
                return user_coupon_id;
            }

            public void setUser_coupon_id(String user_coupon_id) {
                this.user_coupon_id = user_coupon_id;
            }

            public String getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getCoupon_name() {
                return coupon_name;
            }

            public void setCoupon_name(String coupon_name) {
                this.coupon_name = coupon_name;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getLimit_price() {
                return limit_price;
            }

            public void setLimit_price(String limit_price) {
                this.limit_price = limit_price;
            }

            public String getStart_used_time() {
                return start_used_time;
            }

            public void setStart_used_time(String start_used_time) {
                this.start_used_time = start_used_time;
            }

            public String getEnd_used_time() {
                return end_used_time;
            }

            public void setEnd_used_time(String end_used_time) {
                this.end_used_time = end_used_time;
            }

            public String getIs_all_course() {
                return is_all_course;
            }

            public void setIs_all_course(String is_all_course) {
                this.is_all_course = is_all_course;
            }

            public String getIs_all_card() {
                return is_all_card;
            }

            public void setIs_all_card(String is_all_card) {
                this.is_all_card = is_all_card;
            }

            public String getCondition_id() {
                return condition_id;
            }

            public void setCondition_id(String condition_id) {
                this.condition_id = condition_id;
            }

            public String getCondition_type() {
                return condition_type;
            }

            public void setCondition_type(String condition_type) {
                this.condition_type = condition_type;
            }
        }

        public static class NewUserBean {
            private String condition_id;
            private String condition_type;
            private String title;
            private String price;
            private String valid_time;

            public String getCondition_id() {
                return condition_id;
            }

            public void setCondition_id(String condition_id) {
                this.condition_id = condition_id;
            }

            public String getCondition_type() {
                return condition_type;
            }

            public void setCondition_type(String condition_type) {
                this.condition_type = condition_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getValid_time() {
                return valid_time;
            }

            public void setValid_time(String valid_time) {
                this.valid_time = valid_time;
            }
        }
    }
}
