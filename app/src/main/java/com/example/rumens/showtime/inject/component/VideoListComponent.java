package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.App;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.VideoListModule;
import com.example.rumens.showtime.video.kankan.VideoListFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = VideoListModule.class)
public interface VideoListComponent {
    void inject(VideoListFragment fragment);
}
