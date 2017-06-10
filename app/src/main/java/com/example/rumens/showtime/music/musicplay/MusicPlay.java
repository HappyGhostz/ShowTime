package com.example.rumens.showtime.music.musicplay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.rumens.showtime.App;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.adapter.listener.OnRecyclerViewItemClickListener;
import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.api.bean.SongListDetail;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.constant.ConstantUitles;
import com.example.rumens.showtime.music.listplay.MusicListDetialActivity;
import com.example.rumens.showtime.music.musicplay.service.MusicPlayServise;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.rxBus.event.MusicContralEvent;
import com.example.rumens.showtime.utils.SharedPreferencesUtil;
import com.example.rumens.showtime.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    public static final String RECOMMEND_MUSIC = "recommendmusic";
    public static final String RECOMMEND_POSITION = "recommendposition";
    public static final String RECOMMEND_SONG_LIST = "recommendsonglist";
    public static final String MUSIC_IS_RANK = "musicisrank";
    private static final String RECOMMEND_MUSIC_INFO = "songinfo";
    public static final String MUSIC_NET_URL = "songurl";
    public static final String MUSIC_NET_PIC = "songpic";
    public static final String MUSIC_NET_TITLE = "songtitle";
    public static final String MUSIC_NET_URL_LIST = "songurllist";
    public static final String MUSIC_NET_PIC_LIST = "songpiclist";
    public static final String MUSIC_NET_TITLE_LIST = "songtitlelist";
    public static final String MUSIC_SELECT = "isselect";
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
    private boolean isLocal;
    private SongDetailInfo mSongDetialInfo;

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
        if(isLocal){
            SongLocalBean songLocalBean = (SongLocalBean) intent.getParcelableExtra(LOCAL_MUSIC);
            initToolBar(mToolbar,true,songLocalBean.title);
        }else{
//            ArrayList<SongDetailInfo> mSongInfos = intent.getParcelableArrayListExtra(RECOMMEND_SONG_LIST);
            boolean isRank = intent.getBooleanExtra(MUSIC_IS_RANK, false);
//            SongDetailInfo songinfo= intent.getExtras().getParcelable(RECOMMEND_MUSIC);
            String songTitle = intent.getStringExtra(MUSIC_NET_TITLE);
            initToolBar(mToolbar,true,songTitle);
            picUrl=intent.getStringExtra(MUSIC_NET_PIC);
        }
        intent.setClass(getApplicationContext(), MusicPlayServise.class);
        startService(intent);
        mMusicServiceConnect = new MusicServiceConnect();
        this.bindService(intent, mMusicServiceConnect, BIND_AUTO_CREATE);
        mIvPlayingPlay.setImageResource(R.mipmap.play_rdi_btn_pause);
        initViewPager();
        pictureDim();
         mIvNeedle.setRotation(0);
    }
    private String picUrl=null;
    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(RoundFragment.lunch(this,isLocal,picUrl));
        List<String> titles = new ArrayList<>();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setItems(fragments,titles);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected int getContenView() {
        return R.layout.activity_music_play;
    }

    @Override
    protected void initInjector() {
        isLocal = getIntent().getBooleanExtra(LOCAL_IS, false);
        if(isLocal){
            mPosition = getIntent().getIntExtra(LOCAL_POSITION, 0);
        }
        initRxBus();
    }


    public static void lunch(Context mContext, SongLocalBean item, int adapterPosition, List<SongLocalBean> songs) {
        Intent intent = new Intent(mContext, MusicPlay.class);
        intent.putExtra(LOCAL_MUSIC, item);
        intent.putExtra(LOCAL_POSITION, adapterPosition);
        intent.putExtra(LOCAL_IS, true);
        intent.putExtra(MUSIC_SELECT,true);
        intent.putParcelableArrayListExtra(LOCAL_SONG_LIST, (ArrayList<? extends Parcelable>) songs);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }
   /* public static void lunchNet(Context context, SongDetailInfo songDetailInfo, List<SongDetailInfo> mSongDetialInfos, int position) {
        Intent intent = new Intent(context,MusicPlay.class);
        intent.putExtra(RECOMMEND_POSITION,position);
        intent.putExtra(LOCAL_IS,false);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECOMMEND_MUSIC,songDetailInfo);
        intent.putExtras(bundle);
//        intent.putExtra(RECOMMEND_MUSIC,songDetailInfo);
        intent.putParcelableArrayListExtra(RECOMMEND_SONG_LIST, (ArrayList<? extends Parcelable>) mSongDetialInfos);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }*/
    public static void lunchNet(Context context, String songUrl, String songPic, String songTitle, List<String> mSongUrl, List<String> mSongPic, List<String> mSongTitle, int position) {
        Intent intent = new Intent(context,MusicPlay.class);
        intent.putExtra(RECOMMEND_POSITION,position);
        intent.putExtra(LOCAL_IS,false);
        intent.putExtra(MUSIC_NET_URL,songUrl);
        intent.putExtra(MUSIC_NET_PIC,songPic);
        intent.putExtra(MUSIC_SELECT,true);
        intent.putExtra(MUSIC_NET_TITLE,songTitle);
        intent.putStringArrayListExtra(MUSIC_NET_URL_LIST, (ArrayList<String>) mSongUrl);
        intent.putStringArrayListExtra(MUSIC_NET_PIC_LIST, (ArrayList<String>) mSongPic);
        intent.putStringArrayListExtra(MUSIC_NET_TITLE_LIST, (ArrayList<String>) mSongTitle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    private void initRxBus() {
        rxBus = App.getRxBus();
        registerRxBus(MusicContralEvent.class, new Action1<MusicContralEvent>() {
            @Override
            public void call(MusicContralEvent musicContralEvent) {
                if(isLocal){
                    mLocalSongBean = musicContralEvent.getSongBean();
                    getLocalSongInfos(mLocalSongBean);
                }else {
                    String title = musicContralEvent.getTitle();
                    picUrl = musicContralEvent.getList();
                    getNetSongInfos(title);
                    initViewPager();
                    pictureDim();

                }
                setUI(musicContralEvent);
            }
        });
    }

    private void getNetSongInfos(String string) {
        initToolBar(mToolbar,true,string);
        setPlayTime();
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
        unbindService(mMusicServiceConnect);
//        finish();
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
                mMusicBind.getMusicServiceInstance().preOrNext(false,isLocal);
                break;
            case R.id.iv_playing_play:
                upDataMusicState();
                break;
            case R.id.iv_playing_next:
                mMusicBind.getMusicServiceInstance().preOrNext(true,isLocal);
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
            mIvNeedle.setRotation(0);
        }else {
            mIvPlayingPlay.setImageResource(R.mipmap.play_rdi_btn_play);
            handler.removeMessages(MUSIC_PLAYING_CURENT_DURATION);
            mIvNeedle.setRotation(-30);
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
    private void pictureDim() {
        Observable.just(picUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return getDimBitmap(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        applyBlur(bitmap);
                    }
                });
    }
    private static int radius = 25;
    private void applyBlur(Bitmap bitmap) {
        //处理得到模糊效果的图
        RenderScript renderScript = RenderScript.create(App.getAppContext());
        // Allocate memory for Renderscript to work with
        final Allocation input = Allocation.createFromBitmap(renderScript, bitmap);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        // Copy the output to the blurred bitmap
        output.copyTo(bitmap);
        renderScript.destroy();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.getResources(), bitmap);
        ivAlbumart.setBackground(bitmapDrawable);
    }
    private Bitmap getDimBitmap(String url) {
        if(url==null){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.muiscderault);
            return bitmap;
        }else {
            FutureTarget<File> fileFutureTarget = Glide.with(this).load(url).downloadOnly(100, 100);
            String mPath;
            FileInputStream mIs = null;
            try{
                File file = fileFutureTarget.get();
                mPath = file.getAbsolutePath();
                mIs = new FileInputStream(mPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(mIs);
            return bitmap;
        }
    }
}
