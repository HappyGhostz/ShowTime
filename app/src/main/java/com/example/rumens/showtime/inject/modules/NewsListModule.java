package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.IRxBusPresenter;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.news.main.NewsMainFragment;
import com.example.rumens.showtime.news.main.NewsMainPresenter;
import com.example.rumens.showtime.rxBus.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */
@Module
public class NewsListModule {
    private NewsMainFragment newsMainFragment;

    public NewsListModule(NewsMainFragment newsMainFragment) {

        this.newsMainFragment = newsMainFragment;
    }

    @Provides
    public IRxBusPresenter provideMainPresenter(DaoSession daoSession, RxBus rxBus) {
        return new NewsMainPresenter(newsMainFragment, daoSession.getNewsTypeInfoDao(), rxBus);
    }


    @Provides
    public ViewPagerAdapter provideViewPagerAdapter() {
        return new ViewPagerAdapter(newsMainFragment.getChildFragmentManager());
    }
}
