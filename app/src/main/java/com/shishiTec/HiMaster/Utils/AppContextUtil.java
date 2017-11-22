package com.shishiTec.HiMaster.Utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class AppContextUtil {
    private static Context sContext;

    private AppContextUtil() {

    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        if (sContext == null) {
            throw new NullPointerException("the context is null,please init AppContextUtil in Application first.");
        }
        return sContext;
    }


    /**
     *获得deviceID
     */
    public static String getdeviceID(){
        String deviceID;
        TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
        deviceID = tm.getDeviceId();

        return deviceID;
    }
}
