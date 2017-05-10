package com.example.rumens.showtime.video.videonetplay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.VideoLocalListItemBean;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class VideoNetPlayActivity extends BaseActivity<IVideoPresenter> implements View.OnClickListener, MediaPlayer.OnErrorListener {
    private static final String VIDEO_TYPE_LOCAL = "videotypelocal";
    private static final int CURRENT_TIME = 0;
    private static final int CURRENT_PLAY_TIME = 1;
    private static final int HIDE_PANEL = 2;
    private static final String VIDEO_TYPE_LOCAL_POSITION = "videotypelocalposition";
    private static final String VIDEO_TYPE = "videotype";
    @BindView(R.id.videoView)
    VideoView mVideoView;
    @BindView(R.id.playTime)
    TextView mPlayTime;
    @BindView(R.id.sbPlay)
    SeekBar mSbPlay;
    @BindView(R.id.playDuration)
    TextView mPlayDuration;
    @BindView(R.id.ib_back)
    ImageButton mBack;
    @BindView(R.id.ib_pre)
    ImageButton mPre;
    @BindView(R.id.ib_playpause)
    ImageButton mPlayPause;
    @BindView(R.id.ib_next)
    ImageButton mNext;
    @BindView(R.id.ib_screensize)
    ImageButton mScreensize;
    @BindView(R.id.ll_bottompanel)
    LinearLayout mBottomPanel;
    @BindView(R.id.tv_videotitle)
    TextView mVideotitle;
    @BindView(R.id.tv_currenttime)
    TextView mCurrenttime;
    @BindView(R.id.iv_battery)
    ImageView mBattery;
    @BindView(R.id.ib_volume)
    ImageButton mIbVolume;
    @BindView(R.id.sb_volume)
    SeekBar mSbVolume;
    @BindView(R.id.rl_toppanel)
    RelativeLayout mTopPanel;
    @BindView(R.id.alpha_cover)
    View mAlpha;
    @BindView(R.id.ll_loading_cover)
    LinearLayout mLoading;
    @BindView(R.id.pb_buffer)
    ProgressBar mPbBuffer;
    @BindView(R.id.activity_video_play)
    RelativeLayout mActivityVideoPlay;
    @BindView(R.id.tv_show_buff)
    TextView mTvShowBuff;
    private ArrayList<VideoLocalListItemBean> mediaItemInfos;
    private int position;
    private String inputUrl;
    private int mTopPanelHeight;
    private int mBottomPanelHeight;
    private int screenWidth;
    private int screenHight;
    private AudioManager audioManager;
    private int maxVolume;
    private int currentVolume;
    private MyBatteryReceiver receiver;
    private boolean isShowPanel = false;
    private boolean mIbVolume_enable = true;
    private boolean isFullScreen = false;
    private int lastVolume;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CURRENT_TIME:
                    upDataCurrentTime();
                    break;
                case CURRENT_PLAY_TIME:
                    upDataPlayTime();
                    break;
                case HIDE_PANEL:
                    hidePanel();
                    break;
            }
        }
    };
    private int videoWidth;
    private int videoHeight;
    private int downY;
    private int downX;
    private GestureDetector gestureDetector;
    private String mVideoType;

    //隐藏面板
    private void hidePanel() {
        showAndHidePanel(-mTopPanelHeight, mBottomPanelHeight);
        isShowPanel = false;
    }

    //显示面板
    private void showPanel() {
        showAndHidePanel(0, 0);
        handler.sendEmptyMessageDelayed(HIDE_PANEL, 3000);
        isShowPanel = true;
    }


    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {
//        if(!LibsChecker.checkVitamioLibs(this)){
//            finish();
//        }
        Intent intent = getIntent();
        //其他应用传过来的网址路径或者文件
        Uri uri = intent.getData();
        if (uri != null) {
            mVideoView.setVideoURI(uri);
            mVideotitle.setText(uri.toString());

        } else {
            //获取网络视频路径
//            inputUrl = intent.getStringExtra("inputUrl");
//        MediaItemInfo item = (MediaItemInfo) intent.getSerializableExtra("item");
            //获取本地视频
            mediaItemInfos = (ArrayList<VideoLocalListItemBean>) intent.getSerializableExtra(VIDEO_TYPE_LOCAL);
            if (mediaItemInfos != null) {
                position = (int) intent.getSerializableExtra(VIDEO_TYPE_LOCAL_POSITION);
            }
//        System.out.println("path"+item.path+"title"+item.title+"size"+item.size+"duration"+item.duration);
            startPalyVideo();
        }

        upDataCurrentTime();
        registerBatteryReceiver();
        getSystemAudio();
        //获得屏幕的宽度
        getScreenWidthAndHeight();
        //测量上下面板的高度
        mTopPanel.measure(0, 0);
        mBottomPanel.measure(0, 0);
        mTopPanelHeight = mTopPanel.getMeasuredHeight();
        mBottomPanelHeight = mBottomPanel.getMeasuredHeight();
        //初始化隐藏面板
        showAndHidePanel(-mTopPanelHeight, mBottomPanelHeight);
        initListener();
    }

    private void initListener() {
        mPlayPause.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mPre.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mScreensize.setOnClickListener(this);

        mIbVolume.setOnClickListener(this);
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
        mVideoView.setOnErrorListener(this);

        mVideoView.setOnPreparedListener((MediaPlayer.OnPreparedListener) new MyParedListener());

        MySeekBarChange seekBarChangeListener = new MySeekBarChange();
        mSbVolume.setOnSeekBarChangeListener(seekBarChangeListener);
        mSbPlay.setOnSeekBarChangeListener(seekBarChangeListener);
        //视频播放完成事件监听
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //当播放完成的时候会执行这个方法
                //把当前播放的时间设置为总时长
                handler.removeMessages(CURRENT_PLAY_TIME);
                mPlayTime.setText(StringUtils.formatTime((int) mp.getDuration()));
                //让seekbar 展示的进度移动到最后
                mSbPlay.setMax(mSbPlay.getMax());
            }
        });
        //屏幕单击与双击事件监听
        //只要点击屏幕就走这个方法
