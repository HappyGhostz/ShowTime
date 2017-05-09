package com.example.rumens.showtime.video.videonetplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.VideoLocalListItemBean;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IVideoPresenter;

public class VideoNetPlayActivity extends BaseActivity<IVideoPresenter> {
    private static final String VIDEO_TYPE_LOCAL = "videotypelocal";
    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContenView() {
        return R.layout.activity_video_net_play;
    }

    @Override
    protected void initInjector() {

    }

    public static void lunchLocalVideo(Context mContext, VideoLocalListItemBean videoLocalListItem) {
        Intent intent = new Intent(mContext,VideoNetPlayActivity.class);
        intent.putExtra(VIDEO_TYPE_LOCAL,videoLocalListItem);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }
}
