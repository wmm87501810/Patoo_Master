package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by Pursue on 16/4/28.
 */
public class UpLoadPictureBean {

    /**
     * url : 上传成功后图片资源url
     * width : 图片宽度
     * height : 图片高度
     */

    private String url;
    private int width;
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
