package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.BookDetialListModule;
import com.example.rumens.showtime.reader.bookdetail.BookDetailActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = BookDetialListModule.class)
public interface BookDetailListComponent {
    void inject(BookDetailActivity activity);
}
