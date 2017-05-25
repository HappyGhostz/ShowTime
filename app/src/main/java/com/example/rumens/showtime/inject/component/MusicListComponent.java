package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.MusicLisModule;
import com.example.rumens.showtime.music.listmusic.MusicListFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = MusicLisModule.class)
public interface MusicListComponent {
    void inject(MusicListFragment fragment);
}
