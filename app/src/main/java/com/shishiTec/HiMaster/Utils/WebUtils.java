package com.shishiTec.HiMaster.Utils;

/**
 * Created by wangmengyan on 2016/6/5.
 */
public class WebUtils {
    private static WebUtils webUtils = null;
    private static final String masterUrl = "http://kaifa";
    private static final String appMasterUrlNameCourseDetail = "master://nmcourse_detail?course_id";
    private static final String appMasterUrlNameUserMaster = "master://nmuser_master?uid";
    private static final String appMasterUrlNameShareMasterDetail = "master://nmshare_master_detail?share_id";
    private static final String appMasterUrlNameShareUserDetail = "master://nmshare_user_detail?share_id";

    public static WebUtils getInstance() {
        if (webUtils == null) {
            webUtils = new WebUtils();
        }
        return webUtils;
    }

    public String getMasterUrl(String masterUrl) {
        String url = masterUrl.substring(0, 6);
        if (url.equals("http:/")) {
            return masterUrl;
        } else if (url.equals("master")) {
            if (masterUrl.substring(0, 12).equals("master://nmc")) {
                String course_id = masterUrl.substring(35);
                return "1" + course_id;
            } else if (masterUrl.substring(0, 12).equals("master://nmu")) {
                String user_master = masterUrl.substring(27);
                return "2" + user_master;
            } else if (masterUrl.substring(0, 12).equals("master://nms")) {
                if (masterUrl.substring(0, 18).equals("master://nmshare_m")) {
                    String share_master_detail = masterUrl.substring(40);
                    return "3" + share_master_detail;
                } else if (masterUrl.substring(0, 18).equals("master://nmshare_u")) {
                    String share_user_detail = masterUrl.substring(38);
                    return "4" + share_user_detail;
                }
            }
        }
        return null;
    }
}
