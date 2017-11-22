package com.shishiTec.HiMaster.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 */
public class DateUtil {
    /**
     *转换时间
     * @param time
     * @return
     */
    public static String getMonthDay(long time) {
        time = time * 1000;
        Date dat = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm");
        return formatter.format(dat);
    }

    /**
     *转换时间
     * @param time
     * @return
     */
    public static String getYearMonthDay(long time) {
        time = time * 1000;
        Date dat = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(dat);
    }
    /**
     * 得到两日期相差几个月
     */
    public static int getMonth(String startDate, String endDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        int monthday;
        try {
            String startTime = getYearMonthDay(Long.parseLong(startDate));
            String endTime = getYearMonthDay(Long.parseLong(endDate));
            Date startDate1 = f.parse(startTime);
            //开始时间与今天相比较
            Date endDate1 = f.parse(endTime);

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate1);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);
            int sDay = starCal.get(Calendar.DATE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate1);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);
            int eDay = endCal.get(Calendar.DATE);
            if (eYear==sYear){
                monthday = eMonth - sMonth;
            }else {
                monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
            }
            if (sDay < eDay) {
                monthday = monthday + 1;
            }
            return monthday;
        } catch (Exception e) {
            monthday = 1;
        }
        return monthday;
    }

    public static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012年10月03日 23:41:31
    }

    public static String getDateEN() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(new Date(System.currentTimeMillis()));
        return date1;// 2012-10-03 23:41:31
    }
}
