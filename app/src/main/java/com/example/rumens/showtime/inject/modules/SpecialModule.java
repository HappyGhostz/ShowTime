package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.SpecialAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.news.special.SpecialActivity;
import com.example.rumens.showtime.news.special.SpecialPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */
@Module
public class SpecialModule {
    private final SpecialActivity mView;
    private final String mSpecialId;

    public SpecialModule(SpecialActivity mView, String mSpecialId) {
        this.mView = mView;
        this.mSpecialId = mSpecialId;
    }
    @PerActivity
    @Provides
    public IBasePresenter provideSpecialPresent() {
        return new SpecialPresenter(mView, mSpecialId);
    }

    @PerActivity
    @Provides
    public BaseQuickAdapter provideSpecialAdapter() {
        return new SpecialAdapter(mView);
    }
}
