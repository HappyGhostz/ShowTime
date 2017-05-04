package com.example.rumens.showtime.video.videoplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.example.rumens.showtime.widget.LoadingView;

import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.ScreenResolution;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.ui.widget.DanmakuView;

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
    @BindView(R.id.vm_videoview)
    VideoView mVmVideoview;
    @BindView(R.id.danmakuView)
    DanmakuView mDanmakuView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_live_nickname)
    TextView mTvLiveNickname;
    @BindView(R.id.iv_live_setting)
    ImageView mIvLiveSetting;
    @BindView(R.id.iv_live_gift)
    ImageView mIvLiveGift;
    @BindView(R.id.iv_live_share)
    ImageView mIvLiveShare;
    @BindView(R.id.iv_live_follow)
    ImageView mIvLiveFollow;
    @BindView(R.id.control_top)
    RelativeLayout mControlTop;
    @BindView(R.id.iv_live_play)
    ImageView mIvLivePlay;
    @BindView(R.id.iv_live_refresh)
    ImageView mIvLiveRefresh;
    @BindView(R.id.tv_live_list)
    TextView mTvLiveList;
    @BindView(R.id.im_danmu_control)
    ImageView mImDanmuControl;
    @BindView(R.id.txt_btn_hot_word)
    ImageView txtBtnHotWord;
    @BindView(R.id.ediit_comment)
    EditText ediitComment;
    @BindView(R.id.img_btn_send)
    ImageView imgBtnSend;
    @BindView(R.id.rl_danmu_send)
    RelativeLayout rlDanmuSend;
    @BindView(R.id.control_bottom)
    RelativeLayout controlBottom;
    @BindView(R.id.im_logo)
    ImageView imLogo;
    @BindView(R.id.lv_playloading)
    LoadingView lvPlayloading;
    @BindView(R.id.tv_loading_buffer)
    TextView tvLoadingBuffer;
    @BindView(R.id.fl_loading)
    FrameLayout mFlLoading;
    @BindView(R.id.iv_control_img)
    ImageView mIvControlImg;
    @BindView(R.id.tv_control_name)
    TextView mTvControlName;
    @BindView(R.id.tv_control)
    TextView mTvControl;
    @BindView(R.id.control_center)
    RelativeLayout mControlCenter;
    private int mScreenWidth = 0;//屏幕宽度
    //    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
