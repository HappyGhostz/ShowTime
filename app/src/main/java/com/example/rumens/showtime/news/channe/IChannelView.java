package com.example.rumens.showtime.news.channe;

import com.example.rumens.showtime.local.NewsTypeInfo;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public interface IChannelView {
    /**
     * 显示数据
     * @param checkList     选中栏目
     * @param uncheckList   未选中栏目
     */
    void loadData(List<NewsTypeInfo> checkList, List<NewsTypeInfo> uncheckList);
}
