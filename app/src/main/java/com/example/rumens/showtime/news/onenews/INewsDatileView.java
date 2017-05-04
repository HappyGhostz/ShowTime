package com.example.rumens.showtime.news.onenews;

import com.example.rumens.showtime.adapter.item.NewsMultiItem;
import com.example.rumens.showtime.api.bean.NewsInfo;
import com.example.rumens.showtime.base.ILoadDataView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public interface INewsDatileView extends ILoadDataView<List<NewsMultiItem>> {
    /**
     * 加载广告数据
     * @param newsBean 新闻
     */
    void loadAdData(NewsInfo newsBean);
}
