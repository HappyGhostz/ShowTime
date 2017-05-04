package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.SpecialModule;
import com.example.rumens.showtime.news.special.SpecialActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */
@PerActivity
@Component(modules = SpecialModule.class)
public interface SpecialComponent {
    void inject(SpecialActivity specialActivity);
}
