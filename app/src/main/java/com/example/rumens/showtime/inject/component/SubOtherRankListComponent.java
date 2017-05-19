package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.reader.bookrank.SubOtherHomeRankActivity;
import com.example.rumens.showtime.reader.bookrank.SubOtherRankListModule;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/19
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = SubOtherRankListModule.class)
public interface SubOtherRankListComponent {
    void inject(SubOtherHomeRankActivity activity);
}
