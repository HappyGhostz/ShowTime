package com.example.rumens.showtime.inject.modules;

import android.text.TextUtils;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.video.videoplay.VideoDouyuPlayerPresenter;
import com.example.rumens.showtime.video.videoplay.VideoPlayActivity;
import com.example.rumens.showtime.video.videoplay.VideoPlayerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/4
 * @description
 */
@Module
public class VideoLiveDouyuPlayerModule {
    private final VideoPlayActivity mView;
    private final DouyuLiveListItemBean.DataBean mDouyuData;
    private final String mDouyuType;

    public VideoLiveDouyuPlayerModule(VideoPlayActivity mView, DouyuLiveListItemBean.DataBean mDouyuData, String mDouyuType) {

        this.mView = mView;
        this.mDouyuData = mDouyuData;
        this.mDouyuType = mDouyuType;
    }
    @PerActivity
    @Provides
    public IVideoPresenter providePresenter() {
            return new VideoDouyuPlayerPresenter(mView, mDouyuData,mDouyuType);
    }
}
