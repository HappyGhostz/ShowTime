package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.BookRankListModule;
import com.example.rumens.showtime.reader.bookrank.BookRankListFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = BookRankListModule.class)
public interface BookRankListComponent {
    void inject(BookRankListFragment fragment);
}
