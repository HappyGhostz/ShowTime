package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.VideoDouyuPhonePlayerModule;
import com.example.rumens.showtime.inject.modules.VideoLiveDouyuPlayerModule;
import com.example.rumens.showtime.video.videoplay.DouyuPhonePlayActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/5
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = VideoDouyuPhonePlayerModule.class)
public interface VideoDouyuPhonePlayerComponent {
    void inject(DouyuPhonePlayActivity activity);
}
