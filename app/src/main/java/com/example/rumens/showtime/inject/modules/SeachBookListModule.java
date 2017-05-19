package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.SearchBookAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.reader.booksearch.SearchBookPresent;
import com.example.rumens.showtime.reader.booksearch.SearchResultActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */
@Module
public class SeachBookListModule {
    private final SearchResultActivity mView;
    private final String mBookName;

    public SeachBookListModule(SearchResultActivity mView, String mBookName) {
        this.mView = mView;
        this.mBookName = mBookName;
    }
    @PerActivity
    @Provides
    public IBasePresenter providesPresent(){
        return new SearchBookPresent(mView,mBookName);
    }
    @PerActivity
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new SearchBookAdapter(mView.getApplicationContext());
    }
}
