package com.example.rumens.showtime.video.videoliveplay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.local.VideoInfo;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Zhaochen Ping
 * @create 2017/5/5
 * @description
 */

public class DouyuWebViewPlayActivity extends BaseActivity<IVideoPresenter> implements IVideoView {
    private static final String DOU_YU_WEBVIEW = "webview";
    private static final String DOU_YU_WEBVIEW_TYPE = "type";
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.progressbar_webview)
    ProgressBar mProgressBar;
    @BindView(R.id.web_main)
    WebView mWebView;
    @BindView(R.id.web_view_play)
    LinearLayout webViewPlay;
    private IX5WebChromeClient.CustomViewCallback callback;

    public static void lunchLiveDouyu(Context mContext, DouyuLiveListItemBean.DataBean item, String mPlatformType) {
        Intent intent = new Intent(mContext,DouyuWebViewPlayActivity.class);
        intent.putExtra(DOU_YU_WEBVIEW,item);
        intent.putExtra(DOU_YU_WEBVIEW_TYPE,mPlatformType);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }

    @Override
    public void loadLiveData(LiveDetailBean data) {

    }

    @Override
    public void loadLiveDouyuData(OldLiveVideoInfo data) {

    }

    @Override
    public void loadVideoData(VideoInfo data) {

    }

    @Override
    public void loadDanmakuData(InputStream inputStream) {

    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        // // TODO: 2016/11/26  常量问题
        DouyuLiveListItemBean.DataBean mDouyuWebView = getIntent().getParcelableExtra(DOU_YU_WEBVIEW);
        String webUrl = mDouyuWebView.getJumpUrl() + "?from=dy";
        String title = mDouyuWebView.getRoom_id();
        setTitle(title);
        initWebView();
        mWebView.loadUrl(webUrl);
    }

    private void initWebView() {
        WebSettings webSetting = mWebView.getSettings();
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
        mWebView.setDrawingCacheEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);

        initWebViewPlay();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewPlay() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return false;
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                callback=customViewCallback;
            }
            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (mWebView != null) {
                    ViewGroup viewGroup = (ViewGroup) mWebView.getParent();
                    viewGroup.removeView(mWebView);
                }
            }
            @Override
            public void onReceivedTitle(WebView arg0, final String arg1) {
                super.onReceivedTitle(arg0, arg1);
                tvTitle.setText(arg1);

            }
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                changeProgress(i);
            }
        });
    }

    private void changeProgress(int i) {

        if (i >= 0 && i < 100) {
            mProgressBar.setProgress(i);
            mProgressBar.setVisibility(View.VISIBLE);
        } else if (i == 100) {
            mProgressBar.setProgress(100);
            mProgressBar.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    @OnClick(R.id.img_back)
    public void back() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_webview_play;
    }

    @Override
    protected void initInjector() {

    }
}
