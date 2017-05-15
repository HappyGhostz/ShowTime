package com.example.rumens.showtime.reader.bookread;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.BookMarkAdapter;
import com.example.rumens.showtime.adapter.ReadThemeAdapter;
import com.example.rumens.showtime.adapter.TocListAdapter;
import com.example.rumens.showtime.api.bean.BookMark;
import com.example.rumens.showtime.api.bean.BookMixATocBean;
import com.example.rumens.showtime.api.bean.ChapterReadBean;
import com.example.rumens.showtime.api.bean.DownloadMessage;
import com.example.rumens.showtime.api.bean.DownloadProgress;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.entity.ReadTheme;
import com.example.rumens.showtime.inject.component.DaggerReadBookComponent;
import com.example.rumens.showtime.inject.modules.ReadBookModule;
import com.example.rumens.showtime.reader.downloadservice.DownloadBookService;
import com.example.rumens.showtime.reader.downloadservice.DownloadQueue;
import com.example.rumens.showtime.rxBus.EventManager;
import com.example.rumens.showtime.utils.CacheManager;
import com.example.rumens.showtime.utils.CollectionsManager;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FileUtils;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ScreenUtils;
import com.example.rumens.showtime.utils.SettingManager;
import com.example.rumens.showtime.utils.SharedPreferencesUtil;
import com.example.rumens.showtime.utils.StatusBarCompat;
import com.example.rumens.showtime.utils.ThemeManager;
import com.example.rumens.showtime.utils.ToastUtils;
import com.example.rumens.showtime.widget.readview.OnReadStateChangeListener;
import com.example.rumens.showtime.widget.readview.OverlappedWidget;
import com.example.rumens.showtime.widget.readview.PageWidget;
import com.example.rumens.showtime.widget.readview.ReadView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class ReadActivity extends BaseActivity<IBookReadPresenter> implements IReadBaseView {
    private static final String READ_ACTIVITY = "readactivity";
    @BindView(R.id.flReadWidget)
    FrameLayout mFlReadWidget;
    @BindView(R.id.ivBack)
    ImageView mIvBack;
    @BindView(R.id.tvBookReadTocTitle)
    TextView mTvBookReadTocTitle;
    @BindView(R.id.tvBookReadReading)
    TextView mTvBookReadReading;
    @BindView(R.id.tvBookReadCommunity)
    TextView mTvBookReadCommunity;
    @BindView(R.id.tvBookReadIntroduce)
    TextView mTvBookReadChangeSource;
    @BindView(R.id.tvBookReadSource)
    TextView mTvBookReadSource;
    @BindView(R.id.llBookReadTop)
    LinearLayout mLlBookReadTop;
    @BindView(R.id.tvDownloadProgress)
    TextView mTvDownloadProgress;
    @BindView(R.id.ivBrightnessMinus)
    ImageView mIvBrightnessMinus;
    @BindView(R.id.seekbarLightness)
    SeekBar mSeekbarLightness;
    @BindView(R.id.ivBrightnessPlus)
    ImageView mIvBrightnessPlus;
    @BindView(R.id.tvFontsizeMinus)
    TextView mTvFontsizeMinus;
    @BindView(R.id.seekbarFontSize)
    SeekBar mSeekbarFontSize;
    @BindView(R.id.tvFontsizePlus)
    TextView mTvFontsizePlus;
    @BindView(R.id.cbVolume)
    CheckBox mCbVolume;
    @BindView(R.id.cbAutoBrightness)
    CheckBox mCbAutoBrightness;
    @BindView(R.id.gvTheme)
    GridView mGvTheme;
    @BindView(R.id.rlReadAaSet)
    LinearLayout mRlReadAaSet;
    @BindView(R.id.tvAddMark)
    TextView mTvAddMark;
    @BindView(R.id.tvClear)
    TextView mTvClear;
    @BindView(R.id.lvMark)
    ListView mLvMark;
    @BindView(R.id.rlReadMark)
    LinearLayout mRlReadMark;
    @BindView(R.id.tvBookReadMode)
    TextView mTvBookReadMode;
    @BindView(R.id.tvBookReadSettings)
    TextView mTvBookReadSettings;
    @BindView(R.id.tvBookReadDownload)
    TextView mTvBookReadDownload;
    @BindView(R.id.tvBookMark)
    TextView mTvBookMark;
    @BindView(R.id.tvBookReadToc)
    TextView mTvBookReadToc;
    @BindView(R.id.llBookReadBottom)
    LinearLayout mLlBookReadBottom;
    @BindView(R.id.rlBookReadRoot)
    RelativeLayout mRlBookReadRoot;
    private int statusBarColor;
    /**
     * 是否开始阅读章节
     **/
    private boolean startRead = false;
    private Recommend.RecommendBooks mRecommendBooks;
    private String mBookId;
    private boolean isFromSD;
    private IntentFilter intentFilter = new IntentFilter();
    private List<BookMixATocBean.mixToc.Chapters> mChapterList = new ArrayList<>();
    private View decodeView;
    private int currentChapter = 1;
    private TocListAdapter mTocListAdapter;
    private ListPopupWindow mTocListPopupWindow;
    private int curTheme;
    private ReadView mPageWidget;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private boolean isAutoLightness = false; // 记录其他页面是否自动调整亮度
    private List<ReadTheme> themes;
    private ReadThemeAdapter gvAdapter;
    private Receiver receiver = new Receiver();
    private BookMarkAdapter mMarkAdapter;
    private List<BookMark> mMarkList;

    @Override
    public void loadBookToc(List<BookMixATocBean.mixToc.Chapters> list) {
        mChapterList.clear();
        mChapterList.addAll(list);
        readCurrentChapter();
    }

    @Override
    public void loadChapterRead(ChapterReadBean.Chapter data, int chapter) {
        if (data != null) {
            CacheManager.getInstance().saveChapterFile(mBookId, chapter, data);
        }
        if (!startRead) {
            startRead = true;
            currentChapter = chapter;
            if (!mPageWidget.isPrepared) {
                mPageWidget.init(curTheme);
            } else {
                mPageWidget.jumpToChapter(currentChapter);
            }
        }
    }

    @Override
    protected void updateViews() {
        hideStatusBar();
        decodeView = getWindow().getDecorView();
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlBookReadTop.getLayoutParams();
//        params.topMargin = ScreenUtils.getStatusBarHeight(this) - 2;
//        mLlBookReadTop.setLayoutParams(params);
        initTocList();

        initAASet();

        initPagerWidget();

//        mPresenter.attachView(this);
        // 本地收藏  直接打开
        if (isFromSD) {
            BookMixATocBean.mixToc.Chapters chapters = new BookMixATocBean.mixToc.Chapters();
            chapters.title = mRecommendBooks.title;
            mChapterList.add(chapters);
            //加载章节内容
            loadChapterRead(null, currentChapter);
            //本地书籍隐藏社区、简介、缓存按钮
            gone(mTvBookReadCommunity, mTvBookReadChangeSource, mTvBookReadDownload);
            return;
        }
        mPresenter.getBookMixAToc(mBookId, "chapters");
    }

    private void initTocList() {
        mTocListAdapter = new TocListAdapter(this, mChapterList, mBookId, currentChapter);
        mTocListPopupWindow = new ListPopupWindow(this);
        mTocListPopupWindow.setAdapter(mTocListAdapter);
        mTocListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTocListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mTocListPopupWindow.setAnchorView(mLlBookReadTop);
        mTocListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTocListPopupWindow.dismiss();
                currentChapter = position + 1;
                mTocListAdapter.setCurrentChapter(currentChapter);
                startRead = false;
                readCurrentChapter();
                hideReadBar();
            }
        });
        mTocListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTvBookReadTocTitle.setVisibility(View.GONE);
                mTvBookReadReading.setVisibility(View.VISIBLE);
                mTvBookReadCommunity.setVisibility(View.VISIBLE);
                mTvBookReadChangeSource.setVisibility(View.VISIBLE);
            }
        });
    }



    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    /**
     * 获取当前章节。章节文件存在则直接阅读，不存在则请求
     */
    private void readCurrentChapter() {
        if (CacheManager.getInstance().getChapterFile(mBookId, currentChapter) != null) {
            loadChapterRead(null, currentChapter);
        } else {
            mPresenter.getChapterRead(mChapterList.get(currentChapter - 1).link, currentChapter);
        }
    }

    private void initAASet() {
        curTheme = SettingManager.getInstance().getReadTheme();
        ThemeManager.setReaderTheme(curTheme, mRlBookReadRoot);

        mSeekbarFontSize.setMax(10);
        //int fontSizePx = SettingManager.getInstance().getReadFontSize(bookId);
        int fontSizePx = SettingManager.getInstance().getReadFontSize();
        int progress = (int) ((ScreenUtils.pxToDpInt(fontSizePx) - 12) / 1.7f);
        mSeekbarFontSize.setProgress(progress);
        mSeekbarFontSize.setOnSeekBarChangeListener(new SeekBarChangeListener());

        mSeekbarLightness.setMax(100);
        mSeekbarLightness.setOnSeekBarChangeListener(new SeekBarChangeListener());
        mSeekbarLightness.setProgress(SettingManager.getInstance().getReadBrightness());
        isAutoLightness = ScreenUtils.isAutoBrightness(this);
        if (SettingManager.getInstance().isAutoBrightness()) {
            startAutoLightness();
        } else {
            stopAutoLightness();
        }

        mCbVolume.setChecked(SettingManager.getInstance().isVolumeFlipEnable());
        mCbVolume.setOnCheckedChangeListener(new ChechBoxChangeListener());

        mCbAutoBrightness.setChecked(SettingManager.getInstance().isAutoBrightness());
        mCbAutoBrightness.setOnCheckedChangeListener(new ChechBoxChangeListener());

        gvAdapter = new ReadThemeAdapter(this, (themes = ThemeManager.getReaderThemeData(curTheme)), curTheme);
        mGvTheme.setAdapter(gvAdapter);
        mGvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < themes.size() - 1) {
                    changedMode(false, position);
                } else {
                    changedMode(true, position);
                }
            }
        });
    }

    private void changedMode(boolean isNight, int position) {
        SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, isNight);
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);

        if (position >= 0) {
            curTheme = position;
        } else {
            curTheme = SettingManager.getInstance().getReadTheme();
        }
        gvAdapter.select(curTheme);

        mPageWidget.setTheme(isNight ? ThemeManager.NIGHT : curTheme);
        mPageWidget.setTextColor(ContextCompat.getColor(this, isNight ? R.color.chapter_content_night : R.color.chapter_content_day),
                ContextCompat.getColor(this, isNight ? R.color.chapter_title_night : R.color.chapter_title_day));
        mTvBookReadMode.setText(getString(isNight ? R.string.book_read_mode_day_manual_setting
                : R.string.book_read_mode_night_manual_setting));
        Drawable drawable = ContextCompat.getDrawable(this, isNight ? R.mipmap.ic_menu_mode_day_manual
                : R.mipmap.ic_menu_mode_night_manual);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTvBookReadMode.setCompoundDrawables(null, drawable, null, null);

        ThemeManager.setReaderTheme(curTheme, mRlBookReadRoot);
    }

    private void initPagerWidget() {
        if (SharedPreferencesUtil.getInstance().getInt(Constant.FLIP_STYLE, 0) == 0) {
            mPageWidget = new PageWidget(this, mBookId, mChapterList, new ReadListener());
        } else {
            mPageWidget = new OverlappedWidget(this, mBookId, mChapterList, new ReadListener());
        }
        registerReceiver(receiver, intentFilter);
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
        }
        mFlReadWidget.removeAllViews();
        mFlReadWidget.addView(mPageWidget);
    }

    private void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void initView() {
        isFromSD = mRecommendBooks.isFromSD;
        mBookId = mRecommendBooks._id;
        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            String filePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
            String fileName;
            if (filePath.lastIndexOf(".") > filePath.lastIndexOf("/")) {
                fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
            } else {
                fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            }

            CollectionsManager.getInstance().remove(fileName);
            // 转存
            File desc = FileUtils.createWifiTranfesFile(fileName);
            FileUtils.fileChannelCopy(new File(filePath), desc);
            // 建立
            mRecommendBooks = new Recommend.RecommendBooks();
            mRecommendBooks.isFromSD = true;
            mRecommendBooks._id = fileName;
            mRecommendBooks.title = fileName;
            isFromSD = true;
        }
        String title = mRecommendBooks.title;
        mTvBookReadTocTitle.setText(title);
        EventBus.getDefault().register(this);
