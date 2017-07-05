package com.example.rumens.showtime.rxBus;

import com.example.rumens.showtime.rxBus.event.RefreshCollectionIconEvent;
import com.example.rumens.showtime.rxBus.event.RefreshCollectionListEvent;
import com.example.rumens.showtime.rxBus.event.SubEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class EventManager {
    public static void refreshCollectionList() {
        EventBus.getDefault().post(new RefreshCollectionListEvent());
    }

    public static void refreshCollectionIcon() {
        EventBus.getDefault().post(new RefreshCollectionIconEvent());
    }

    public static void refreshSubCategory(String minor, String type) {
        EventBus.getDefault().post(new SubEvent(minor, type));
    }

    public  static void postSomeMessage(String message){
        EventBus.getDefault().post(message);
    }
}
