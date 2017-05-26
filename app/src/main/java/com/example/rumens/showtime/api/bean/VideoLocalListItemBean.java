package com.example.rumens.showtime.api.bean;

import android.database.Cursor;

import java.io.Serializable;

import io.vov.vitamio.provider.MediaStore;

/**
 * Created by e445 on 2017/5/8.
 */

public class VideoLocalListItemBean implements Serializable {
    public String title;
    public  String path;
    public int size;
    public int duration;
    public String artist;

    public VideoLocalListItemBean(String title, String path, int size, int duration,String artist) {
        this.title = title;
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.artist=artist;
    }
    /**
     * 通过游标转化为视频对象
     */
    public static VideoLocalListItemBean getInstanceFromCursor(Cursor cursor){
        VideoLocalListItemBean item = null;
        if(cursor!=null){
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
            item = new VideoLocalListItemBean(title,path,size,duration,null);
        }
        return item;
    }
    /**
     * 通过游标转化为音乐对象
     */
    public static VideoLocalListItemBean getInstanceAudioFromCursor(Cursor cursor){
        VideoLocalListItemBean item = null;
        if(cursor!=null){
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            item = new VideoLocalListItemBean(title,path,0,duration,artist);
        }
        return item;
    }

    @Override
    public String toString() {
        return "MediaItemInfo{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                '}';
    }
}
