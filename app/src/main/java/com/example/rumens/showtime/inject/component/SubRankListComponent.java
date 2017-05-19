package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.SubRankListModule;
import com.example.rumens.showtime.reader.bookrank.SubRankFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = SubRankListModule.class)
public interface SubRankListComponent {
    void inject(SubRankFragment fragment);
}
