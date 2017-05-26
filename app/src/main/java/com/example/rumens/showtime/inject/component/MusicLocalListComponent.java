package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.MusicLocalListModule;
import com.example.rumens.showtime.music.localmusic.MusicLocalFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = MusicLocalListModule.class)
public interface MusicLocalListComponent {
    void inject(MusicLocalFragment fragment);
}
