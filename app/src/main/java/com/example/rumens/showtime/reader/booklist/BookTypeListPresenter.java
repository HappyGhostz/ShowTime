package com.example.rumens.showtime.reader.booklist;

import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.base.IBookListBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookTypeListPresenter implements IBasePresenter {
    private final IBookListBaseView mView;
    private final String mBookListType;

    public BookTypeListPresenter(IBookListBaseView mView, String mBookListType) {
        this.mView = mView;
        this.mBookListType = mBookListType;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }
}
