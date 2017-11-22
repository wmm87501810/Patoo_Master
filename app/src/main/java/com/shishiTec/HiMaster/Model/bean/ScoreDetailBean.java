package com.shishiTec.HiMaster.Model.bean;

import java.util.List;

/**
 * Created by Pursue on 16/4/26.
 */
public class ScoreDetailBean {
    private String score;//当前积分数
    private List<Detail> detail;

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public class Detail{
        private String month;
        private List<Credit> credit;

        public List<Credit> getCredit() {
            return credit;
        }

        public void setCredit(List<Credit> credit) {
            this.credit = credit;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }

    public class Credit{
        /**
         * month : 201606
         * addtime : 时间
         * get : 获取的积分数
         * put : 消费的积分数
         * instruct : 来源说明
         * game_id :游戏ID
         * type :积分类型
         */

        private String month;
        private String addtime;
        private String get;
        private String put;
        private String instruct;
        private String game_id;
        private String type;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getGet() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }

        public String getPut() {
            return put;
        }

        public void setPut(String put) {
            this.put = put;
        }

        public String getInstruct() {
            return instruct;
        }

        public void setInstruct(String instruct) {
            this.instruct = instruct;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
