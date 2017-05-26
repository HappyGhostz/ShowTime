package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.MusicListDetailModule;
import com.example.rumens.showtime.music.listplay.MusicListDetialActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = MusicListDetailModule.class)
public interface MusicListDetailComponent {
    void inject(MusicListDetialActivity activity);
}
