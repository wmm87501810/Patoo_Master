package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by Pursue on 16/4/26.
 */
public class SoyCardBean {
    /**
     * id : 订单编号
     * sharePic : 分享的图片地址
     * shareMsg : 分享内容
     * cardId : 优惠卡编号
     * cardTitle : 卡片标题
     * cardDesc : 卡片介绍
     * cardUrl : 卡片图片
     * cardPrice : 卡片价格
     * beginTime : 开始时间
     * endTime : 结束时间
     * validNum : 单张卡有效次数；0为无数次
     * courseLimit : 课程使用次数限制，0为无限制
     * totalNum : 总共有效次数
     * remainNum : 剩余次数
     * isGiveaway : 0:未转赠；1:转赠中；2:转赠失败；3:转赠成功；4:被转赠卡状态
     */

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
    private String totalNum;
    private String remainNum;
    private String isGiveaway;

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
}
