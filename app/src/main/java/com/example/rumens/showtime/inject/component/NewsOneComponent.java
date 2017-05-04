package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;

import com.example.rumens.showtime.inject.modules.NewsOneModule;
import com.example.rumens.showtime.news.onenews.NewsListFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/22
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = NewsOneModule.class)
public interface NewsOneComponent {
    void inject(NewsListFragment fragment);
}
