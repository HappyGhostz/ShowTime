package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.BookTypeListModule;
import com.example.rumens.showtime.reader.booklist.BookTypeListFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = BookTypeListModule.class)
public interface BookTypeListComponent {
    void inject(BookTypeListFragment fragment);
}
