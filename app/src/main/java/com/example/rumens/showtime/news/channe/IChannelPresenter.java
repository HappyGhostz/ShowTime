package com.example.rumens.showtime.news.channe;



/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public interface IChannelPresenter<T> extends ILocalPresenter<T>{
    /**
     * 交换
     * @param fromPos
     * @param toPos
     */
    void swap(int fromPos, int toPos);

}
