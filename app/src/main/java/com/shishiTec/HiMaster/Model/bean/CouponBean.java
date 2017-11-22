package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by Pursue on 16/4/26.
 */
public class CouponBean {
        /**
         * coupon_id : 代金券id
         * coupon_name : 券名
         * intro : 券介绍
         * type : 类型，1 抵用券 2打折券
         * price : 可抵用价格
         * limit_price : 满足这个价格才能使用
         * start_used_time : 开始使用时间
         * end_used_time : 结束使用时间
         */

        private String coupon_id;
        private String coupon_name;
        private String intro;
        private String type;
        private String price;
        private String limit_price;
        private String start_used_time;
        private String end_used_time;

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

        public String getEnd_used_time() {
                return end_used_time;
        }

        public void setEnd_used_time(String end_used_time) {
                this.end_used_time = end_used_time;
        }

        public String getIntro() {
                return intro;
        }

        public void setIntro(String intro) {
                this.intro = intro;
        }

        public String getLimit_price() {
                return limit_price;
        }

        public void setLimit_price(String limit_price) {
                this.limit_price = limit_price;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        public String getStart_used_time() {
                return start_used_time;
        }

        public void setStart_used_time(String start_used_time) {
                this.start_used_time = start_used_time;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }
}
