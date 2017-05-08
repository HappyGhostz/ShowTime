package com.example.rumens.showtime.inject.modules;

import android.text.TextUtils;

import com.example.rumens.showtime.adapter.VideoListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.video.kankan.VideoListFragment;
import com.example.rumens.showtime.video.kankan.VideoListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */
@Module
public class VideoListModule {
    private final VideoListFragment mView;
    private final String mVideoType;

    public VideoListModule(VideoListFragment mView, String mVideoType) {
        this.mView = mView;
        this.mVideoType = mVideoType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new VideoListPresenter(mView, mVideoType);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new VideoListAdapter(mView.getContext(),mVideoType);
    }
}
