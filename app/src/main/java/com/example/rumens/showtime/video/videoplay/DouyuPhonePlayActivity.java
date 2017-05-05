package com.example.rumens.showtime.video.videoplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.inject.component.DaggerVideoDouyuPhonePlayerComponent;
import com.example.rumens.showtime.inject.modules.VideoDouyuPhonePlayerModule;
import com.example.rumens.showtime.local.VideoInfo;
import com.example.rumens.showtime.widget.LoadingView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.ScreenResolution;
import io.vov.vitamio.widget.VideoView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/5
 * @description
 */

public class DouyuPhonePlayActivity extends BaseActivity<IVideoPresenter> implements IVideoView, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {
    private static final String DOU_YU_PHONE_PLAY = "douyuphoneplay";
    private static final String DOU_YU_PHONE_PLAY_TYPE = "douyuphoneplaytype";
    @BindView(R.id.vm_videoview)
    VideoView vmVideoview;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_live_nickname)
    TextView tvLiveNickname;
    @BindView(R.id.iv_live_setting)
    ImageView ivLiveSetting;
    @BindView(R.id.iv_live_gift)
    ImageView ivLiveGift;
    @BindView(R.id.iv_live_share)
    ImageView ivLiveShare;
    @BindView(R.id.iv_live_follow)
    ImageView ivLiveFollow;
    @BindView(R.id.control_top)
    RelativeLayout controlTop;
    @BindView(R.id.iv_live_play)
    ImageView ivLivePlay;
    @BindView(R.id.iv_live_refresh)
    ImageView ivLiveRefresh;
    @BindView(R.id.tv_live_list)
    TextView tvLiveList;
    @BindView(R.id.im_danmu_control)
    ImageView imDanmuControl;
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
    FrameLayout flLoading;
    @BindView(R.id.iv_control_img)
    ImageView ivControlImg;
    @BindView(R.id.tv_control_name)
    TextView tvControlName;
    @BindView(R.id.tv_control)
    TextView tvControl;
    @BindView(R.id.control_center)
    RelativeLayout controlCenter;
    private DouyuLiveListItemBean.DataBean mDouyuData;
    private int  mScreenWidth = 0;
    private AudioManager mAudioManager;
    private int mMaxVolume;
    private int mShowVolume;
    private int mShowLightness;
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener;
    private boolean mIsFullScreen = true;//是否为全屏
    /**
     * 声音
     */
    public final static int ADD_FLAG = 1;
    /**
     * 亮度
     */
    public final static int SUB_FLAG = -1;
    public static final int HIDE_CONTROL_BAR = 0x02;//隐藏控制条
    public static final int HIDE_TIME = 5000;//隐藏控制条时间
    public static final int SHOW_CENTER_CONTROL = 0x03;//显示中间控制
    public static final int SHOW_CONTROL_TIME = 1000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**
                 *  隐藏top ,bottom
                 */
                case HIDE_CONTROL_BAR:
                    hideControlBar();
                    break;
                /**
                 *  隐藏center控件
                 */
                case SHOW_CENTER_CONTROL:
                    if (controlCenter != null) {
                        controlCenter.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
    private GestureDetector mGestureDetector;

    @Override
    public void loadLiveData(LiveDetailBean data) {

    }
    /**
     * 触摸事件进行监听
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector != null)
            mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }
    @Override
    public void loadLiveDouyuData(final OldLiveVideoInfo data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(data!=null){
                    String url = data.getData().getLive_url();
                    Uri uri = Uri.parse(url);
                    if (vmVideoview != null) {
                        vmVideoview.setVideoURI(uri);
                        vmVideoview.setBufferSize(1024 * 1024 * 20);
                        /**
                         * 设置视频质量。参数quality参见MediaPlayer的常量：
                         * VIDEOQUALITY_LOW（流畅）、VIDEOQUALITY_MEDIUM（普通）、VIDEOQUALITY_HIGH（高质）。
                         */
                        vmVideoview.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
                        vmVideoview.requestFocus();
                        vmVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                // optional need Vitamio 4.0
                                mediaPlayer.setPlaybackSpeed(1.0f);
                                flLoading.setVisibility(View.GONE);
//                    vmVideoview.setBackgroundResource(0);
                                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                            }
                        });
                    }
                }
            }
        });
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
        vmVideoview.setKeepScreenOn(true);
        Pair<Integer,Integer> sceenPair = ScreenResolution.getResolution(this);
        mScreenWidth = sceenPair.first;
        //   初始化声音和亮度
        initVolumeWithLight();
        vmVideoview.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
        //        添加手势监听
        addTouchListener();
