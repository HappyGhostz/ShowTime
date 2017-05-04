package com.example.rumens.showtime.inject.component;

import android.app.Activity;

import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.inject.modules.ActivityModule;

import dagger.Component;

/**
 * Created by long on 2016/8/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
}
