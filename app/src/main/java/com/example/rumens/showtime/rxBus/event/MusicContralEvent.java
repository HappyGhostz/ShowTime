package com.example.rumens.showtime.rxBus.event;

import android.support.annotation.IntDef;


import com.example.rumens.showtime.api.bean.SongLocalBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

    public MusicContralEvent(@MusicContralEvent.MusicContralEventType int eventType, SongLocalBean songBean) {
        this.eventType = eventType;
        this.songBean = songBean;
    }

}
