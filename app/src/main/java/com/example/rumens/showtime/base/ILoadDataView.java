package com.example.rumens.showtime.base;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public interface ILoadDataView<T> extends IBaseView {
    /**
     * 加载数据
     * @param data 数据
     */
    void loadData(T data);

    /**
     * 加载更多
     * @param data 数据
     */
    void loadMoreData(T data);

    /**
     * 没有数据
     */
    void loadNoData();
}
