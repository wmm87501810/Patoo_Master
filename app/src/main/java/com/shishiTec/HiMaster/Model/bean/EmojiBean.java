package com.shishiTec.HiMaster.Model.bean;

/**
 * Description:表情
 */
public class EmojiBean {
    private String fileName;//表情文件名
    private String key;//表情名
    private String text;//key所对应的文本形式

    public EmojiBean() {
    }

    public EmojiBean(String fileName, String key, String text) {
        this.fileName = fileName;
        this.key = key;
        this.text = text;
    }

    public String getFileName() {
        return fileName == null ? "" : fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
