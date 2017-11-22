package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu on 2016/4/21.
 */
public class MasterStartBean {

    /**
     * id : 分类id
     * name : 分类名称
     * pic_url : 分类图片地址
     */

    private List<CategoryListBean> category_list;

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
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
}
