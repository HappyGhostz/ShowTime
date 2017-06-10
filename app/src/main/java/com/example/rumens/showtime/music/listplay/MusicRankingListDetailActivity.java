package com.example.rumens.showtime.music.listplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideOnScaleAndeAlphaAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRecyclerViewItemClickListener;
import com.example.rumens.showtime.api.IMusicsApi;
import com.example.rumens.showtime.api.bean.RankingListDetail;
import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerMusicRankingListDetailComponent;
import com.example.rumens.showtime.inject.modules.MusicRankingListDetailModule;
import com.example.rumens.showtime.music.musicplay.MusicPlay;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicRankingListDetailActivity extends BaseActivity<IBasePresenter> implements IBaseRankListPlayView {
    @BindView(R.id.iv_album_art)
    ImageView mIivAlbumArt;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rl_toobar)
    RelativeLayout mRlToobar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout nToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.irv_song_detail)
    RecyclerView mIrvSongDetail;
    @BindView(R.id.bottom_container)
    LinearLayout mBottomContainer;
    private int mType;
    @Inject
    BaseQuickAdapter mAdapter;
    private RelativeLayout mRlPlayAll;
    private ImageView mIvSetting;
    private TextView mTvPlayNumber;

    private List<RankingListDetail.SongListBean> mList = new ArrayList<>();
    //请求返回的SongDetailInfo先存放在数组中，对应下标索引是其在集合中所处位置
    private SongDetailInfo[] mInfos;
    //song_id 对应的在集合中的位置
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private String mTitle;
    private List<SongDetailInfo> mSongDetialInfos;
    private List<String> mSongTitle ;
    private List<String> mSongPic;
    private List<String> mSongUrl;

    @Override
    public void loadRankPlayList(RankingListDetail detail) {
        List<RankingListDetail.SongListBean> song_list = detail.getSong_list();
        mAdapter.updateItems(song_list);
        mList.addAll(song_list);
        //初始化数组集合
        mInfos = new SongDetailInfo[mList.size()];
        initHeadData(detail);
        initMusicList();
    }

    private void initMusicList() {
        mSongTitle = new ArrayList<>();
        mSongPic=new ArrayList<>();
        mSongUrl=new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            RankingListDetail.SongListBean songDetail = mList.get(i);
            String song_id = songDetail.getSong_id();
            positionMap.put(song_id, i);
            RetrofitService.loadSongDetail(IMusicsApi.MUSIC_URL_FROM_2, IMusicsApi.MUSIC_URL_VERSION,
                    IMusicsApi.MUSIC_URL_FORMAT, IMusicsApi.MUSIC_URL_METHOD_SONG_DETAIL
                        , song_id)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mBottomContainer.setVisibility(View.VISIBLE);
                        }
                    })
                    .subscribe(new Subscriber<SongDetailInfo>() {
                        @Override
                        public void onCompleted() {
                            mBottomContainer.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast("哇,出错了哎-o-");
                        }

                        @Override
                        public void onNext(SongDetailInfo songDetailInfo) {
                            mSongTitle.add(songDetailInfo.getSonginfo().getTitle());
                            mSongPic.add(songDetailInfo.getSonginfo().getPic_premium());
                            mSongUrl.add(songDetailInfo.getBitrate().getFile_link());
                        }
                    });
        }
    }

    private void initHeadData(RankingListDetail detail) {
        List<RankingListDetail.SongListBean> list = detail.getSong_list();
        mTvPlayNumber.setText("(共"+list.size()+"首)");
        RankingListDetail.BillboardBean billboard = detail.getBillboard();
        mTvName.setText(billboard.getName());
        ImageLoader.loadCenterCrop(this,billboard.getPic_s210(),mIivAlbumArt, DefIconFactory.provideIcon());
    }

    @Override
    protected void updateViews() {
         mPresenter.getData();
    }

    @Override
    protected void initView() {

         initToolBar(mToolbar,true,mTitle);
        View view = View.inflate(this,R.layout.item_music_rank_list_play_head, null);
//        View view = from.inflate(R.layout.item_book_help_detial_head, null);
        initHeadView(view);
        mAdapter.addHeaderView(view);
        SlideOnScaleAndeAlphaAdapter slideAdapter = new SlideOnScaleAndeAlphaAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(this,mIrvSongDetail,true,slideAdapter);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String songUrl = mSongUrl.get(position - 1);
                String songPic = mSongPic.get(position - 1);
                String songTitle = mSongTitle.get(position - 1);
                MusicPlay.lunchNet(MusicRankingListDetailActivity.this,songUrl,songPic,songTitle,mSongUrl,mSongPic,mSongTitle,position);
            }
        });
    }

    private void initHeadView(View view) {
        mRlPlayAll = (RelativeLayout) view.findViewById(R.id.rl_play_all_layout);
        mIvSetting = (ImageView) view.findViewById(R.id.iv_detail_select);
        mTvPlayNumber = (TextView) view.findViewById(R.id.tv_play_all_number);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_rank_play_list;
    }

    @Override
    protected void initInjector() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mType = (int) extras.get("type");
            mTitle = (String) extras.get("title");
        }
        DaggerMusicRankingListDetailComponent.builder()
                .appComponent(getAppComponent())
                .musicRankingListDetailModule(new MusicRankingListDetailModule(this,mType))
                .build()
                .inject(this);
    }

    public static void lunch(Context mContext, int position,String title) {
        Intent intent = new Intent(mContext, MusicRankingListDetailActivity.class);
        if (position + 1 == 5) {
            intent.putExtra("type", 20);
        } else if (position + 1 == 8) {
            intent.putExtra("type", 24);
        } else if(position+1==3){
            intent.putExtra("type",21);
        }else if(position+1==6){
            intent.putExtra("type",22);
        }else if(position+1==9){
            intent.putExtra("type",23);
        }else if(position+1==7){
            intent.putExtra("type",25);
        }else if(position+1==10){
            intent.putExtra("type",8);
        }else if(position+1==4){
            intent.putExtra("type",100);
        }else {
            intent.putExtra("type", position + 1);
        }
        intent.putExtra("title",title);
        mContext.startActivity(intent);
        ((Activity)mContext).overridePendingTransition(R.anim.fade_entry,R.anim.fade_exit);
    }
}
