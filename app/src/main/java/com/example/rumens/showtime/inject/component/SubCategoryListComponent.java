package com.example.rumens.showtime.inject.component;

import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.inject.modules.SubCategoryListModule;
import com.example.rumens.showtime.reader.bookclassify.SubCategoryFragment;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */
@PerFragment
@Component(dependencies = AppComponent.class,modules = SubCategoryListModule.class)
public interface SubCategoryListComponent {
    void inject(SubCategoryFragment fragment);
}
