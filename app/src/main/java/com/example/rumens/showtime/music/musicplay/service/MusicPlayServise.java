package com.example.rumens.showtime.music.musicplay.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.rumens.showtime.App;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.constant.ConstantUitles;
import com.example.rumens.showtime.music.musicplay.MusicPlay;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.rxBus.event.MusicContralEvent;
import com.example.rumens.showtime.utils.ImageLoader;
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
    private static final int PLAY_PRE = 1000;
    private static final int PLAY_NEXT = 1001;
    private static final int PLAY_PAUSE = 1002;
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
    private ArrayList<String> songArt;

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
        boolean fromNotificaton = intent.getBooleanExtra("fromNotification", false);
        if(fromNotificaton){
            switch (intent.getIntExtra("operation",0)){
                case PLAY_PRE:
                    preOrNext(false,isLocal);
                    break;
                case PLAY_NEXT:
                    preOrNext(true,isLocal);
                    break;
                case PLAY_PAUSE:
                    if(isPlaying()){
                        mMediaPlayer.pause();
                    }else{
                        mMediaPlayer.start();
                    }
                    creatCustomBigNotifcaition();
                    break;
            }
        }else{
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
                songArt = intent.getStringArrayListExtra(MusicPlay.MUSIC_NET_ART_LIST);
                if(position-1!=mCurrent){
                    mCurrent = position-1;
                    playMusic();
                }
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
        creatCustomBigNotifcaition();//API16以上的通知
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

    private void creatCustomBigNotifcaition() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker(isLocal?songs.get(mCurrent).title:songTitles.get(mCurrent));
        builder.setSmallIcon(R.mipmap.play_rdi_btn_play);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        builder.setStyle(bigPictureStyle);
        builder.setCustomContentView(getRemoteView());
        if(Build.VERSION.SDK_INT>16){
            builder.setCustomBigContentView(getBigRomateView());
        }


        builder.setOngoing(true);
        Notification build = builder.build();
        NotificationManager  manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,build);
    }

    private RemoteViews getBigRomateView() {
        final RemoteViews bigRemoteView = new RemoteViews(getPackageName(),R.layout.item_big_notification);
        bigRemoteView.setTextViewText(R.id.tv_notification_name,isLocal?songs.get(mCurrent).title:songTitles.get(mCurrent));
        bigRemoteView.setTextViewText(R.id.tv_notification_album,isLocal?songs.get(mCurrent).artist:songArt.get(mCurrent));
        if(isPlaying()){
            bigRemoteView.setImageViewResource(R.id.iv_notification_playpause,R.mipmap.play_rdi_btn_pause);
        }else {
            bigRemoteView.setImageViewResource(R.id.iv_notification_playpause,R.mipmap.play_rdi_btn_play);
        }
        if(isLocal){
            bigRemoteView.setImageViewResource(R.id.iv_notification_album,R.mipmap.background_default);
        }else{

            Glide.with(this).load(songPics.get(mCurrent)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bigRemoteView.setImageViewBitmap(R.id.iv_notification_album,resource);
                }
            }); //方法中设置asBitmap可以设置回调类型
//            bigRemoteView.setImageViewBitmap(R.id.iv_notification_album,ImageLoader.getBitMap(this,songPics.get(mCurrent)));
//            bigRemoteView.setImageViewUri(R.id.iv_notification_album, Uri.parse(songPics.get(mCurrent)));

        }
        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_next,getNextPendingIntent());
        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_pre,getPrePendingIntent());
        bigRemoteView.setOnClickPendingIntent(R.id.rl_notification_layout,getActivityPendingIntent());
        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_playpause,getPlayPausePendingIntent());
//        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_canel,getCancelPendingIntent());
        return bigRemoteView;
    }

    private PendingIntent getNextPendingIntent() {
        Intent intent = new Intent(getApplicationContext(),MusicPlayServise.class);
        intent.putExtra("fromNotification",true);
        intent.putExtra("operation",PLAY_NEXT);
        //参数1：上下文；参数2：用来区分不同的notification；参数3：具体操作的意图；参数4：更新当前的notification，若
        //参数2相同，则新的通知会替代前一个通知
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private PendingIntent getPrePendingIntent() {
        Intent intent = new Intent(getApplicationContext(),MusicPlayServise.class);
        intent.putExtra("fromNotification",true);
        intent.putExtra("operation",PLAY_PRE);
        //参数1：上下文；参数2：用来区分不同的notification；参数3：具体操作的意图；参数4：更新当前的notification，若
        //参数2相同，则新的通知会替代前一个通知
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private PendingIntent getActivityPendingIntent() {
        Intent intent = new Intent(getApplicationContext(),MusicPlay.class);
        intent.putExtra("fromNotification",true);
//        intent.putExtra("operation",PLAY_PRE);
        //参数1：上下文；参数2：用来区分不同的notification；参数3：具体操作的意图；参数4：更新当前的notification，若
        //参数2相同，则新的通知会替代前一个通知
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private PendingIntent getPlayPausePendingIntent() {
        Intent intent = new Intent(getApplicationContext(),MusicPlayServise.class);
        intent.putExtra("fromNotification",true);
        intent.putExtra("operation",PLAY_PAUSE);
        //参数1：上下文；参数2：用来区分不同的notification；参数3：具体操作的意图；参数4：更新当前的notification，若
        //参数2相同，则新的通知会替代前一个通知
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),3,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private RemoteViews getRemoteView() {

        RemoteViews bigRemoteView = new RemoteViews(getPackageName(),R.layout.item_narmal_notification);
        bigRemoteView.setTextViewText(R.id.tv_notification_normal_name,isLocal?songs.get(mCurrent).title:songTitles.get(mCurrent));
        bigRemoteView.setTextViewText(R.id.tv_notification_normal_album,isLocal?songs.get(mCurrent).artist:songArt.get(mCurrent));

        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_normal_next,getNextPendingIntent());
        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_normal_pre,getPrePendingIntent());
        bigRemoteView.setOnClickPendingIntent(R.id.rl_notification_layout,getActivityPendingIntent());
//        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_playpause,getPlayPausePendingIntent());
//        bigRemoteView.setOnClickPendingIntent(R.id.iv_notification_canel,getCancelPendingIntent());
        return bigRemoteView;
    }
}