//        mTtsPlayer = TTSPlayerUtils.getTTSPlayer();
//        ttsConfig = TTSPlayerUtils.getTtsConfig();

        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        CollectionsManager.getInstance().setRecentReadingTime(mBookId);
    }

    @Override
    protected int getContenView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        statusBarColor = ContextCompat.getColor(this, R.color.reader_menu_bg_color);
         mRecommendBooks= (Recommend.RecommendBooks) getIntent().getSerializableExtra(READ_ACTIVITY);
        return R.layout.activity_read;
    }

    @Override
    protected void initInjector() {
        DaggerReadBookComponent.builder()
                .appComponent(getAppComponent())
                .readBookModule(new ReadBookModule(this))
                .build()
                .inject(this);
    }

    public static void lunch(Context mContext, Recommend.RecommendBooks item) {
        Intent intent = new Intent(mContext, ReadActivity.class);
        intent.putExtra(READ_ACTIVITY,item);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @OnClick({R.id.ivBack,R.id.tvBookReadTocTitle, R.id.tvBookReadReading, R.id.tvBookReadCommunity, R.id.tvBookReadIntroduce, R.id.tvBookReadSource, R.id.tvDownloadProgress, R.id.tvFontsizeMinus, R.id.tvAddMark, R.id.tvClear, R.id.tvBookReadMode, R.id.tvBookReadSettings, R.id.tvBookReadDownload, R.id.tvBookMark, R.id.tvBookReadToc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                if (mTocListPopupWindow.isShowing()) {
                    mTocListPopupWindow.dismiss();
                } else {
                    finish();
                }
                break;
            case R.id.tvBookReadReading:
                gone(mRlReadAaSet, mRlReadMark);
                ToastUtils.showToast("正在拼命开发中...");
                break;
            case R.id.tvBookReadCommunity:
                gone(mRlReadAaSet, mRlReadMark);
//                BookDetailCommunityActivity.startActivity(this, bookId, mTvBookReadTocTitle.getText().toString(), 0);
                break;
            case R.id.tvBookReadIntroduce:
                gone(mRlReadAaSet, mRlReadMark);
//                BookDetailActivity.startActivity(mContext, bookId);
                break;
            case R.id.tvBookReadSource:
//                BookSourceActivity.start(this, bookId, 1);
                break;
            case R.id.tvDownloadProgress:
                break;
            case R.id.tvFontsizeMinus:
                calcFontSize(mSeekbarFontSize.getProgress() - 1);
                break;
            case R.id.tvFontsizePlus:
                calcFontSize(mSeekbarFontSize.getProgress() + 1);
                break;
            case R.id.tvAddMark:
                addBookMark();
                break;
            case R.id.tvClear:
                SettingManager.getInstance().clearBookMarks(mBookId);
                updateMark();
                break;
            case R.id.tvBookReadMode:
                gone(mRlReadAaSet, mRlReadMark);
                boolean isNight = !SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false);
                changedMode(isNight, -1);
                break;
            case R.id.tvBookReadSettings:
                if (isVisible(mLlBookReadBottom)) {
                    if (isVisible(mRlReadAaSet)) {
                        gone(mRlReadAaSet);
                    } else {
                        visible(mRlReadAaSet);
                        gone(mRlReadMark);
                    }
                }
                break;
            case R.id.tvBookReadDownload:
                bookDownload();
                break;
            case R.id.tvBookMark:
                if (isVisible(mLlBookReadBottom)) {
                    if (isVisible(mRlReadMark)) {
                        gone(mRlReadMark);
                    } else {
                        gone(mRlReadAaSet);

                        updateMark();

                        visible(mRlReadMark);
                    }
                }
                break;
            case R.id.tvBookReadToc:
                gone(mRlReadAaSet, mRlReadMark);
                if (!mTocListPopupWindow.isShowing()) {
                    visible(mTvBookReadTocTitle);
                    gone(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadChangeSource);
                    mTocListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    mTocListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mTocListPopupWindow.show();
                    mTocListPopupWindow.setSelection(currentChapter - 1);
                    mTocListPopupWindow.getListView().setFastScrollEnabled(true);
                }
                break;
            case R.id.ivBrightnessMinus:
                int curBrightness = SettingManager.getInstance().getReadBrightness();
                if (curBrightness > 2 && !SettingManager.getInstance().isAutoBrightness()) {
                    mSeekbarLightness.setProgress((curBrightness = curBrightness - 2));
                    ScreenUtils.setScreenBrightness(curBrightness, ReadActivity.this);
                    SettingManager.getInstance().saveReadBrightness(curBrightness);
                }
                break;
            case R.id.ivBrightnessPlus:
                int pCurBrightness = SettingManager.getInstance().getReadBrightness();
                if (pCurBrightness < 99 && !SettingManager.getInstance().isAutoBrightness()) {
                    mSeekbarLightness.setProgress((pCurBrightness = pCurBrightness + 2));
                    ScreenUtils.setScreenBrightness(pCurBrightness, ReadActivity.this);
                    SettingManager.getInstance().saveReadBrightness(pCurBrightness);
                }
                break;
        }
    }

    private void addBookMark() {
        int[] readPos = mPageWidget.getReadPos();
        BookMark mark = new BookMark();
        mark.chapter = readPos[0];
        mark.startPos = readPos[1];
        mark.endPos = readPos[2];
        if (mark.chapter >= 1 && mark.chapter <= mChapterList.size()) {
            mark.title = mChapterList.get(mark.chapter - 1).title;
        }
        mark.desc = mPageWidget.getHeadLine();
        if (SettingManager.getInstance().addBookMark(mBookId, mark)) {
            ToastUtils.showSingleToast("添加书签成功");
            updateMark();
        } else {
            ToastUtils.showSingleToast("书签已存在");
        }
    }

    private void updateMark() {
        if (mMarkAdapter == null) {
            mMarkAdapter = new BookMarkAdapter(this, new ArrayList<BookMark>());
            mLvMark.setAdapter(mMarkAdapter);
            mLvMark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BookMark mark = mMarkAdapter.getData(position);
                    if (mark != null) {
                        mPageWidget.setPosition(new int[]{mark.chapter, mark.startPos, mark.endPos});
                        hideReadBar();
                    } else {
                        ToastUtils.showSingleToast("书签无效");
                    }
                }
            });
        }
        mMarkAdapter.clear();

        mMarkList = SettingManager.getInstance().getBookMarks(mBookId);
        if (mMarkList != null && mMarkList.size() > 0) {
            Collections.reverse(mMarkList);
            mMarkAdapter.addAll(mMarkList);
        }
    }

    private void bookDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("缓存多少章？")
                .setItems(new String[]{"后面五十章", "后面全部", "全部"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                DownloadBookService.post(new DownloadQueue(mBookId, mChapterList, currentChapter + 1, currentChapter + 50));
                                break;
                            case 1:
                                DownloadBookService.post(new DownloadQueue(mBookId, mChapterList, currentChapter + 1, mChapterList.size()));
                                break;
                            case 2:
                                DownloadBookService.post(new DownloadQueue(mBookId, mChapterList, 1, mChapterList.size()));
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }

    /***************Event*****************/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDownProgress(DownloadProgress progress) {
        if (mBookId.equals(progress.bookId)) {
            if (isVisible(mLlBookReadBottom)) { // 如果工具栏显示，则进度条也显示
                visible(mTvDownloadProgress);
                // 如果之前缓存过，就给提示
                mTvDownloadProgress.setText(progress.message);
            } else {
                gone(mTvDownloadProgress);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadMessage(final DownloadMessage msg) {
        if (isVisible(mLlBookReadBottom)) { // 如果工具栏显示，则进度条也显示
            if (mBookId.equals(msg.bookId)) {
                visible(mTvDownloadProgress);
                mTvDownloadProgress.setText(msg.message);
                if (msg.isComplete) {
                    mTvDownloadProgress.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gone(mTvDownloadProgress);
                        }
                    }, 2500);
                }
            }
        }
    }

    /**
     * 显示加入书架对话框
     *
     * @param bean
     */
    private void showJoinBookShelfDialog(final Recommend.RecommendBooks bean) {
        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.book_read_add_book))
                .setMessage(getString(R.string.book_read_would_you_like_to_add_this_to_the_book_shelf))
                .setPositiveButton(getString(R.string.book_read_join_the_book_shelf), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        bean.recentReadingTime = FormatUtils.getCurrentTimeString(FormatUtils.FORMAT_DATE_TIME);
                        CollectionsManager.getInstance().add(bean);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.book_read_not), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                if(resultCode == RESULT_OK) {
//                    BookSource bookSource = (BookSource) data.getSerializableExtra("source");
//                    bookId = bookSource._id;
//                }
//                //mPresenter.getBookMixAToc(bookId, "chapters");
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mTocListPopupWindow != null && mTocListPopupWindow.isShowing()) {
                    mTocListPopupWindow.dismiss();
                    gone(mTvBookReadTocTitle);
                    visible(mTvBookReadReading, mTvBookReadCommunity, mTvBookReadChangeSource);
                    return true;
                } else if (isVisible(mRlReadAaSet)) {
                    gone(mRlReadAaSet);
                    return true;
                } else if (isVisible(mLlBookReadBottom)) {
                    hideReadBar();
                    return true;
                } else if (!CollectionsManager.getInstance().isCollected(mBookId)) {
                    showJoinBookShelfDialog(mRecommendBooks);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                toggleReadBar();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (SettingManager.getInstance().isVolumeFlipEnable()) {
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (SettingManager.getInstance().isVolumeFlipEnable()) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                mPageWidget.nextPage();
                return true;// 防止翻页有声音
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (SettingManager.getInstance().isVolumeFlipEnable()) {
                mPageWidget.prePage();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PLAYING)
//            mTtsPlayer.stop();

        EventManager.refreshCollectionIcon();
        EventManager.refreshCollectionList();
        EventBus.getDefault().unregister(this);

        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isAutoLightness) {
            ScreenUtils.startAutoBrightness(ReadActivity.this);
        } else {
            ScreenUtils.stopAutoBrightness(ReadActivity.this);
        }
    }



    private boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            currentChapter = chapter;
            mTocListAdapter.setCurrentChapter(currentChapter);
            // 加载前一节 与 后三节
            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
                if (i > 0 && i != chapter
                        && CacheManager.getInstance().getChapterFile(mBookId, i) == null) {
                    mPresenter.getChapterRead(mChapterList.get(i - 1).link, i);
                }
            }
        }

        @Override
        public void onPageChanged(int chapter, int page) {

        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            startRead = false;
            if (CacheManager.getInstance().getChapterFile(mBookId, chapter) == null)
                mPresenter.getChapterRead(mChapterList.get(chapter - 1).link, chapter);
        }

        @Override
        public void onCenterClick() {
            toggleReadBar();
        }

        @Override
        public void onFlip() {
            hideReadBar();
        }
    }

    private synchronized void toggleReadBar() {
        if (mLlBookReadTop.getVisibility() == View.VISIBLE) {
            hideReadBar();
        } else {
            showReadBar();
        }
    }
    private synchronized void hideReadBar() {
        gone(mTvDownloadProgress, mLlBookReadBottom, mLlBookReadTop, mRlReadAaSet, mRlReadMark);
        hideStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }
    private synchronized void showReadBar() {
        visible(mLlBookReadBottom, mLlBookReadTop);
//        showStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        View statusBarView = StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.chapter_title_night));
//        statusBarView.setBackgroundColor(Color.RED);
    }

    private void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar.getId() == mSeekbarFontSize.getId() && fromUser) {
                calcFontSize(progress);
            } else if (seekBar.getId() == mSeekbarLightness.getId() && fromUser
                    && !SettingManager.getInstance().isAutoBrightness()) { // 非自动调节模式下 才可调整屏幕亮度
                ScreenUtils.setScreenBrightness(progress, ReadActivity.this);
                SettingManager.getInstance().saveReadBrightness(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class ChechBoxChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == mCbVolume.getId()) {
                SettingManager.getInstance().saveVolumeFlipEnable(isChecked);
            } else if (buttonView.getId() == mCbAutoBrightness.getId()) {
                if (isChecked) {
                    startAutoLightness();
                } else {
                    stopAutoLightness();
                }
            }
        }
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mPageWidget != null) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra("level", 0);
                    mPageWidget.setBattery(100 - level);
                } else if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                    mPageWidget.setTime(sdf.format(new Date()));
                }
            }
        }
    }

    private void startAutoLightness() {
        SettingManager.getInstance().saveAutoBrightness(true);
        ScreenUtils.startAutoBrightness(ReadActivity.this);
        mSeekbarLightness.setEnabled(false);
    }

    private void stopAutoLightness() {
        SettingManager.getInstance().saveAutoBrightness(false);
        ScreenUtils.stopAutoBrightness(ReadActivity.this);
        int value = SettingManager.getInstance().getReadBrightness();
        mSeekbarLightness.setProgress(value);
        ScreenUtils.setScreenBrightness(value, ReadActivity.this);
        mSeekbarLightness.setEnabled(true);
    }

    private void calcFontSize(int progress) {
        // progress range 1 - 10
        if (progress >= 0 && progress <= 10) {
            mSeekbarFontSize.setProgress(progress);
            mPageWidget.setFontSize(ScreenUtils.dpToPxInt(12 + 1.7f * progress));
        }
    }
}
