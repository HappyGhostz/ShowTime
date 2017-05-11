package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.BookClassifyListModule;
import com.example.rumens.showtime.reader.bookclassify.BookClassifyListFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = BookClassifyListModule.class)
public interface BookClassifyListComponent {
    void inject(BookClassifyListFragment fragment);
}
