package com.example.rumens.showtime.reader.booksearch;

import com.example.rumens.showtime.api.bean.SearchDetail;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */

public interface ISearchBaseView extends IBaseView{
    void loadSearchResultList(List<SearchDetail.SearchBooks> list);
}
