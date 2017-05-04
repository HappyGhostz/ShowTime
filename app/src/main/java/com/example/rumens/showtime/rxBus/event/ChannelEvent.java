package com.example.rumens.showtime.rxBus.event;

import android.support.annotation.IntDef;

import com.example.rumens.showtime.local.NewsTypeInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public class ChannelEvent {
    /**
     * 频道事件：添加、删除和交换位置
     */
    public static final int ADD_EVENT = 301;
    public static final int DEL_EVENT = 302;
    public static final int SWAP_EVENT = 303;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({ADD_EVENT, DEL_EVENT, SWAP_EVENT})
    public @interface ChannelEventType{}

    public int eventType;
    public NewsTypeInfo newsInfo;
    public int fromPos = -1;
    public int toPos = -1;

    public ChannelEvent(@ChannelEventType int eventType, NewsTypeInfo newsInfo) {
        this.eventType = eventType;
        this.newsInfo = newsInfo;
    }

    public ChannelEvent(@ChannelEventType int eventType) {
        this.eventType = eventType;
    }

    public ChannelEvent(@ChannelEventType int eventType, int fromPos, int toPos) {
        this.eventType = eventType;
        this.fromPos = fromPos;
        this.toPos = toPos;
    }
}
