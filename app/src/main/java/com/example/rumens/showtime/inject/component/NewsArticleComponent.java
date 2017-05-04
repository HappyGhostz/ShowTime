package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.NewsArticleModule;
import com.example.rumens.showtime.news.article.NewsArticleActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */
@PerActivity
@Component(modules = NewsArticleModule.class)
public interface NewsArticleComponent {
    void inject(NewsArticleActivity newsArticleActivity);
}
