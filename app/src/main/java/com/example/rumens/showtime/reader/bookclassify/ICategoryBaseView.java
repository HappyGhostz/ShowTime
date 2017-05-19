package com.example.rumens.showtime.reader.bookclassify;

import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public interface ICategoryBaseView extends IBaseView{
    /**
     * 加载数据
     * @param data
     */
    void loadCategoryList(BooksByCats data);
    /**
     * 加载更多数据
     * @param data
     */
    void loadMoreCategoryList(BooksByCats data);
}
