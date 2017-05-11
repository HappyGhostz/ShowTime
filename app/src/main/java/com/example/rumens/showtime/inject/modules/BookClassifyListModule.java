package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.ClassifyListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.reader.bookclassify.BookClassifyListFragment;
import com.example.rumens.showtime.reader.bookclassify.BookClassifyListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */
@Module
public class BookClassifyListModule {
    private final BookClassifyListFragment mView;
    private final String mBookType;

    public BookClassifyListModule(BookClassifyListFragment mView, String mBookType) {
        this.mView = mView;
        this.mBookType = mBookType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new BookClassifyListPresenter(mView, mBookType);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new ClassifyListAdapter(mView.getContext());

    }
}
