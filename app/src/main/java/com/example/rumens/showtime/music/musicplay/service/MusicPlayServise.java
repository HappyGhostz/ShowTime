package com.example.rumens.showtime.music.musicplay.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.rumens.showtime.App;
import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.constant.ConstantUitles;
import com.example.rumens.showtime.music.musicplay.MusicPlay;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.rxBus.event.MusicContralEvent;
import com.example.rumens.showtime.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Zhaochen Ping
 * @create 2017/5/31
 * @description
 */

public class MusicPlayServise extends Service implements MediaPlayer.OnPreparedListener {
    //播放的模式
    public static final int LIST_MODE = 0;
    public static final int SINGO_MODE = 1;
    public static final int SHUFFLE_MODE = 2;
    private MediaPlayer mMediaPlayer;
    private String mMusicPath;
    private long mCurrentPosition;
    private long mDuration;
    private SongLocalBean mSongLocalBean;
    private int mCurrent=-1;
    private boolean isLocal;
    private RxBus rxBus;
    private ArrayList<SongLocalBean> songs;
    public static int mCurrentMode = 0;
    private ArrayList<SongDetailInfo> mRecommendSongInfos;
    private boolean isRank;
    private List<String> songUrls;
    private ArrayList<String> songPics;
    private ArrayList<String> songTitles;
    private boolean mIsSelect;
    private boolean mCurrtentSelect=false;
    private boolean mFrist=true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        rxBus = new RxBus();
        mCurrentMode = SharedPreferencesUtil.getInstance().getInt(ConstantUitles.MUSIC_MODE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isLocal = intent.getBooleanExtra(MusicPlay.LOCAL_IS,false);
        mIsSelect = intent.getBooleanExtra(MusicPlay.MUSIC_SELECT,false);
        mCurrtentSelect = mIsSelect;
        if(isLocal){
            int position = intent.getIntExtra(MusicPlay.LOCAL_POSITION, 0);
            songs = intent.getParcelableArrayListExtra(MusicPlay.LOCAL_SONG_LIST);
            if(position-1!=mCurrent){
                mCurrent = position-1;
                playMusic();
            }
        }else {
            int position = intent.getIntExtra(MusicPlay.RECOMMEND_POSITION, 0);
            songUrls = intent.getStringArrayListExtra(MusicPlay.MUSIC_NET_URL_LIST);
            songPics = intent.getStringArrayListExtra(MusicPlay.MUSIC_NET_PIC_LIST);
            songTitles = intent.getStringArrayListExtra(MusicPlay.MUSIC_NET_TITLE_LIST);
            if(position-1!=mCurrent){
                    mCurrent = position-1;
                    playMusic();
                }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void playMusic() {
        if(mMediaPlayer==null){
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(new MusicCompleteListener());
        }else {
            mMediaPlayer.reset();
        }
        try{
            if(isLocal){
                mMediaPlayer.setDataSource(songs.get(mCurrent).path);
            }else {
                mMediaPlayer.setDataSource(songUrls.get(mCurrent));
            }
            mMediaPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        RxBus rxBus = App.getRxBus();
        if(isLocal){
            rxBus.post(new MusicContralEvent(MusicContralEvent.MUSIC_PLAY,songs.get(mCurrent)));
        }else{
            rxBus.post(new MusicContralEvent(MusicContralEvent.MUSIC_PLAY,songPics.get(mCurrent),songTitles.get(mCurrent)));
        }
    }

    public int getCurrentDuration(){
        return mMediaPlayer.getCurrentPosition();
    }
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public void preOrNext(boolean isNext,boolean isLocal){
        preNext(isNext,isLocal);
    }

    private void preNext(boolean isNext,boolean isLocal) {
        if(isLocal){
            switch (mCurrentMode){
                case LIST_MODE:
                    if(isNext){
                        mCurrent=(mCurrent==songs.size()-1)?0:++mCurrent;
                    }else {
                        mCurrent=(mCurrent==0)?songs.size()-1:--mCurrent;
                    }
                    break;
                case SHUFFLE_MODE:
                    Random random = new Random();
                    int randomPosition = random.nextInt(songs.size());
                    while (randomPosition==mCurrent){
                        randomPosition=random.nextInt(songs.size());
                    }
                    mCurrent=randomPosition;
                    break;
            }
        }else {
            switch (mCurrentMode){
                case LIST_MODE:
                    if(isNext){
                        mCurrent=(mCurrent==songUrls.size()-1)?0:++mCurrent;
                    }else {
                        mCurrent=(mCurrent==0)?songUrls.size()-1:--mCurrent;
                    }
                    break;
                case SHUFFLE_MODE:
                    Random random = new Random();
                    int randomPosition = random.nextInt(songUrls.size());
                    while (randomPosition==mCurrent){
                        randomPosition=random.nextInt(songUrls.size());
                    }
                    mCurrent=randomPosition;
                    break;
            }
        }
        playMusic();
    }
    public void hasPlay(){
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
//            rxBus.post(new MusicContralEvent(MusicContralEvent.MUSIC_PUSE));
        }else{
            mMediaPlayer.start();
//            rxBus.post(new MusicContralEvent(MusicContralEvent.MUSIC_PLAY));
        }
    }
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    public class MusicBind extends Binder{
        public  MusicPlayServise getMusicServiceInstance(){
            return MusicPlayServise.this;
        }
    }

    private class MusicCompleteListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            preNext(true,isLocal);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
