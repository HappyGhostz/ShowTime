package com.example.rumens.showtime.reader.bookdetail;

import com.example.rumens.showtime.api.bean.BookDetail;
import com.example.rumens.showtime.api.bean.HotReview;
import com.example.rumens.showtime.api.bean.RecommendBookList;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public interface BookDetailBaseView extends IBaseView{
    void loadBookDetail(BookDetail data);

    void loadHotReview(List<HotReview.Reviews> list);

    void loadRecommendBookList(List<RecommendBookList.RecommendBook> list);
}
