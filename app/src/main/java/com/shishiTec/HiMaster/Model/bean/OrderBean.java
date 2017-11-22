package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by Pursue on 16/4/27.
 */
public class OrderBean {

    /**
     * oid : 主键
     * cardType : 类型 1:卡2:优惠券3:M点
     * cardId :   卡或是优惠券编号
     * zfType : 支付方式,0：M点；1：支付宝；2：银联；3：微信页面；4：微信app；5：优惠支付；6：验证码；7：转赠；8：wap支付宝；9：百度支付
     * buyDate : 支付时间
     * buyDateTime : 支付时间
     * orderId : 订单ID
     * price : 价格，当zfType为0时为M点数
     * eachPrice : 每个订单的总价
     * userMoney :钱包使用金额
     * orderStatus : 订单状态 0:待上课/待验单;1：已使用，2：已过期，3：无效；4：未使用/待接单; 5:退款/已拒单;
     * addtime : 下单时间
     * cid : 购买的产品ID（课程ID，或者是活动ID）
     * title : 订单产品的标题
     * buyerUid : 购买用户ID
     * uid : 达人ID
     * nikeName : 昵称
     * cover : 课程封面
     * imgTop : 头像地址
     * needScore : 是否需要评分，1为待评分，2为已评分，
     * courseStatus : 课程状态，0:待审核，1:已上架，2：已下架，3：已拒绝, 4:待上架，5：下架不显示, 6：预约课程
     * rejectReason : 拒绝订单的原因
     * version : 订单版本
     */

    private String oid;
    private String cardType;
    private String cardId;
    private String zfType;
    private String buyDate;
    private String buyDateTime;
    private String orderId;
    private String price;
    private String eachPrice;
    private String userMoney;
    private String orderStatus;
    private String addtime;
    private String cid;
    private String title;
    private String buyerUid;
    private String uid;
    private String nikeName;
    private String cover;
    private String imgTop;
    private String needScore;
    private String courseStatus;
    private String rejectReason;
    private String version;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getZfType() {
        return zfType;
    }

    public void setZfType(String zfType) {
        this.zfType = zfType;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getBuyDateTime() {
        return buyDateTime;
    }

    public void setBuyDateTime(String buyDateTime) {
        this.buyDateTime = buyDateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEachPrice() {
        return eachPrice;
    }

    public void setEachPrice(String eachPrice) {
        this.eachPrice = eachPrice;
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getImgTop() {
        return imgTop;
    }

    public void setImgTop(String imgTop) {
        this.imgTop = imgTop;
    }

    public String getNeedScore() {
        return needScore;
    }

    public void setNeedScore(String needScore) {
        this.needScore = needScore;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
