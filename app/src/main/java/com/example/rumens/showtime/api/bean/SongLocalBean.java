package com.example.rumens.showtime.api.bean;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class SongLocalBean {
    public String title;
    public  String path;
    public String size;
    public int duration;
    public String artist;
    public String  albun_id;

    public SongLocalBean(String title, String path, String size, int duration, String artist, String albun_id) {
        this.title = title;
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.artist = artist;
        this.albun_id = albun_id;
    }

    @Override
    public String toString() {
        return "SongLocalBean{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", artist='" + artist + '\'' +
                ", albun_id=" + albun_id +
                '}';
    }
}
