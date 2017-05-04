package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.news.article.NewsArticleActivity;
import com.example.rumens.showtime.news.article.NewsArticlePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */
@Module
public class NewsArticleModule {
    private final NewsArticleActivity mView;
    private final String mNewsId;

    public NewsArticleModule(NewsArticleActivity mView, String mNewsId) {
        this.mView = mView;
        this.mNewsId = mNewsId;
    }
    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new NewsArticlePresenter(mNewsId, mView);
    }
}
