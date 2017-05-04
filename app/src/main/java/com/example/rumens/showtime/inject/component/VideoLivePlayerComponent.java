package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.VideoLivePlayerModule;
import com.example.rumens.showtime.video.videoplay.VideoPlayActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = VideoLivePlayerModule.class)
public interface VideoLivePlayerComponent {
    void inject(VideoPlayActivity activity);
}
