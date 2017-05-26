package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.MusicRankingListDetailModule;
import com.example.rumens.showtime.music.listplay.MusicRankingListDetailActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = MusicRankingListDetailModule.class)
public interface MusicRankingListDetailComponent {
    void inject(MusicRankingListDetailActivity activity);
}
