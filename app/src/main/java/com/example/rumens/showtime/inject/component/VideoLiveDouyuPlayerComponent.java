package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.VideoLiveDouyuPlayerModule;
import com.example.rumens.showtime.video.videoliveplay.VideoPlayActivity;


import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/4
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = VideoLiveDouyuPlayerModule.class)
public interface VideoLiveDouyuPlayerComponent {
    void inject(VideoPlayActivity activity);
}