//双击走这个方法
//单击走这个方法
        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            //只要点击屏幕就走这个方法
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            //双击走这个方法
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                screenSize();
                return super.onDoubleTap(e);
            }

            //单击走这个方法
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isShowPanel) {
                    hidePanel();
                    handler.removeMessages(HIDE_PANEL);
                } else {
//                    System.out.println("我走了吗");
                    showPanel();
                }
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    //控制面板的隐藏与显示
    private void showAndHidePanel(int mTopPanelHeight, int mBottomPanelHeight) {
        mTopPanel.setTranslationY(mTopPanelHeight);
        mBottomPanel.setTranslationY(mBottomPanelHeight);
    }

    //获得屏幕的宽度
    private void getScreenWidthAndHeight() {

        screenWidth = getWindowManager().getDefaultDisplay().getWidth();

        screenHight = getWindowManager().getDefaultDisplay().getHeight();
    }

    //开始播放后的一些初始化设置
    private void startPalyVideo() {
        if (mVideoView != null) {
            mVideoView.setBufferSize(1024 * 1024 * 2);
            mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            mVideoView.requestFocus();
            mVideoView.setVideoLayout(VideoView. VIDEO_LAYOUT_SCALE, 0);
        }
        if (mediaItemInfos != null) {

            mVideoView.setVideoPath(mediaItemInfos.get(position).path);
            mVideotitle.setText(mediaItemInfos.get(position).title);
//            mPlayDuration.setText(StringUtils.formatTime(mediaItemInfos.get(position).duration));
//            mSbPlay.setMax(mediaItemInfos.get(position).duration);
        } else if (inputUrl != null) {
            mLoading.setVisibility(View.VISIBLE);
            mVideoView.setVideoPath(inputUrl);
            mVideotitle.setText(inputUrl);
            mPre.setClickable(false);
            mPre.setImageResource(R.drawable.btn_pre_gray);
            mNext.setClickable(false);
            mNext.setImageResource(R.drawable.btn_pre_gray);
        }

    }

    /**
     * 初始化当前音量显示
     */
    private void getSystemAudio() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //获取系统最大音量 传入参数 就是指定音量的类型  AudioManager.STREAM_MUSIC 音乐音量
        //AudioManager.STREAM_NOTIFICATION 通知
        //AudioManager.STREAM_RING  响铃
        //AudioManager.STREAM_SYSTEM 系统音量
        //AudioManager.STREAM_ALARM 闹钟
        //AudioManager.STREAM_VOICE_CALL 通话音量

        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSbVolume.setMax(maxVolume);//设置进度条的最大音量
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSbVolume.setProgress(currentVolume);//设置当前音量
        if (currentVolume > 0) {
            mIbVolume.setImageResource(R.drawable.btn_voice_pressed);
        } else {
            mIbVolume.setImageResource(R.drawable.btn_voice_normal);
        }
    }

    /**
     * 动态注册电量广播者
     */
    private void registerBatteryReceiver() {
        receiver = new MyBatteryReceiver();
        //当电量变化的时候 系统会发一个广播 ACTION_BATTERY_CHANGED
        IntentFilter intertFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, intertFilter);
    }

    //更新时间
    public void upDataCurrentTime() {
        mCurrenttime.setText(StringUtils.getCurrentTime());
        handler.sendEmptyMessageDelayed(CURRENT_TIME, 1000);
    }

    //更新播放的当前时间
    private void upDataPlayTime() {
        int currentPosition = (int) mVideoView.getCurrentPosition();
        mPlayTime.setText(StringUtils.formatTime(currentPosition));
        mSbPlay.setProgress(currentPosition);
        handler.sendEmptyMessageDelayed(CURRENT_PLAY_TIME, 500);
    }


    @Override
    protected int getContenView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        Vitamio.isInitialized(this);
        return R.layout.activity_video_net_play;
    }

    @Override
    protected void initInjector() {
        mVideoType = getIntent().getStringExtra(VIDEO_TYPE);
    }

    public static void lunchLocalVideo(Context mContext, ArrayList<VideoLocalListItemBean> videoLocalListItem, int position, String mVideoType) {
        Intent intent = new Intent(mContext, VideoNetPlayActivity.class);
        intent.putExtra(VIDEO_TYPE_LOCAL, videoLocalListItem);
        intent.putExtra(VIDEO_TYPE_LOCAL_POSITION, position);
        intent.putExtra(VIDEO_TYPE, mVideoType);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //播放开关点击事件
            case R.id.ib_playpause:
                upDataPlayState();
                break;
            case R.id.ib_volume:
                changeAudioState();
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_pre:
                pre();
                break;
            case R.id.ib_next:
                next();
                break;
            case R.id.ib_screensize:
                screenSize();
                break;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
//            svProgressHUD.showErrorWithStatus("主播还在赶来的路上~~");
            Toast.makeText(this, "未知错误~~", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * 接收电量发出的广播，从而设置图片
     */
    private class MyBatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //当前电量的大小 通过intent传递 key是level
            int level = intent.getIntExtra("level", 40);
            if (level > 90) {
                mBattery.setImageResource(R.drawable.ic_battery_100);
            } else if (level > 80) {
                mBattery.setImageResource(R.drawable.ic_battery_80);
            } else if (level > 60) {
                mBattery.setImageResource(R.drawable.ic_battery_60);
            } else if (level > 40) {
                mBattery.setImageResource(R.drawable.ic_battery_40);
            } else if (level > 20) {
                mBattery.setImageResource(R.drawable.ic_battery_20);
            } else if (level > 10) {
                mBattery.setImageResource(R.drawable.ic_battery_10);
            } else {
                mBattery.setImageResource(R.drawable.ic_battery_0);
            }
        }
    }

    //静音开关状态的设置
    private void changeAudioState() {
        if (mIbVolume_enable) {
            if (currentVolume == 0) {
                mSbVolume.setProgress(lastVolume);
            } else {
                mSbVolume.setProgress(0);
            }
        }
    }

    //开始，暂停播放
    private void upDataPlayState() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
            handler.removeMessages(CURRENT_PLAY_TIME);
            mPlayPause.setImageResource(R.drawable.play_selector);
        } else {
            mVideoView.start();
            upDataPlayTime();
            mPlayPause.setImageResource(R.drawable.pause_selector);
        }
    }

    //播放上一首
    private void pre() {
        position--;
        handler.removeMessages(CURRENT_PLAY_TIME);
        startPalyVideo();

    }

    //播放下一首
    private void next() {
        position++;
        handler.removeMessages(CURRENT_PLAY_TIME);
        startPalyVideo();
    }

    //全屏
    private void screenSize() {
        if (isFullScreen) {
            mVideoView.getLayoutParams().width =videoWidth;
//            System.out.println("videoWidth*videoHeight/screenHight"+videoWidth*videoHeight/screenHight);
            mVideoView.getLayoutParams().height = videoHeight;
//            System.out.println("screenHight"+screenHight);
            mScreensize.setImageResource(R.drawable.fullscreen_selector);
        } else {
            mVideoView.getLayoutParams().width = screenWidth;
//            System.out.println("screenWidth"+screenWidth);
            mVideoView.getLayoutParams().height = screenHight;
            mScreensize.setImageResource(R.drawable.defaultscreen_selector);
        }
        mVideoView.requestLayout();
        isFullScreen = !isFullScreen;
    }

    /**
     * VideoView准备的监听事件
     */
    private class MyParedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mediaItemInfos != null) {
                //异步准备准备好之后就会调用这个onPrepared
                mVideoView.start();
                mPlayPause.setImageResource(R.drawable.pause_selector);

                //根据播放的条目修改UI
                if (mediaItemInfos != null && mediaItemInfos.size() > 0) {
                    if (position == 0) {
                        mPre.setImageResource(R.drawable.btn_pre_gray);
                        mPre.setClickable(false);
                    } else {
                        mPre.setImageResource(R.drawable.pre_selector);
                        mPre.setClickable(true);
                    }
                    if (position == mediaItemInfos.size() - 1) {
                        mNext.setImageResource(R.drawable.btn_next_gray);
                        mNext.setClickable(false);
                    } else {
                        mNext.setImageResource(R.drawable.next_selector);
                        mNext.setClickable(true);
                    }
                }
                //开始准备好播放视频之后获取视频的宽和高
            } else if (inputUrl != null) {
                mVideoView.start();
                mPlayPause.setImageResource(R.drawable.pause_selector);
                //缓存视频中显示进度条
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        switch (what) {
                            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                                mPbBuffer.setVisibility(View.VISIBLE);
                                mLoading.setVisibility(View.VISIBLE);
//                                System.out.println("===BUFFERING_START我走了吗，哈哈哈哈哈====");
                                break;
                            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                                System.out.println("----BUFFERING_END我走了吗，哈哈哈哈哈-----");
                                mPbBuffer.setVisibility(View.GONE);
                                mLoading.setVisibility(View.GONE);
                                break;
                        }
                        return false;
                    }
                });
                //显示第二条SeekBar代表缓存了多少视频
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        mSbPlay.setSecondaryProgress((int) (mp.getDuration() * percent / 100));
                    }
                });

            }
            //视频准备好之后开始更新当前播放时间
            upDataPlayTime();
            videoWidth = mp.getVideoWidth();
            videoHeight = mp.getVideoHeight();
            mPlayDuration.setText(StringUtils.formatTime((int) mp.getDuration()));
            mSbPlay.setMax((int) mp.getDuration());
            mp.setPlaybackSpeed(1.0f);
        }
    }

    /**
     * SeekBar进度条改变监听事件
     */
    private class MySeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.sb_volume:
                    lastVolume = currentVolume;//修改音量前先记录当前音量
                    currentVolume = progress;//修改当前音量
                    //第一个参数 调整的音量类型
                    //第二个参数 音量大小
                    //第三个参数  调整时的效果 AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE 没声也不震动
                    //AudioManager.FLAG_PLAY_SOUND 调整的过程中会出生
                    //AudioManager.FLAG_SHOW_UI 调整时会显示系统自带的音量调整界面
                    //AudioManager.FLAG_VIBRATE 调整的时候会震动
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    if (currentVolume == 0) {
                        mIbVolume.setImageResource(R.drawable.btn_voice_normal);
                        if (fromUser) {
                            mIbVolume_enable = false;
                        }
                    } else if (lastVolume == 0 && currentVolume > 0) {
                        mIbVolume.setImageResource(R.drawable.btn_voice_pressed);
                        if (fromUser) {
                            mIbVolume_enable = true;
                        }
                    }
                    break;
                case R.id.sbPlay:
                    if (fromUser) {
                        mVideoView.seekTo(progress);
                    }
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //再拖拽进度条时不隐藏上下面板
            handler.removeMessages(HIDE_PANEL);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //拖拽结束3秒后隐藏上下面板
            handler.sendEmptyMessageDelayed(HIDE_PANEL, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        if (mVideoView != null) {
            //        释放资源
            mVideoView.stopPlayback();
        }
        super.onDestroy();
        //移除所有消息
        handler.removeCallbacksAndMessages(null);
        //移除广播
        unregisterReceiver(receiver);
    }

    /**
     * 触摸事件，滑动屏幕改变音量的大小和亮度大小
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //让单击双击事件生效
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //在屏幕左边滑动加减音量，否则加减亮度
                int endY = (int) event.getY();
                int distanceY = endY - downY;
                if (downX < screenWidth / 2) {
                    int temp = currentVolume + distanceY / 150;
                    if (temp > maxVolume) {
                        currentVolume = maxVolume;
                    } else if (temp < 0) {
                        currentVolume = 0;
                    } else {
                        currentVolume = temp;
                    }
                    mSbVolume.setProgress(currentVolume);
                } else {
                    float alpha = mAlpha.getAlpha();
                    float temp = alpha + distanceY / 250 * 0.1f;
                    if (temp > 0.8f) {
                        alpha = 0.8f;
                    } else if (temp < 0f) {
                        alpha = 0f;
                    } else {
                        alpha = temp;
                    }
                    mAlpha.setAlpha(alpha);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        mPresenter.getPresenterPcLiveVideoInfo(Room_id);
//        mPresenter.getData();
        if (mVideoView != null) {
            mVideoView.start();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }


}
