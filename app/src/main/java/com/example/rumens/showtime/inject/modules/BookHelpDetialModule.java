package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.BookHelpDetialAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.reader.bookhelp.BookHelpDetailActivity;
import com.example.rumens.showtime.reader.bookhelp.BookHelpDetialPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */
@Module
public class BookHelpDetialModule {
    private final BookHelpDetailActivity mView;
    private final String mHelpBeanId;

    public BookHelpDetialModule(BookHelpDetailActivity mView, String mHelpBeanId) {
        this.mView = mView;
        this.mHelpBeanId = mHelpBeanId;
    }
    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new BookHelpDetialPresenter(mView, mHelpBeanId);
    }

    @PerActivity
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new BookHelpDetialAdapter(mView.getApplicationContext());
    }
}
