package com.example.rumens.showtime.news.article;

import com.example.rumens.showtime.api.bean.NewsDetailInfo;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

interface INewsArticleView extends IBaseView{
    /**
     * 显示数据
     * @param newsDetailBean 新闻详情
     */
    void loadData(NewsDetailInfo newsDetailBean);
}
