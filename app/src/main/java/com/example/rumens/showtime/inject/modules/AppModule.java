package com.example.rumens.showtime.inject.modules;

import android.content.Context;

import com.example.rumens.showtime.App;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.rxBus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/19
 * @description
 */
@Module
public class AppModule {
    private App app;
    private DaoSession mDaoSession;
    private RxBus mRxBus;

    public AppModule(App app, DaoSession mDaoSession, RxBus mRxBus) {

        this.app = app;
        this.mDaoSession = mDaoSession;
        this.mRxBus = mRxBus;
    }
    @Provides
    @Singleton
    Context provideAppContext() {
        return app;
    }
    @Provides
    @Singleton
    RxBus provideRxBus() {
        return mRxBus;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        return mDaoSession;
    }
}
