package com.example.rumens.showtime.base;

import rx.functions.Action1;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public interface IRxBusPresenter extends IBasePresenter {
    /**
     * 注册
     * @param eventType
     * @param <T>
     */
    <T> void registerRxBus(Class<T> eventType, Action1<T> action);

    /**
     * 注销
     */
    void unregisterRxBus();
}
