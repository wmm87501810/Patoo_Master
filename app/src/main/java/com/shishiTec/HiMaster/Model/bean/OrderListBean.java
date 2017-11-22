package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/27.
 */
public class OrderListBean {

    /**
     * day : 2016年04月28日
     * detail : [{"oid":"3042","cardType":"2","cardId":"158317","zfType":"5","buyDate":"2016年04月28日","buyDateTime":"2016/04/28 11:00:52","orderId":"D14618124528963","price":"1.00","eachPrice":"0.0","userMoney":"","orderStatus":"0","addtime":"1461812452","cid":"680","title":"阿拉","buyerUid":"9069","uid":"2971","nikeName":"convse","cover":"uploadfile/2015/0831/20150831054013658_thumb.jpg","imgTop":"uploadfile/2015/0601/20150601114211262_thumb.png","needScore":"0","courseStatus":"1","rejectReason":"","version":"1"}]
     */

    private String day;
    /**
     * oid : 3042
     * cardType : 2
     * cardId : 158317
     * zfType : 5
     * buyDate : 2016年04月28日
     * buyDateTime : 2016/04/28 11:00:52
     * orderId : D14618124528963
     * price : 1.00
     * eachPrice : 0.0
     * userMoney :
     * orderStatus : 0
     * addtime : 1461812452
     * cid : 680
     * title : 阿拉
     * buyerUid : 9069
     * uid : 2971
     * nikeName : convse
     * cover : uploadfile/2015/0831/20150831054013658_thumb.jpg
     * imgTop : uploadfile/2015/0601/20150601114211262_thumb.png
     * needScore : 0
     * courseStatus : 1
     * rejectReason :
     * version : 1
     */

    private List<DetailBean> detail;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
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
}
