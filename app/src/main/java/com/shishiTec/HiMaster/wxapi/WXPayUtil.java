package com.shishiTec.HiMaster.wxapi;

/**
 * Created by 仇杰 on 2015/5/15.
 * Description:
 */
public class WXPayUtil {

    //    public static final String APP_ID = "wxf2f565574a968187";
    public static final String APP_ID = "wx00d2ac16fd629e08";

    private WXPayUtil() {

    }

//    public static String genAppSign(PayReq req,String apiKey) {
//        StringBuilder sb = new StringBuilder();
//
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//
//        for (int i = 0; i < signParams.size(); i++) {
//            sb.append(signParams.get(i).getName());
//            sb.append('=');
//            sb.append(signParams.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(apiKey);
//        String appSign = getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", appSign);
//        return appSign;
//    }
//
//    private final static String getMessageDigest(byte[] buffer) {
//        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//        try {
//            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
//            mdTemp.update(buffer);
//            byte[] md = mdTemp.digest();
//            int j = md.length;
//            char str[] = new char[j * 2];
//            int k = 0;
//            for (int i = 0; i < j; i++) {
//                byte byte0 = md[i];
//                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
//                str[k++] = hexDigits[byte0 & 0xf];
//            }
//            return new String(str);
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
