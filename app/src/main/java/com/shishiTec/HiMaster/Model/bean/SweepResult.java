package com.shishiTec.HiMaster.Model.bean;

/**
 * Created by hyu on 2016/5/24.
 */
public class SweepResult {
    private String code;//验证码
    private String id;//用户Id
    private String oid;//订单Id

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
