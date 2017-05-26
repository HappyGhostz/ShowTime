package com.example.rumens.showtime.music.listplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.rumens.showtime.App;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideOnScaleAndeAlphaAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.api.bean.SongListDetail;
import com.example.rumens.showtime.api.bean.WrapperSongListInfo;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerMusicListDetailComponent;
import com.example.rumens.showtime.inject.modules.MusicListDetailModule;
import com.example.rumens.showtime.utils.DefIconFactory;
import com.example.rumens.showtime.utils.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class MusicListDetialActivity extends BaseActivity<IBasePresenter> implements IBaseMusicListView {
    @BindView(R.id.album_art)
    ImageView mAlbumArt;
    @BindView(R.id.overlay)
    View mOverlay;
    @BindView(R.id.iv_songlist_photo)
    ImageView mIvSonglistPhoto;
    @BindView(R.id.tv_songlist_count)
    TextView mTvSonglistCount;
    @BindView(R.id.fra)
    FrameLayout mFra;
    @BindView(R.id.tv_songlist_name)
    TextView mTvSonglistName;
    @BindView(R.id.tv_songlist_detail)
    TextView mTvSonglistDetail;
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;
    @BindView(R.id.ll_collect)
    LinearLayout mLlCollect;
    @BindView(R.id.iv_comment)
    ImageView mIvComment;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.ll_share)
    LinearLayout mLlShare;
    @BindView(R.id.ll_download)
    LinearLayout mLlDownload;
    @BindView(R.id.headerdetail)
    RelativeLayout mHeaderdetail;
    @BindView(R.id.headerview)
    FrameLayout mHeaderview;
    @BindView(R.id.rl_toobar)
    RelativeLayout mRlToobar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.irv_list_detail)
    RecyclerView mIrvSongDetail;
    @BindView(R.id.bottom_container)
    FrameLayout mBottomContainer;
    @Inject
    BaseQuickAdapter mAdapter;
    private String songListid;
    private boolean isLocal;
    private String photoUrl;
    private String listName;
    private String detail;
    private String count;
    private RelativeLayout mRlPlayAll;
    private ImageView mIvSetting;
    private TextView mTvPlayNumber;
    private List<SongListDetail.SongDetail> mList = new ArrayList<>();
    private SongDetailInfo[] mInfos;
    //song_id 对应的在集合中的位置
    private HashMap<String, Integer> positionMap = new HashMap<>();
    private String path;
    private static int radius = 25;


    @Override
    public void loadMusicListDetial(SongListDetail songListDetail) {
        List<SongListDetail.SongDetail> songDetailList = songListDetail.getContent();
        mAdapter.updateItems(songDetailList);
        //初始化数组集合
        mList.addAll(songDetailList);
        mInfos = new SongDetailInfo[mList.size()];
        initHeadData(songListDetail);
        initMusicList();
    }

    private void initHeadData(SongListDetail songListDetail) {
        List<SongListDetail.SongDetail> songDetailList = songListDetail.getContent();
        mTvPlayNumber.setText("(共"+songDetailList.size()+"首)");

    }

    private void initMusicList() {
        for (int i = 0; i < mList.size(); i++) {
            SongListDetail.SongDetail songDetail = mList.get(i);
            String song_id = songDetail.getSong_id();
            positionMap.put(song_id, i);
//            mPresenter.requestSongDetail(AppConstantValue.MUSIC_URL_FROM_2, AppConstantValue.MUSIC_URL_VERSION, AppConstantValue.MUSIC_URL_FORMAT, AppConstantValue.MUSIC_URL_METHOD_SONG_DETAIL
//                    , song_id);
        }
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        initToolBar(mToolbar,true,"歌单");
        initToolBarBackRes();
        View view = View.inflate(this,R.layout.item_music_rank_list_play_head, null);
//        View view = from.inflate(R.layout.item_book_help_detial_head, null);
        initHeadView(view);
        mAdapter.addHeaderView(view);
        SlideOnScaleAndeAlphaAdapter slideAdapter = new SlideOnScaleAndeAlphaAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(this,mIrvSongDetail,true,slideAdapter);
    }

    private void initToolBarBackRes() {
        ImageLoader.loadCenterCrop(this,photoUrl,mIvSonglistPhoto, DefIconFactory.provideIcon());
        mTvSonglistCount.setText(count);
        mTvSonglistName.setText(listName);
        String[] split = detail.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("标签：");
        for (int i = 0; i < split.length; i++) {
            stringBuffer.append(split[i] + " ");
        }
        mTvSonglistDetail.setText(stringBuffer);
//        new PathAsyncTask(mAlbumArt).execute(photoUrl);
        pictureDim();

    }

    private void pictureDim() {
        Observable.just(photoUrl)
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
        mAlbumArt.setBackground(bitmapDrawable);
    }

    private Bitmap getDimBitmap(String url) {
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


    private void initHeadView(View view) {
        mRlPlayAll = (RelativeLayout) view.findViewById(R.id.rl_play_all_layout);
        mIvSetting = (ImageView) view.findViewById(R.id.iv_detail_select);
        mTvPlayNumber = (TextView) view.findViewById(R.id.tv_play_all_number);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_music_list_detial;
    }

    @Override
    protected void initInjector() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            songListid = (String) extras.get("songListId");
            isLocal = (boolean) extras.get("islocal");
            photoUrl = (String) extras.get("songListPhoto");
            listName = (String) extras.get("songListname");
            detail = (String) extras.get("songListTag");
            count = (String) extras.get("songListCount");
        }
        DaggerMusicListDetailComponent.builder()
                .appComponent(getAppComponent())
                .musicListDetailModule(new MusicListDetailModule(this,songListid))
                .build()
                .inject(this);
    }

    public static void lunch(Context mContext, WrapperSongListInfo.SongListInfo item) {
        Intent intent = new Intent(mContext, MusicListDetialActivity.class);
        intent.putExtra("songListId", item.getListid());
        intent.putExtra("islocal", false);
        intent.putExtra("songListPhoto", item.getPic_300());
        intent.putExtra("songListname", item.getTitle());
        intent.putExtra("songListTag", item.getTag());
        intent.putExtra("songListCount", item.getListenum());
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

}
