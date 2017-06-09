package com.example.rumens.showtime.rxBus.event;

import android.support.annotation.IntDef;


import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.api.bean.SongLocalBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/31
 * @description
 */

public class MusicContralEvent {
    /**
     * 音乐播放器播放、暂停、上一首、下一首、播放顺序事件
     */
    public static final int MUSIC_PLAY = 30;
    public static final int MUSIC_PUSE = 31;
    public static final int MUSIC_PRE = 32;
    public static final int MUSIC_NEXT = 33;
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({MUSIC_PLAY, MUSIC_PUSE, MUSIC_PRE,MUSIC_NEXT})
    public @interface MusicContralEventType{}
    public int eventType;

    public SongLocalBean getSongBean() {
        return songBean;
    }

    public void setSongBean(SongLocalBean songBean) {
        this.songBean = songBean;
    }

    private SongLocalBean songBean;

    public SongDetailInfo getSongDetailInfo() {
        return songDetailInfo;
    }

    public void setSongDetailInfo(SongDetailInfo songDetailInfo) {
        this.songDetailInfo = songDetailInfo;
    }

    private SongDetailInfo songDetailInfo;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    private String string;

    public String  getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    private String list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public MusicContralEvent(@MusicContralEvent.MusicContralEventType int eventType, SongLocalBean songBean) {
        this.eventType = eventType;
        this.songBean = songBean;
    }
    public MusicContralEvent(@MusicContralEvent.MusicContralEventType int eventType, SongDetailInfo songBean) {
        this.eventType = eventType;
        this.songDetailInfo = songBean;
    }
    public MusicContralEvent(@MusicContralEvent.MusicContralEventType int eventType, String string) {
        this.eventType = eventType;
        this.string = string;
    }
    public MusicContralEvent(@MusicContralEventType int eventType, String list, String title) {
        this.eventType = eventType;
        this.list = list;
        this.title = title;
    }

}
