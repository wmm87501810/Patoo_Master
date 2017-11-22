package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/30.
 */
public class CardOrderBean {

    /**
     * card_id : 15
     * card_name : 酱油3次卡1
     * price : 0.03
     * intro : 此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有）课程！此卡在手，你便可在一个月内享受无限畅享Master达人平台的（所有
     * valid_time : 2016-06-24
     * user_money : 0
     * mobile : 13917410050
     * condition : {"card":[],"coupon":[{"user_coupon_id":"158392","coupon_id":"69","coupon_name":"酱油卡优惠券","intro":"酱油卡优惠券酱油卡优惠券酱油卡优惠券酱油卡优惠券","type":"1","price":"0.01","limit_price":"0.00","start_used_time":"1464158820","end_used_time":"1498286820","is_all_course":"0","is_all_card":"1","condition_id":"158392","condition_type":"2"},{"user_coupon_id":"158393","coupon_id":"69","coupon_name":"酱油卡优惠券","intro":"酱油卡优惠券酱油卡优惠券酱油卡优惠券酱油卡优惠券","type":"1","price":"0.01","limit_price":"0.00","start_used_time":"1464158820","end_used_time":"1498286820","is_all_course":"0","is_all_card":"1","condition_id":"158393","condition_type":"2"}],"new_user":[]}
     */

    private String card_id;
    private String card_name;
    private String price;
    private String intro;
    private String valid_time;
    private String user_money;
    private String mobile;
    private ConditionBean condition;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
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
        private List<?> card;
        /**
         * user_coupon_id : 158392
         * coupon_id : 69
         * coupon_name : 酱油卡优惠券
         * intro : 酱油卡优惠券酱油卡优惠券酱油卡优惠券酱油卡优惠券
         * type : 1
         * price : 0.01
         * limit_price : 0.00
         * start_used_time : 1464158820
         * end_used_time : 1498286820
         * is_all_course : 0
         * is_all_card : 1
         * condition_id : 158392
         * condition_type : 2
         */

        private List<CouponBean> coupon;
        private List<?> new_user;

        public List<?> getCard() {
            return card;
        }

        public void setCard(List<?> card) {
            this.card = card;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public List<?> getNew_user() {
            return new_user;
        }

        public void setNew_user(List<?> new_user) {
            this.new_user = new_user;
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
    }
}
