package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.NewsMultiListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.news.onenews.NewsListFragment;
import com.example.rumens.showtime.news.onenews.NewsListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */
@Module
public class NewsOneModule {
    private NewsListFragment mView;
    private String mNewsId;

    public NewsOneModule(NewsListFragment mView, String mNewsId) {
        this.mView = mView;
        this.mNewsId = mNewsId;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new NewsListPresenter(mView, mNewsId);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new NewsMultiListAdapter(mView.getContext());
    }
}
