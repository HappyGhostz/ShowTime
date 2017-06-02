package com.example.rumens.showtime.music.musicplay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.rumens.showtime.App;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.constant.ConstantUitles;
import com.example.rumens.showtime.music.musicplay.service.MusicPlayServise;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.rxBus.event.MusicContralEvent;
import com.example.rumens.showtime.utils.SharedPreferencesUtil;
import com.example.rumens.showtime.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author Zhaochen Ping
 * @create 2017/5/31
 * @description
 */

public class MusicPlay extends BaseActivity {
    public static final String LOCAL_MUSIC = "localmusic";
    public static final String LOCAL_POSITION = "localposition";
    public static final String LOCAL_IS = "islcaol";
    public static final String LOCAL_SONG_LIST = "localsonglist";
    private static final int MUSIC_PLAYING_CURENT_DURATION = 100;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.iv_needle)
    ImageView mIvNeedle;
    @BindView(R.id.fl_headerView)
    FrameLayout mFlHeaderView;
    @BindView(R.id.iv_playing_fav)
    ImageView mIvPlayingFav;
    @BindView(R.id.iv_playing_down)
    ImageView mIvPlayingDown;
    @BindView(R.id.iv_playing_cmt)
    ImageView mIvPlayingCmt;
    @BindView(R.id.iv_playing_more)
    ImageView mIvPlayingMore;
    @BindView(R.id.tv_music_duration_played)
    TextView mTvMusicDurationPlayed;
    @BindView(R.id.sb_play_seek)
    SeekBar mSbPlaySeek;
    @BindView(R.id.tv_music_duration)
    TextView mTvMusicDuration;
    @BindView(R.id.iv_playing_mode)
    ImageView mIvPlayingMode;
    @BindView(R.id.iv_playing_pre)
    ImageView mIvPlayingPre;
    @BindView(R.id.iv_playing_play)
    ImageView mIvPlayingPlay;
    @BindView(R.id.iv_playing_next)
    ImageView mIvPlayingNext;
    @BindView(R.id.iv_playing_playlist)
    ImageView mIvPlayingPlaylist;
    @BindView(R.id.iv_albumart)
    ImageView ivAlbumart;
    private SongLocalBean mLocalMusic;
    private int mPosition;
    private MusicServiceConnect mMusicServiceConnect;
    private static MusicPlayServise.MusicBind mMusicBind;
    private boolean isLocal = true;
    private RxBus rxBus;
    private SongLocalBean mLocalSongBean;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MUSIC_PLAYING_CURENT_DURATION:
                    upDataPlayTime();
                    break;
            }
        }
    };

    @Override
    protected void updateViews() {
        mSbPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekBar.setProgress(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mMusicBind.getMusicServiceInstance().seekTo(progress);
            }
        });
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        SongLocalBean songLocalBean = (SongLocalBean) intent.getParcelableExtra(LOCAL_MUSIC);
        intent.setClass(getApplicationContext(), MusicPlayServise.class);
        startService(intent);
        mMusicServiceConnect = new MusicServiceConnect();
        this.bindService(intent, mMusicServiceConnect, BIND_AUTO_CREATE);
        initToolBar(mToolbar,true,songLocalBean.title);
        mIvPlayingPlay.setImageResource(R.mipmap.play_rdi_btn_pause);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_music_play;
    }

    @Override
    protected void initInjector() {
        mLocalMusic = (SongLocalBean) getIntent().getExtras().getSerializable(LOCAL_MUSIC);
        mPosition = getIntent().getIntExtra(LOCAL_POSITION, 0);
        initRxBus();
    }


    public static void lunch(Context mContext, SongLocalBean item, int adapterPosition, List<SongLocalBean> songs) {
        Intent intent = new Intent(mContext, MusicPlay.class);
        intent.putExtra(LOCAL_MUSIC, item);
        intent.putExtra(LOCAL_POSITION, adapterPosition);
        intent.putExtra(LOCAL_IS, true);
        intent.putParcelableArrayListExtra(LOCAL_SONG_LIST, (ArrayList<? extends Parcelable>) songs);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.zoom_in_exit);
    }

    private void initRxBus() {
        rxBus = App.getRxBus();
        registerRxBus(MusicContralEvent.class, new Action1<MusicContralEvent>() {
            @Override
            public void call(MusicContralEvent musicContralEvent) {
                mLocalSongBean = musicContralEvent.getSongBean();
                getLocalSongInfos(mLocalSongBean);
                setUI(musicContralEvent);
            }
        });
    }

    private void setPlayTime() {
        int maxDuration = mMusicBind.getMusicServiceInstance().getDuration();
        String durationFormat = StringUtils.formatTime(maxDuration);
        mTvMusicDuration.setText(durationFormat);
        mSbPlaySeek.setMax(maxDuration);
        upDataPlayTime();
        upDataMusicMode();
    }

    private void upDataPlayTime() {
        int duration = mMusicBind.getMusicServiceInstance().getCurrentDuration();
        String durationPgFormat = StringUtils.formatTime(duration);
        mTvMusicDurationPlayed.setText(durationPgFormat);
        mSbPlaySeek.setProgress(duration);
        handler.sendEmptyMessageDelayed(MUSIC_PLAYING_CURENT_DURATION,1000);
    }


    private void getLocalSongInfos(SongLocalBean mLocalSongBean) {
        initToolBar(mToolbar,true,mLocalSongBean.title);
        setPlayTime();
    }


    private void setUI(MusicContralEvent musicContralEvent) {
        switch (musicContralEvent.eventType) {
            case MusicContralEvent.MUSIC_PLAY:
                mIvPlayingPlay.setImageResource(R.mipmap.play_rdi_btn_pause);
                break;
        }
    }

    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = rxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("NewsMainPresenter", throwable.toString());
            }
        });
        rxBus.addSubscription(this, subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxBus.unSubscribe(this);
        //移除所有消息
        handler.removeCallbacksAndMessages(null);
        finish();
    }

    @OnClick({R.id.iv_playing_mode, R.id.iv_playing_pre, R.id.iv_playing_play, R.id.iv_playing_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_playing_mode:
                MusicPlayServise.mCurrentMode = ++MusicPlayServise.mCurrentMode%3;
                upDataMusicMode();
                SharedPreferencesUtil.getInstance().putInt(ConstantUitles.MUSIC_MODE,MusicPlayServise.mCurrentMode);
                break;
            case R.id.iv_playing_pre:
                mMusicBind.getMusicServiceInstance().preOrNext(false);
                break;
            case R.id.iv_playing_play:
                upDataMusicState();
                break;
            case R.id.iv_playing_next:
                mMusicBind.getMusicServiceInstance().preOrNext(true);
                break;
        }
    }

    private void upDataMusicMode() {
        switch (MusicPlayServise.mCurrentMode){
            case MusicPlayServise.SINGO_MODE:
                mIvPlayingMode.setImageResource(R.mipmap.play_icn_one);
                break;
            case MusicPlayServise.LIST_MODE:
                mIvPlayingMode.setImageResource(R.mipmap.play_icn_loop);
                break;
            case MusicPlayServise.SHUFFLE_MODE:
                mIvPlayingMode.setImageResource(R.mipmap.play_icn_shuffle);
                break;
        }
    }

    private void upDataMusicState() {
        mMusicBind.getMusicServiceInstance().hasPlay();
        boolean isPlaying = mMusicBind.getMusicServiceInstance().isPlaying();
        if(isPlaying){
            mIvPlayingPlay.setImageResource(R.mipmap.play_rdi_btn_pause);
            handler.sendEmptyMessageDelayed(MUSIC_PLAYING_CURENT_DURATION,1000);
        }else {
            mIvPlayingPlay.setImageResource(R.mipmap.play_rdi_btn_play);
            handler.removeMessages(MUSIC_PLAYING_CURENT_DURATION);
        }
    }

    public  class MusicServiceConnect implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBind = (MusicPlayServise.MusicBind) service;
            setPlayTime();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
