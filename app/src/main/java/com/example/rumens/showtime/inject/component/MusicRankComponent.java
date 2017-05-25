package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.MusicRankModule;
import com.example.rumens.showtime.music.rankmusic.MusicRankFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = MusicRankModule.class)
public interface MusicRankComponent {
    void inject(MusicRankFragment fragment);
}
