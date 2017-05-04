package com.example.rumens.showtime.news.channe;

import com.example.rumens.showtime.base.IBasePresenter;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

interface ILocalPresenter<T>extends IBasePresenter {
    /**
     * 插入数据
     * @param data  数据
     */
    void insert(T data);

    /**
     * 删除数据
     * @param data  数据
     */
    void delete(T data);

    /**
     * 更新数据
     * @param list   所有数据
     */
    void update(List<T> list);
}
