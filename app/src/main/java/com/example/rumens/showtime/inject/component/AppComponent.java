package com.example.rumens.showtime.inject.component;

import android.content.Context;


import com.example.rumens.showtime.App;
import com.example.rumens.showtime.inject.modules.AppModule;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.rxBus.RxBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Zhaochen Ping
 * @create 2017/4/19
 * @description
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
    void inject(App app);
    RxBus getRxBus();
    DaoSession getDaoSession();
}
