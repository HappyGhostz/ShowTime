package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.reader.bookdetail.BookDetailActivity;
import com.example.rumens.showtime.reader.bookdetail.BookDetailPresent;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */
@Module
public class BookDetialListModule {
    private final BookDetailActivity mView;
    private final String mBookId;

    public BookDetialListModule(BookDetailActivity mView, String mBookId) {
        this.mView = mView;
        this.mBookId = mBookId;
    }
    @PerActivity
    @Provides
    public IBasePresenter providePresent(){
        return new BookDetailPresent(mView,mBookId);
    }
}
