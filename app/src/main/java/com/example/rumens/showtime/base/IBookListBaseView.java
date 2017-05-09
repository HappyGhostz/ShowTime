package com.example.rumens.showtime.base;

import com.example.rumens.showtime.api.bean.Recommend;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public interface IBookListBaseView extends IBaseView{
    void showRecommendList(List<Recommend.RecommendBooks> list);

//    void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list);
}