//    @BindView(R.id.video_player)
//    IjkPlayerView mVideoPlayer;
//    @BindView(R.id.et_content)
//    EditText mEtContent;
//    @BindView(R.id.tag_send)
//    TagView mTagSend;
//    @BindView(R.id.ll_edit_layout)
//    LinearLayout mLlEditLayout;
//    @BindView(R.id.iv_video_share)
//    ImageView mIvVideoShare;
//    @BindView(R.id.iv_video_collect)
//    ShineButton mIvVideoCollect;
//    @BindView(R.id.iv_video_download)
//    ImageView mIvVideoDownload;
//    @BindView(R.id.ll_operate)
//    LinearLayout mLlOperate;
////    @BindView(R.id.magic_indicator)
////    MagicIndicator mMagicIndicator;
//    @BindView(R.id.layout_container)
//    FrameLayout mLayoutContainer;
//    @BindView(R.id.layout_bottom)
//    LinearLayout mLayoutBottom;
    private LiveListItemBean mVideoLiveData;
    private String mLiveDetailUrl;
    private LiveDetailBean mDetailLiveData;
    private DouyuLiveListItemBean.DataBean mDouyuData;
    private String mDouyuType;
    private OldLiveVideoInfo mLiveDouyuVideo;
    private AudioManager mAudioManager;
    private int mScreenWidth = 0;//屏幕宽度
    private boolean mIsFullScreen = true;//是否为全屏
    private int mShowVolume;//声音
    private int mShowLightness;//亮度
    private int mMaxVolume;//最大声音
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener;

    @Override
    public void loadLiveData(LiveDetailBean data) {
        mDetailLiveData = data;
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
        mVmVideoview.setKeepScreenOn(true);
//        svProgressHUD = new SVProgressHUD(this);
        //获取屏幕宽度
        Pair<Integer, Integer> screenPair = ScreenResolution.getResolution(this);
        mScreenWidth = screenPair.first;
        initVolumeWithLight();
        addTouchListener();
        vmVideoview.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
//        if (mVideoLiveData != null) {
//            if (TextUtils.isEmpty(mDouyuType)) {
//                initToolBar(mToolbar, true, mVideoLiveData.getLive_title());
//                mLlOperate.setVisibility(View.GONE);
//                mLayoutBottom.setVisibility(View.VISIBLE);
//            } else {
//                initToolBar(mToolbar, true, mLiveDouyuVideo.getData().getRoom_name());
//                mLlOperate.setVisibility(View.GONE);
//                mLayoutBottom.setVisibility(View.VISIBLE);
//
//            }

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
    /**
     * 初始化声音和亮度
     */
    private void initVolumeWithLight() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mShowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / mMaxVolume;
        mShowLightness = getScreenBrightness();
    }
    /**
     * 获得当前屏幕亮度值 0--255
     */
    private int getScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }
    /**
     * 添加手势操作
     */
    private void addTouchListener() {
        mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            //滑动操作
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                if (!mIsFullScreen)//非全屏不进行手势操作
                    return false;

                float x1 = e1.getX();

                float absDistanceX = Math.abs(distanceX);// distanceX < 0 从左到右
                float absDistanceY = Math.abs(distanceY);// distanceY < 0 从上到下

                // Y方向的距离比X方向的大，即 上下 滑动
                if (absDistanceX < absDistanceY) {
                    if (distanceY > 0) {//向上滑动
                        if (x1 >= mScreenWidth * 0.65) {//右边调节声音
                            changeVolume(ADD_FLAG);
                        } else {//调节亮度
                            changeLightness(ADD_FLAG);
                        }
                    } else {//向下滑动
                        if (x1 >= mScreenWidth * 0.65) {
                            changeVolume(SUB_FLAG);
                        } else {
                            changeLightness(SUB_FLAG);
                        }
                    }
                } else {
                    // X方向的距离比Y方向的大，即 左右 滑动

                }
                return false;
            }
            //双击事件，有的视频播放器支持双击播放暂停，可从这实现
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }

            //单击事件
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (controlBottom.getVisibility() == View.VISIBLE) {
                    mHandler.removeMessages(HIDE_CONTROL_BAR);
                    hideControlBar();
                } else {
                    showControlBar();
                    mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                }

                return true;
            }
        };
        mGestureDetector = new GestureDetector(this, mSimpleOnGestureListener);
    }
    /**
     * 改变声音
     */
    private void changeVolume(int flag) {
        mShowVolume += flag;
        if (mShowVolume > 100) {
            mShowVolume = 100;
        } else if (mShowVolume < 0) {
            mShowVolume = 0;
        }
        mTvControlName.setText("音量");
        mIvControlImg.setImageResource(R.mipmap.img_volume);
        mTvControl.setText(mShowVolume + "%");
        int tagVolume = mShowVolume * mMaxVolume / 100;
        //tagVolume:音量绝对值
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, tagVolume, 0);
        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME);
    }

    /**
     * 改变亮度
     */
    private void changeLightness(int flag) {
        mShowLightness += flag;
        if (mShowLightness > 255) {
            mShowLightness = 255;
        } else if (mShowLightness <= 0) {
            mShowLightness = 0;
        }
        tvControlName.setText("亮度");
        ivControlImg.setImageResource(R.drawable.img_light);
        tvControl.setText(mShowLightness * 100 / 255 + "%");
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = mShowLightness / 255f;
        getWindow().setAttributes(lp);

        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME);
    }


    @Override
    protected int getContenView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        Vitamio.isInitialized(this);
//        return R.layout.activity_video_player;
        return R.layout.activity_video_douyu_player;
    }

    @Override
    protected void initInjector() {
        mDouyuType = getIntent().getStringExtra(DOU_YU);
        mVideoLiveData = getIntent().getParcelableExtra(VIDEO_TYPE_LIVE);
        mDouyuData = getIntent().getParcelableExtra(GAME_TYPE_DOUYU);
        if (TextUtils.equals(mDouyuType, "douyu")) {
            DaggerVideoLiveDouyuPlayerComponent.builder()
                    .appComponent(getAppComponent())
                    .videoLiveDouyuPlayerModule(new VideoLiveDouyuPlayerModule(this, mDouyuData, mDouyuType))
                    .build()
                    .inject(this);
        } else if (mVideoLiveData != null && TextUtils.isEmpty(mDouyuType)) {
            DaggerVideoLivePlayerComponent.builder()
                    .appComponent(getAppComponent())
                    .videoLivePlayerModule(new VideoLivePlayerModule(this, mVideoLiveData))
                    .build()
                    .inject(this);
        }

    }

    public static void lunchLive(Context mContext, LiveListItemBean bean) {
        Intent intent = new Intent(mContext, VideoPlayActivity.class);
        intent.putExtra(VIDEO_TYPE_LIVE, bean);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }

    public static void lunchLiveDouyu(Context mContext, DouyuLiveListItemBean.DataBean bean, String mPlatformType) {
        Intent intent = new Intent(mContext, VideoPlayActivity.class);
        intent.putExtra(GAME_TYPE_DOUYU, bean);
        intent.putExtra(DOU_YU, mPlatformType);
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
