package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by hyu_anzhuo on 2016/5/20.
 */
public class CalendarBean {

    /**
     * course_cfg_id : 398
     * start_date : 1463500800
     * time_list : [{"id":"6497","start_time":"02:00","end_time":"03:00","num":"12","remain_num":"12"},{"id":"6522","start_time":"09:00","end_time":"10:00","num":"10","remain_num":"10"}]
     * is_full : 0
     */

    private String course_cfg_id;
    private String start_date;
    private String is_full;
    /**
     * id : 6497
     * start_time : 02:00
     * end_time : 03:00
     * num : 12
     * remain_num : 12
     */

    private List<TimeListBean> time_list;

    public String getCourse_cfg_id() {
        return course_cfg_id;
    }

    public void setCourse_cfg_id(String course_cfg_id) {
        this.course_cfg_id = course_cfg_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getIs_full() {
        return is_full;
    }

    public void setIs_full(String is_full) {
        this.is_full = is_full;
    }

    public List<TimeListBean> getTime_list() {
        return time_list;
    }

    public void setTime_list(List<TimeListBean> time_list) {
        this.time_list = time_list;
    }

    public static class TimeListBean {
        private String id;
        private String start_time;
        private String end_time;
        private String num;
        private String remain_num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getRemain_num() {
            return remain_num;
        }

        public void setRemain_num(String remain_num) {
            this.remain_num = remain_num;
        }
    }
}
