package com.example.rumens.showtime.news.main;

import com.example.rumens.showtime.local.NewsTypeInfo;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/19
 * @description
 */

public interface INewsView {
    /**
     * 显示数据
     * @param checkList     选中栏目
     */
    void loadData(List<NewsTypeInfo> checkList);
}
