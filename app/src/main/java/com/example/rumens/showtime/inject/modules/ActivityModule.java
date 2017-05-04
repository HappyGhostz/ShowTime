package com.example.rumens.showtime.inject.modules;

import android.app.Activity;


import com.example.rumens.showtime.inject.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by long on 2016/8/19.
 * Activity Module
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @PerActivity
    @Provides
    Activity getActivity() {
        return this.mActivity;
    }
}
