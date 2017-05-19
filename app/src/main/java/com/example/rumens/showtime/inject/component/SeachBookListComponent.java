package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.SeachBookListModule;
import com.example.rumens.showtime.reader.booksearch.SearchResultActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = SeachBookListModule.class)
public interface SeachBookListComponent {
    void inject(SearchResultActivity activity);
}
