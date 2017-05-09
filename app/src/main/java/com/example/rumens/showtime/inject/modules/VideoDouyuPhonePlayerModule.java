package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.video.videoliveplay.DouyuPhonePlayActivity;
import com.example.rumens.showtime.video.videoliveplay.VideoDouyuPlayerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/5
 * @description
 */
@Module
public class VideoDouyuPhonePlayerModule {
    private final DouyuPhonePlayActivity mView;
    private final DouyuLiveListItemBean.DataBean mDouyuData;
    private final String mDouyuType;

    public VideoDouyuPhonePlayerModule(DouyuPhonePlayActivity mView, DouyuLiveListItemBean.DataBean mDouyuData, String mDouyuType) {

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