//        视频播放监听
        vmVideoview.setOnInfoListener(this);
        vmVideoview.setOnBufferingUpdateListener(this);
        vmVideoview.setOnErrorListener(this);
    }

    private void addTouchListener() {
        mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if(!mIsFullScreen){
                    return false;
                }
                float e1X = e1.getX();
                float distancex = Math.abs(distanceX);
                float distancey = Math.abs(distanceY);
                if(distancex<distancey){
                    if(distanceY > 0){
                        if(e1X>mScreenWidth*0.65){
                            changeVolume(ADD_FLAG);
                        }else {
                            changeLightness(ADD_FLAG);
                        }
                    }else{
                        if(e1X>mScreenWidth*0.65){
                            changeVolume(SUB_FLAG);
                        }else {
                            changeLightness(SUB_FLAG);
                        }
                    }
                }
                return false;
            }

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

    private void showControlBar() {
        if (controlBottom != null && controlTop != null) {
            controlBottom.setVisibility(View.VISIBLE);
            controlTop.setVisibility(View.VISIBLE);
        }
    }

    private void hideControlBar() {
        if (controlBottom != null && controlTop != null) {
            controlBottom.setVisibility(View.GONE);
            controlTop.setVisibility(View.GONE);
        }
    }

    private void changeVolume(int flag) {
        mShowVolume+=flag;
        if(mShowVolume>=100){
            mShowVolume=100;
        }else if(mShowVolume<=0){
            mShowVolume=0;
        }
        tvControlName.setText("音量");
        ivControlImg.setImageResource(R.mipmap.img_volume);
        tvControl.setText(mShowVolume + "%");
        int tagVolume = mShowVolume * mMaxVolume / 100;
        //tagVolume:音量绝对值
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,tagVolume,0);
        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        controlCenter.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME);
    }

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

    private void initVolumeWithLight() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mShowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / mMaxVolume;
        mShowLightness = getScreenBrightness();
    }

    private int getScreenBrightness() {
        int screenBrightness = 255;
        try{
            screenBrightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return screenBrightness;
    }

    @Override
    protected int getContenView() {
        Vitamio.isInitialized(this);
        return R.layout.activity_doutu_phone_video;
    }

    @Override
    protected void initInjector() {
        mDouyuData = getIntent().getParcelableExtra(DOU_YU_PHONE_PLAY);
        String mDouyuType = getIntent().getStringExtra(DOU_YU_PHONE_PLAY_TYPE);
        DaggerVideoDouyuPhonePlayerComponent.builder()
                .appComponent(getAppComponent())
                .videoDouyuPhonePlayerModule(new VideoDouyuPhonePlayerModule(this, mDouyuData, mDouyuType))
                .build()
                .inject(this);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        flLoading.setVisibility(View.VISIBLE);
        if (vmVideoview.isPlaying())
            vmVideoview.pause();
        tvLoadingBuffer.setText("直播已缓冲" + percent + "%...");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
//            svProgressHUD.showErrorWithStatus("主播还在赶来的路上~~");
            Toast.makeText(this, "主播还在赶来的路上~~", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (vmVideoview.isPlaying()) {
                    vmVideoview.pause();
                }

                mHandler.removeMessages(HIDE_CONTROL_BAR);
                break;
//            完成缓冲
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                flLoading.setVisibility(View.GONE);
                if (!vmVideoview.isPlaying())
                    vmVideoview.start();
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:

                break;
        }
        return true;
    }

    public static void lunchLiveDouyu(Context mContext, DouyuLiveListItemBean.DataBean item, String mPlatformType) {
        Intent intent = new Intent(mContext, DouyuPhonePlayActivity.class);
        intent.putExtra(DOU_YU_PHONE_PLAY, item);
        intent.putExtra(DOU_YU_PHONE_PLAY_TYPE, mPlatformType);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getData();
        if (vmVideoview != null) {
            vmVideoview.start();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (vmVideoview != null) {
            vmVideoview.pause();
        }

    }

    @Override
    protected void onDestroy() {
        if (vmVideoview != null) {
            //        释放资源
            vmVideoview.stopPlayback();
        }
        super.onDestroy();
    }
}
