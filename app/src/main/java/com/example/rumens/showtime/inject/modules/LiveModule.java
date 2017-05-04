package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.LiveAdapter;
import com.example.rumens.showtime.adapter.NewsMultiListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.news.onenews.NewsListPresenter;
import com.example.rumens.showtime.video.live.LiveFragment;
import com.example.rumens.showtime.video.live.LivePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */
@Module
public class LiveModule {
    private final LiveFragment mView;
    private final String mGameType;
    private String mPlatformType;

    public LiveModule(LiveFragment mView, String mGameType, String mPlatformType) {
        this.mView = mView;
        this.mGameType = mGameType;
        this.mPlatformType = mPlatformType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new LivePresenter(mView, mGameType,mPlatformType);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new LiveAdapter(mView.getContext(),mPlatformType);
    }
}
