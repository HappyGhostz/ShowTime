package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.reader.bookrank.BookRankListFragment;
import com.example.rumens.showtime.reader.bookrank.BookRankListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */
@Module
public class BookRankListModule {
    private final BookRankListFragment mView;
    private final String mBookType;

    public BookRankListModule(BookRankListFragment mView, String mBookType) {
        this.mView = mView;
        this.mBookType = mBookType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new BookRankListPresenter(mView, mBookType);
    }

//    @PerFragment
//    @Provides
//    public BookRankListAdapter provideAdapter() {
//        return new BookRankListAdapter(mView.getContext());
//
//    }
}
