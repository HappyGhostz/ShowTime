package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.BookHelpDetialModule;
import com.example.rumens.showtime.reader.bookhelp.BookHelpDetailActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = BookHelpDetialModule.class)
public interface BookHelpDetialComponent {
    void inject(BookHelpDetailActivity activity);
}
