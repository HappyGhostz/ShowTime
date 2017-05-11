package com.example.rumens.showtime.base;

import com.example.rumens.showtime.api.bean.BookHelpList;
import com.example.rumens.showtime.api.bean.Recommend;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public interface IBookListBaseView extends IBaseView{
    void loadRecommendList(List<Recommend.RecommendBooks> list);

//    void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list);
    void loadCommunityList(List<BookHelpList.HelpsBean> list);
    void loadMoreCommunity(List<BookHelpList.HelpsBean> list);

    /**
     * 没有数据
     */
    void loadNoData();
}
