package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.ChannelAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.news.channe.ChannelActivity;
import com.example.rumens.showtime.news.channe.ChannelPresenter;
import com.example.rumens.showtime.news.channe.IChannelPresenter;
import com.example.rumens.showtime.rxBus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */
@Module
public class ChannelModule {
    private ChannelActivity mView;

    public ChannelModule(ChannelActivity mView) {
        this.mView = mView;
    }

    @Provides
    public BaseQuickAdapter provideManageAdapter(){
        return new ChannelAdapter(mView);
    }
    @PerActivity
    @Provides
    public IChannelPresenter provideManagePresenter(DaoSession daoSession, RxBus rxBus){
        return new ChannelPresenter(mView,daoSession.getNewsTypeInfoDao(),rxBus);
    }
}
