package com.example.rumens.showtime.video.videoplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dl7.tag.TagView;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.inject.component.DaggerVideoLiveDouyuPlayerComponent;
import com.example.rumens.showtime.inject.component.DaggerVideoLivePlayerComponent;
import com.example.rumens.showtime.inject.modules.VideoLiveDouyuPlayerModule;
import com.example.rumens.showtime.inject.modules.VideoLivePlayerModule;
import com.example.rumens.showtime.local.VideoInfo;
import com.example.rumens.showtime.video.videoplay.videoplayhelper.media.IjkPlayerView;
import com.sackcentury.shinebuttonlib.ShineButton;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.Vitamio;

/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */

public class VideoPlayActivity extends BaseActivity<IVideoPresenter> implements IVideoView {


    public static final String LIVE_TYPE = "live_type"; //直播平台
    public static final String LIVE_ID = "live_id";     //直播房间ID
    public static final String GAME_TYPE_DOUYU = "gametypedouyu"; //直播游戏类型
    public static final String VIDEO_TYPE_LIVE = "videotypelive";//直播
    private static final String DOU_YU = "douyu";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.video_player)
    IjkPlayerView mVideoPlayer;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tag_send)
    TagView mTagSend;
    @BindView(R.id.ll_edit_layout)
    LinearLayout mLlEditLayout;
    @BindView(R.id.iv_video_share)
    ImageView mIvVideoShare;
    @BindView(R.id.iv_video_collect)
    ShineButton mIvVideoCollect;
    @BindView(R.id.iv_video_download)
    ImageView mIvVideoDownload;
    @BindView(R.id.ll_operate)
    LinearLayout mLlOperate;
//    @BindView(R.id.magic_indicator)
//    MagicIndicator mMagicIndicator;
    @BindView(R.id.layout_container)
    FrameLayout mLayoutContainer;
    @BindView(R.id.layout_bottom)
    LinearLayout mLayoutBottom;
    private LiveListItemBean mVideoLiveData;
    private String mLiveDetailUrl;
    private LiveDetailBean mDetailLiveData;
    private DouyuLiveListItemBean.DataBean mDouyuData;
    private String mDouyuType;
    private OldLiveVideoInfo mLiveDouyuVideo;

    @Override
    public void loadLiveData(LiveDetailBean data) {
            mDetailLiveData=data;
            List<LiveDetailBean.StreamListBean> streamList = data.getStream_list();
            LiveDetailBean.StreamListBean streamListBean = streamList.get(streamList.size() - 1);
            mLiveDetailUrl = streamListBean.getUrl();
            mVideoPlayer.init()
                .setTitle(mVideoLiveData.getLive_title())
                .setVideoSource(null, mLiveDetailUrl, null, null, null);
            Glide.with(this).load(mDetailLiveData.getLive_img()).fitCenter().into(mVideoPlayer.mPlayerThumb);

    }

    @Override
    public void loadLiveDouyuData(OldLiveVideoInfo data) {
           mLiveDouyuVideo = data;
        String url = mLiveDouyuVideo.getData().getLive_url();
        Uri uri = Uri.parse(url);
        mVideoPlayer.init()
                .setTitle(mLiveDouyuVideo.getData().getRoom_name())
                .setVideoSource(null, url, null, null, null);
        Glide.with(this).load(mDouyuData.getRoom_src()).fitCenter().into(mVideoPlayer.mPlayerThumb);
    }

    @Override
    public void loadVideoData(VideoInfo data) {

    }

    @Override
    public void loadDanmakuData(InputStream inputStream) {

    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        if(mVideoLiveData!=null){
            if(TextUtils.isEmpty(mDouyuType)){
                initToolBar(mToolbar, true, mVideoLiveData.getLive_title());
                mLlOperate.setVisibility(View.GONE);
                mLayoutBottom.setVisibility(View.VISIBLE);
            }else{
                initToolBar(mToolbar, true, mLiveDouyuVideo.getData().getRoom_name());
                mLlOperate.setVisibility(View.GONE);
                mLayoutBottom.setVisibility(View.VISIBLE);

            }

//            mIvVideoCollect.init(this);
//            mIvVideoCollect.setShapeResource(R.mipmap.ic_video_collect);
//            mEtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        mVideoPlayer.editVideo();
//                    }
//                    mLlOperate.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
//                }
//            });
//            mTagSend.setTagClickListener(new TagView.OnTagClickListener() {
//                @Override
//                public void onTagClick(int i, String s, int i1) {
//                    mVideoPlayer.sendDanmaku(mEtContent.getText().toString(), false);
//                    mEtContent.setText("");
////                _closeSoftInput();
//                }
//            });
        }
    }

    @Override
    protected int getContenView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        Vitamio.isInitialized(this);
        return R.layout.activity_video_player;
//        return R.layout.activity_video_douyu_player;
    }

    @Override
    protected void initInjector() {
        mDouyuType = getIntent().getStringExtra(DOU_YU);
        mVideoLiveData = getIntent().getParcelableExtra(VIDEO_TYPE_LIVE);
        mDouyuData = getIntent().getParcelableExtra(GAME_TYPE_DOUYU);
            if(TextUtils.equals(mDouyuType,"douyu")){
                DaggerVideoLiveDouyuPlayerComponent.builder()
                        .appComponent(getAppComponent())
                        .videoLiveDouyuPlayerModule(new VideoLiveDouyuPlayerModule(this, mDouyuData,mDouyuType))
                        .build()
                        .inject(this);
            }else if(mVideoLiveData!=null&&TextUtils.isEmpty(mDouyuType)){
                DaggerVideoLivePlayerComponent.builder()
                        .appComponent(getAppComponent())
                        .videoLivePlayerModule(new VideoLivePlayerModule(this, mVideoLiveData))
                        .build()
                        .inject(this);
            }

    }

    public static void lunchLive(Context mContext, LiveListItemBean bean) {
        Intent intent = new Intent(mContext,VideoPlayActivity.class);
        intent.putExtra(VIDEO_TYPE_LIVE,bean);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }
    public static void lunchLiveDouyu(Context mContext, DouyuLiveListItemBean.DataBean bean, String mPlatformType) {
        Intent intent = new Intent(mContext,VideoPlayActivity.class);
        intent.putExtra(GAME_TYPE_DOUYU,bean);
        intent.putExtra(DOU_YU,mPlatformType);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mVideoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayer.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoPlayer.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mVideoPlayer.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mVideoPlayer.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mVideoPlayer.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
