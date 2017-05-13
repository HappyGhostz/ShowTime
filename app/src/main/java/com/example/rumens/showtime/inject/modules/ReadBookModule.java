package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.reader.bookread.BookReadPresentr;
import com.example.rumens.showtime.reader.bookread.IBookReadPresenter;
import com.example.rumens.showtime.reader.bookread.ReadActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */
@Module
public class ReadBookModule {
    private ReadActivity mView;

    public ReadBookModule(ReadActivity mView) {
        this.mView = mView;
    }
    @PerActivity
    @Provides
    public IBookReadPresenter providesBookReadPreaenter(){
        return  new BookReadPresentr(mView);
    }
}
