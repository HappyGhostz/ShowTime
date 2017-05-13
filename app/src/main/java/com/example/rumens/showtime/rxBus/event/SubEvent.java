package com.example.rumens.showtime.rxBus.event;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class SubEvent {
    private final String minor;
    private final String type;

    public SubEvent(String minor, String type) {
        this.minor = minor;
        this.type = type;
    }
}
