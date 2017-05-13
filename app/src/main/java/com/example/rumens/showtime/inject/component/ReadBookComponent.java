package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.ReadBookModule;
import com.example.rumens.showtime.reader.bookread.ReadActivity;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = ReadBookModule.class)
public interface ReadBookComponent {
    void inject(ReadActivity activity);
}
