package com.example.rumens.showtime.reader.bookread;

import com.example.rumens.showtime.base.IBasePresenter;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public interface IBookReadPresenter extends IBasePresenter{
    void getBookMixAToc(String bookId, String view);

    void getChapterRead(String url, int chapter);
}
