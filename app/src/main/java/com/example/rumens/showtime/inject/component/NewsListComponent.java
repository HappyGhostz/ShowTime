package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.NewsListModule;
import com.example.rumens.showtime.news.main.NewsMainFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

@PerFragment
@Component(dependencies = AppComponent.class, modules = NewsListModule.class)
public interface NewsListComponent {
    void inject(NewsMainFragment fragment);
}

