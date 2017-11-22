package com.shishiTec.HiMaster.Model.realbean;

/**
 * Created by Pursue on 16/4/14.
 */
public class VerifyBean {
    private String code;//验证码
    private String expires_time;//验证码失效时间戳
    private String mobile;//手机号码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(String expires_time) {
        this.expires_time = expires_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
