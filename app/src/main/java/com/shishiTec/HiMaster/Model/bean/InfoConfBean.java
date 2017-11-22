package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/17.
 */
public class InfoConfBean {

    /**
     * city_name : 上海市
     * city_code : sh
     */

    private List<CityListBean> city_list;
    /**
     * id : 20
     * name : 美食
     * pic_url : uploadfile/2015/0309/20150309055718593_thumb.png
     */

    private List<CategoryListBean> category_list;
    /**
     * id : 1
     * name : 热门
     * pic_url : uploadfile/superscript/icon_1.png
     */

    private List<SuperscriptListBean> superscript_list;
    /**
     * id : 1
     * name : 亲子
     */

    private List<InterestListBean> interest_list;
    /**
     * item_id : 1
     * item_name : 综合排序
     */

    private List<OrderTypeBean> order_type;
    /**
     * item_id : 0
     * item_name : 全部
     */

    private List<SelectTypeBean> select_type;

    public List<CityListBean> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<CityListBean> city_list) {
        this.city_list = city_list;
    }

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
    }

    public List<SuperscriptListBean> getSuperscript_list() {
        return superscript_list;
    }

    public void setSuperscript_list(List<SuperscriptListBean> superscript_list) {
        this.superscript_list = superscript_list;
    }

    public List<InterestListBean> getInterest_list() {
        return interest_list;
    }

    public void setInterest_list(List<InterestListBean> interest_list) {
        this.interest_list = interest_list;
    }

    public List<OrderTypeBean> getOrder_type() {
        return order_type;
    }

    public void setOrder_type(List<OrderTypeBean> order_type) {
        this.order_type = order_type;
    }

    public List<SelectTypeBean> getSelect_type() {
        return select_type;
    }

    public void setSelect_type(List<SelectTypeBean> select_type) {
        this.select_type = select_type;
    }

    public static class CityListBean {
        private String city_name;
        private String city_code;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }
    }

    public static class CategoryListBean {
        private String id;
        private String name;
        private String pic_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }

    public static class SuperscriptListBean {
        private String id;
        private String name;
        private String pic_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }

    public static class InterestListBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class OrderTypeBean {
        private String item_id;
        private String item_name;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }
    }

    public static class SelectTypeBean {
        private String item_id;
        private String item_name;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }
    }
}
