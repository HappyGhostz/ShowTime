package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.BookRecommendListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.reader.booklist.BookTypeListFragment;
import com.example.rumens.showtime.reader.booklist.BookTypeListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */
@Module
public class BookTypeListModule {
    private final BookTypeListFragment mView;
    private final String mBookListType;

    public BookTypeListModule(BookTypeListFragment mView, String mBookListType) {
        this.mView = mView;
        this.mBookListType = mBookListType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new BookTypeListPresenter(mView, mBookListType);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new BookRecommendListAdapter(mView.getContext(),mBookListType);
    }
}
