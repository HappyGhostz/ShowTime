package com.example.rumens.showtime.video.kankan;

import android.text.TextUtils;

import com.example.rumens.showtime.base.IBasePresenter;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */

public class VideoListPresenter implements IBasePresenter {
    private final IVideoListView mView;
    private final String mVideoType;

    public VideoListPresenter(IVideoListView mView, String mVideoType) {
        this.mView = mView;
        this.mVideoType = mVideoType;
    }

    @Override
    public void getData() {
        if(TextUtils.equals(mVideoType,"本地")){

        }
    }

    @Override
    public void getMoreData() {

    }
}
