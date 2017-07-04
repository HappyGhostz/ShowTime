package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.SeachMusicShowListModule;
import com.example.rumens.showtime.music.searchmusic.SearchMusicShow;

import dagger.Component;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = SeachMusicShowListModule.class)
public interface SeachMusicShowListComponent {
    void inject(SearchMusicShow activity);
}
