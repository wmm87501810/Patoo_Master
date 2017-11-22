package com.shishiTec.HiMaster.Model.realbean;

import java.util.List;

/**
 * Created by 83802 on 2017/8/18.
 */

public class UserdetailsInfo {
    /* "img": {
        "img_0": "uploadfile/2017/0727/20170727060944676_thumb.jpg",
                "img_1": "uploadfile/2017/0727/20170727060954637_thumb.jpg",
                "img_2": "uploadfile/2017/0727/20170727060959802_thumb.jpg",
                "img_3": "",
                "img_4": "",
                "img_num": "3"
    },
            "detail": {
        "uid": "1",
                "nickname": "你是我的",
                "sex": "1",
                "age": "18",
                "province": "上海市",
                "signature": "jack",
                "like_you": "103",
                "ranking": "15"
    },
            "toys_list": [
    {
        "toys_id": "1",
            "cumulative_number": "120"
    }
        ]*/
    private Imgs img;
    private Detail detail;
    private List<ToysList> toys_list;

    public Imgs getImg() {
        return img;
    }

    public void setImg(Imgs img) {
        this.img = img;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public List<ToysList> getToys_list() {
        return toys_list;
    }

    public void setToys_list(List<ToysList> toys_list) {
        this.toys_list = toys_list;
    }



    public static class Imgs{
        public String img_0;
        public String img_1;
        public String img_2;
        public String img_3;
        public String img_4;
        public String img_num;

        public String getImg_0() {
            return img_0;
        }

        public void setImg_0(String img_0) {
            this.img_0 = img_0;
        }

        public String getImg_1() {
            return img_1;
        }

        public void setImg_1(String img_1) {
            this.img_1 = img_1;
        }

        public String getImg_2() {
            return img_2;
        }

        public void setImg_2(String img_2) {
            this.img_2 = img_2;
        }

        public String getImg_3() {
            return img_3;
        }

        public void setImg_3(String img_3) {
            this.img_3 = img_3;
        }

        public String getImg_4() {
            return img_4;
        }

        public void setImg_4(String img_4) {
            this.img_4 = img_4;
        }

        public String getImg_num() {
            return img_num;
        }

        public void setImg_num(String img_num) {
            this.img_num = img_num;
        }
    }
    public static class Detail{
        public String uid;
        public String nickname;
        public String sex;
        public String age;
        public String province;
        public String signature;
        public String like_you;
        public String ranking;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getLike_you() {
            return like_you;
        }

        public void setLike_you(String like_you) {
            this.like_you = like_you;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }
    }
    public static class ToysList{
        public String  toys_id;
        public String  cumulative_number;

        public String getToys_id() {
            return toys_id;
        }

        public void setToys_id(String toys_id) {
            this.toys_id = toys_id;
        }

        public String getCumulative_number() {
            return cumulative_number;
        }

        public void setCumulative_number(String cumulative_number) {
            this.cumulative_number = cumulative_number;
        }
    }

}
