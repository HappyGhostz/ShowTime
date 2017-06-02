package com.example.rumens.showtime.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class SongLocalBean implements Parcelable{
    public String title;
    public  String path;
    public String size;
    public int duration;
    public String artist;
    public int  albun_id;
    public int  _id;
    public String album;
    public String picurl;

    public SongLocalBean(int  _id,String title, String path, String size, int duration, String artist,String album,int  albun_id,String picurl) {
        this._id = _id;
        this.title = title;
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.artist = artist;
        this.albun_id = albun_id;
        this.album = album;
        this.picurl = picurl;
    }

    protected SongLocalBean(Parcel in) {
        title = in.readString();
        path = in.readString();
        size = in.readString();
        duration = in.readInt();
        artist = in.readString();
        albun_id = in.readInt();
        _id = in.readInt();
        album = in.readString();
        picurl = in.readString();
    }

    public static final Creator<SongLocalBean> CREATOR = new Creator<SongLocalBean>() {
        @Override
        public SongLocalBean createFromParcel(Parcel in) {
            return new SongLocalBean(in);
        }

        @Override
        public SongLocalBean[] newArray(int size) {
            return new SongLocalBean[size];
        }
    };

    @Override
    public String toString() {
        return "SongLocalBean{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", artist='" + artist + '\'' +
                ", albun_id=" + album +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(path);
        dest.writeString(size);
        dest.writeInt(duration);
        dest.writeString(artist);
        dest.writeInt(albun_id);
        dest.writeInt(_id);
        dest.writeString(album);
        dest.writeString(picurl);
    }
}
