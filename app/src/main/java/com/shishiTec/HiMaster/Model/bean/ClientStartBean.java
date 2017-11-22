package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by Pursue on 16/4/18.
 */
public class ClientStartBean {
    private List<City> city_list;//城市列表
    private List<Category> category_list;//分类列表
    private List<Superscript> superscript_list;//角标列表
    private List<Interest> interest_list;//兴趣标签列表

    public List<Category> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<Category> category_list) {
        this.category_list = category_list;
    }

    public List<City> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<City> city_list) {
        this.city_list = city_list;
    }

    public List<Interest> getInterest_list() {
        return interest_list;
    }

    public void setInterest_list(List<Interest> interest_list) {
        this.interest_list = interest_list;
    }

    public List<Superscript> getSuperscript_list() {
        return superscript_list;
    }

    public void setSuperscript_list(List<Superscript> superscript_list) {
        this.superscript_list = superscript_list;
    }

    public class City{
        private String city_name; //城市名称
        private String city_code; //城市代码

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }

    public class Category{
        private String id; //分类ID
        private String name; //分类名称
        private String pic_url; //分类图片

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

    public class Superscript{
        private String id; //角标id
        private String name; //角标名称
        private String pic_url; //角标图片地址

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

    public class Interest{
        private String id; //兴趣标签id
        private String name; //兴趣标签名称

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
}
