package com.example.rumens.showtime.api.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/3
 * @description
 */

public class TestBean {


    /**
     * error : 0
     * data : [{"room_id":"587035","room_src":"https://staticlive.douyucdn.cn/upload/web_pic/79481eff15b98454f2f0b3192d3e1627_thumb.png","vertical_src":"https://staticlive.douyucdn.cn/upload/web_pic/79481eff15b98454f2f0b3192d3e1627_thumb.png","isVertical":0,"cate_id":207,"room_name":"直播NBA：猛龙VS骑士","show_status":"1","subject":"","show_time":"1465389338","owner_uid":"11647214","specific_catalog":"","specific_status":"0","vod_quality":"0","nickname":"wxq4871269","online":165,"url":"/587035","game_url":"/directory/game/qezb","game_name":"企鹅直播","child_id":0,"avatar":"https://apic.douyucdn.cn/upload/avatar/face/201604/26/02792d98064ff7617ea4db7d692b2ff1_big.jpg","avatar_mid":"https://apic.douyucdn.cn/upload/avatar/face/201604/26/02792d98064ff7617ea4db7d692b2ff1_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar/face/201604/26/02792d98064ff7617ea4db7d692b2ff1_small.jpg","jumpUrl":"http://live.qq.com/10000188","isHide":1,"fans":"2288","ranktype":2,"is_noble_rec":0,"anchor_city":""}]
     */

    private int error;
    private List<DataBean> data;

    public static TestBean objectFromData(String str) {

        return new Gson().fromJson(str, TestBean.class);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * room_id : 587035
         * room_src : https://staticlive.douyucdn.cn/upload/web_pic/79481eff15b98454f2f0b3192d3e1627_thumb.png
         * vertical_src : https://staticlive.douyucdn.cn/upload/web_pic/79481eff15b98454f2f0b3192d3e1627_thumb.png
         * isVertical : 0
         * cate_id : 207
         * room_name : 直播NBA：猛龙VS骑士
         * show_status : 1
         * subject :
         * show_time : 1465389338
         * owner_uid : 11647214
         * specific_catalog :
         * specific_status : 0
         * vod_quality : 0
         * nickname : wxq4871269
         * online : 165
         * url : /587035
         * game_url : /directory/game/qezb
         * game_name : 企鹅直播
         * child_id : 0
         * avatar : https://apic.douyucdn.cn/upload/avatar/face/201604/26/02792d98064ff7617ea4db7d692b2ff1_big.jpg
         * avatar_mid : https://apic.douyucdn.cn/upload/avatar/face/201604/26/02792d98064ff7617ea4db7d692b2ff1_middle.jpg
         * avatar_small : https://apic.douyucdn.cn/upload/avatar/face/201604/26/02792d98064ff7617ea4db7d692b2ff1_small.jpg
         * jumpUrl : http://live.qq.com/10000188
         * isHide : 1
         * fans : 2288
         * ranktype : 2
         * is_noble_rec : 0
         * anchor_city :
         */

        private String room_id;
        private String room_src;
        private String vertical_src;
        private int isVertical;
        private int cate_id;
        private String room_name;
        private String show_status;
        private String subject;
        private String show_time;
        private String owner_uid;
        private String specific_catalog;
        private String specific_status;
        private String vod_quality;
        private String nickname;
        private int online;
        private String url;
        private String game_url;
        private String game_name;
        private int child_id;
        private String avatar;
        private String avatar_mid;
        private String avatar_small;
        private String jumpUrl;
        private int isHide;
        private String fans;
        private int ranktype;
        private int is_noble_rec;
        private String anchor_city;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getRoom_src() {
            return room_src;
        }

        public void setRoom_src(String room_src) {
            this.room_src = room_src;
        }

        public String getVertical_src() {
            return vertical_src;
        }

        public void setVertical_src(String vertical_src) {
            this.vertical_src = vertical_src;
        }

        public int getIsVertical() {
            return isVertical;
        }

        public void setIsVertical(int isVertical) {
            this.isVertical = isVertical;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getShow_status() {
            return show_status;
        }

        public void setShow_status(String show_status) {
            this.show_status = show_status;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        public String getOwner_uid() {
            return owner_uid;
        }

        public void setOwner_uid(String owner_uid) {
            this.owner_uid = owner_uid;
        }

        public String getSpecific_catalog() {
            return specific_catalog;
        }

        public void setSpecific_catalog(String specific_catalog) {
            this.specific_catalog = specific_catalog;
        }

        public String getSpecific_status() {
            return specific_status;
        }

        public void setSpecific_status(String specific_status) {
            this.specific_status = specific_status;
        }

        public String getVod_quality() {
            return vod_quality;
        }

        public void setVod_quality(String vod_quality) {
            this.vod_quality = vod_quality;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getGame_url() {
            return game_url;
        }

        public void setGame_url(String game_url) {
            this.game_url = game_url;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public int getChild_id() {
            return child_id;
        }

        public void setChild_id(int child_id) {
            this.child_id = child_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar_mid() {
            return avatar_mid;
        }

        public void setAvatar_mid(String avatar_mid) {
            this.avatar_mid = avatar_mid;
        }

        public String getAvatar_small() {
            return avatar_small;
        }

        public void setAvatar_small(String avatar_small) {
            this.avatar_small = avatar_small;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getIsHide() {
            return isHide;
        }

        public void setIsHide(int isHide) {
            this.isHide = isHide;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public int getRanktype() {
            return ranktype;
        }

        public void setRanktype(int ranktype) {
            this.ranktype = ranktype;
        }

        public int getIs_noble_rec() {
            return is_noble_rec;
        }

        public void setIs_noble_rec(int is_noble_rec) {
            this.is_noble_rec = is_noble_rec;
        }

        public String getAnchor_city() {
            return anchor_city;
        }

        public void setAnchor_city(String anchor_city) {
            this.anchor_city = anchor_city;
        }
    }
}
