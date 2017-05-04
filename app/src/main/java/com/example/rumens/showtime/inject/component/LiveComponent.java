package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.LiveModule;
import com.example.rumens.showtime.video.live.LiveFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = LiveModule.class)
public interface  LiveComponent {
    void inject(LiveFragment liveFragment);
}
