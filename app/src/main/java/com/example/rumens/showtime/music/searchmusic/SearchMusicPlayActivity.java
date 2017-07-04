package com.example.rumens.showtime.music.searchmusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.base.BaseActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;

/**
 * @author Zhao Chenping
 * @creat 2017/7/4.
 * @description
 */

public class SearchMusicPlayActivity extends BaseActivity{
    private static final String SEARCH_MUSIC_NAME = "name";
    private static final String SEARCH_MUSIC_URL = "url";
    @BindView(R.id.tool_search_bar)
    Toolbar mToolSearchBar;
    @BindView(R.id.wv_music)
    WebView mWvMusic;

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {
        String musicName = getIntent().getStringExtra(SEARCH_MUSIC_NAME);
        String musicUrl = getIntent().getStringExtra(SEARCH_MUSIC_URL);
        initToolBar(mToolSearchBar,true,musicUrl);
        initWebView(musicName);
    }

    private void initWebView(String musicUrl) {
        WebSettings webSetting = mWvMusic.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(true);
//        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        mWvMusic.setDrawingCacheEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        mWvMusic.loadUrl(musicUrl);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_search_music_play;
    }

    @Override
    protected void initInjector() {

    }

    public static void lunch(Context mContext, String page, String name) {
        Intent intent = new Intent(mContext, SearchMusicPlayActivity.class);
        intent.putExtra(SEARCH_MUSIC_URL, page);
        intent.putExtra(SEARCH_MUSIC_NAME, name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
//        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
//        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
    }
}
