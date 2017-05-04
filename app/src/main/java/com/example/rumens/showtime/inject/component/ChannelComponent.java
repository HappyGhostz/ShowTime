package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.ChannelModule;
import com.example.rumens.showtime.news.channe.ChannelActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = ChannelModule.class)
public interface ChannelComponent {
    void inject(ChannelActivity channelActivity);
}
