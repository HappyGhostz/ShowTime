package com.example.rumens.showtime.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/2
 * @description
 */

public class DouyuLiveListItemBean implements Parcelable{


    /**
     * error : 0
     * data : [{"room_id":"168000","room_src":"https://rpic.douyucdn.cn/a1705/02/16/168000_170502160717.jpg","vertical_src":"https://rpic.douyucdn.cn/a1705/02/16/168000_170502160717.jpg","isVertical":0,"cate_id":1,"room_name":"斗鱼丁义珍:  非洲野区教学。","show_status":"1","subject":"","show_time":"1493711977","owner_uid":"6340121","specific_catalog":"","specific_status":"0","vod_quality":"0","nickname":"小猪是超级大帅Bi","online":4025,"url":"/168000","game_url":"/directory/game/LOL","game_name":"英雄联盟","child_id":33,"avatar":"https://apic.douyucdn.cn/upload/avatar/face/201604/06/ae72347e5e33519fd04f11a3ae51b6db_big.jpg","avatar_mid":"https://apic.douyucdn.cn/upload/avatar/face/201604/06/ae72347e5e33519fd04f11a3ae51b6db_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar/face/201604/06/ae72347e5e33519fd04f11a3ae51b6db_small.jpg","jumpUrl":"","fans":"199506","ranktype":0,"is_noble_rec":0,"anchor_city":""}]
     */

    private int error;
    private List<DataBean> data;

    protected DouyuLiveListItemBean(Parcel in) {
        error = in.readInt();
    }

    public static final Creator<DouyuLiveListItemBean> CREATOR = new Creator<DouyuLiveListItemBean>() {
        @Override
        public DouyuLiveListItemBean createFromParcel(Parcel in) {
            return new DouyuLiveListItemBean(in);
        }

        @Override
        public DouyuLiveListItemBean[] newArray(int size) {
            return new DouyuLiveListItemBean[size];
        }
    };

    public static DouyuLiveListItemBean objectFromData(String str) {

        return new Gson().fromJson(str, DouyuLiveListItemBean.class);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(error);
    }

    public static class DataBean implements Parcelable{
        /**
         * room_id : 168000
         * room_src : https://rpic.douyucdn.cn/a1705/02/16/168000_170502160717.jpg
         * vertical_src : https://rpic.douyucdn.cn/a1705/02/16/168000_170502160717.jpg
         * isVertical : 0
         * cate_id : 1
         * room_name : 斗鱼丁义珍:  非洲野区教学。
         * show_status : 1
         * subject :
         * show_time : 1493711977
         * owner_uid : 6340121
         * specific_catalog :
         * specific_status : 0
         * vod_quality : 0
         * nickname : 小猪是超级大帅Bi
         * online : 4025
         * url : /168000
         * game_url : /directory/game/LOL
         * game_name : 英雄联盟
         * child_id : 33
         * avatar : https://apic.douyucdn.cn/upload/avatar/face/201604/06/ae72347e5e33519fd04f11a3ae51b6db_big.jpg
         * avatar_mid : https://apic.douyucdn.cn/upload/avatar/face/201604/06/ae72347e5e33519fd04f11a3ae51b6db_middle.jpg
         * avatar_small : https://apic.douyucdn.cn/upload/avatar/face/201604/06/ae72347e5e33519fd04f11a3ae51b6db_small.jpg
         * jumpUrl :
         * fans : 199506
         * ranktype : 0
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
        private String fans;
        private int ranktype;
        private int is_noble_rec;
        private String anchor_city;

        protected DataBean(Parcel in) {
            room_id = in.readString();
            room_src = in.readString();
            vertical_src = in.readString();
            isVertical = in.readInt();
            cate_id = in.readInt();
            room_name = in.readString();
            show_status = in.readString();
            subject = in.readString();
            show_time = in.readString();
            owner_uid = in.readString();
            specific_catalog = in.readString();
            specific_status = in.readString();
            vod_quality = in.readString();
            nickname = in.readString();
            online = in.readInt();
            url = in.readString();
            game_url = in.readString();
            game_name = in.readString();
            child_id = in.readInt();
            avatar = in.readString();
            avatar_mid = in.readString();
            avatar_small = in.readString();
            jumpUrl = in.readString();
            fans = in.readString();
            ranktype = in.readInt();
            is_noble_rec = in.readInt();
            anchor_city = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(room_id);
            dest.writeString(room_src);
            dest.writeString(vertical_src);
            dest.writeInt(isVertical);
            dest.writeInt(cate_id);
            dest.writeString(room_name);
            dest.writeString(show_status);
            dest.writeString(subject);
            dest.writeString(show_time);
            dest.writeString(owner_uid);
            dest.writeString(specific_catalog);
            dest.writeString(specific_status);
            dest.writeString(vod_quality);
            dest.writeString(nickname);
            dest.writeInt(online);
            dest.writeString(url);
            dest.writeString(game_url);
            dest.writeString(game_name);
            dest.writeInt(child_id);
            dest.writeString(avatar);
            dest.writeString(avatar_mid);
            dest.writeString(avatar_small);
            dest.writeString(jumpUrl);
            dest.writeString(fans);
            dest.writeInt(ranktype);
            dest.writeInt(is_noble_rec);
            dest.writeString(anchor_city);
        }
    }
}
