package com.example.rumens.showtime.reader.bookdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.GlideRoundTransform;
import com.example.rumens.showtime.adapter.HotReviewAdapter;
import com.example.rumens.showtime.adapter.RecommendBookListAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.BookDetail;
import com.example.rumens.showtime.api.bean.HotReview;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.api.bean.RecommendBookList;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerBookDetailListComponent;
import com.example.rumens.showtime.inject.modules.BookDetialListModule;
import com.example.rumens.showtime.reader.bookread.ReadActivity;
import com.example.rumens.showtime.rxBus.event.RefreshCollectionIconEvent;
import com.example.rumens.showtime.utils.CollectionsManager;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ToastUtils;
import com.example.rumens.showtime.widget.DrawableCenterButton;
import com.example.rumens.showtime.widget.TagColor;
import com.example.rumens.showtime.widget.TagGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class BookDetailActivity extends BaseActivity<IBasePresenter> implements BookDetailBaseView {
    private static final String BOOK_DETAIL_ID = "bookid";
    @BindView(R.id.common_toolbar)
    Toolbar mCommonToolbar;
    @BindView(R.id.ivBookCover)
    ImageView mIvBookCover;
    @BindView(R.id.tvBookListTitle)
    TextView mTvBookListTitle;
    @BindView(R.id.tvBookListAuthor)
    TextView mTvBookListAuthor;
    @BindView(R.id.tvCatgory)
    TextView mTvCatgory;
    @BindView(R.id.tvWordCount)
    TextView mTvWordCount;
    @BindView(R.id.tvLatelyUpdate)
    TextView mTvLatelyUpdate;
    @BindView(R.id.btnJoinCollection)
    DrawableCenterButton mBtnJoinCollection;
    @BindView(R.id.btnRead)
    DrawableCenterButton mBtnRead;
    @BindView(R.id.tvLatelyFollower)
    TextView mTvLatelyFollower;
    @BindView(R.id.tvRetentionRatio)
    TextView mTvRetentionRatio;
    @BindView(R.id.tvSerializeWordCount)
    TextView mTvSerializeWordCount;
    @BindView(R.id.tag_group)
    TagGroup mTagGroup;
    @BindView(R.id.tvlongIntro)
    TextView mTvlongIntro;
    @BindView(R.id.tvMoreReview)
    TextView mTvMoreReview;
    @BindView(R.id.rvHotReview)
    RecyclerView mRvHotReview;
    @BindView(R.id.tvCommunity)
    TextView mTvCommunity;
    @BindView(R.id.tvHelpfulYes)
    TextView mTvHelpfulYes;
    @BindView(R.id.rlCommunity)
    RelativeLayout mRlCommunity;
    @BindView(R.id.tvRecommendBookList)
    TextView mTvRecommendBookList;
    @BindView(R.id.rvRecommendBoookList)
    RecyclerView mRvRecommendBoookList;
    private String mBookId;

    private List<String> tagList = new ArrayList<>();
    private int times = 0;

    private HotReviewAdapter mHotReviewAdapter;
    private List<HotReview.Reviews> mHotReviewList = new ArrayList<>();
    private RecommendBookListAdapter mRecommendBookListAdapter;
    private List<RecommendBookList.RecommendBook> mRecommendBookList = new ArrayList<>();
    private Recommend.RecommendBooks recommendBooks;
    private boolean isJoinedCollections = false;

    public static void lunch(Context mContext, String id) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        intent.putExtra(BOOK_DETAIL_ID, id);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public void loadBookDetail(final BookDetail data) {
        mTvBookListTitle.setText(data.title);
        mTvBookListAuthor.setText(String.format(getString(R.string.book_detail_author), data.author));
        mTvCatgory.setText(String.format(getString(R.string.book_detail_category), data.cat));
        mTvWordCount.setText(FormatUtils.formatWordCount(data.wordCount));
        mTvLatelyUpdate.setText(FormatUtils.getDescriptionTimeFromDateString(data.updated));
        mTvLatelyFollower.setText(String.valueOf(data.latelyFollower));
        mTvRetentionRatio.setText(TextUtils.isEmpty(data.retentionRatio) ?
                "-" : String.format(getString(R.string.book_detail_retention_ratio),
                data.retentionRatio));
        mTvSerializeWordCount.setText(data.serializeWordCount < 0 ? "-" :
                String.valueOf(data.serializeWordCount));

        tagList.clear();
        tagList.addAll(data.tags);
        times = 0;
        showHotWord();

        mTvlongIntro.setText(data.longIntro);
        mTvCommunity.setText(String.format(getString(R.string.book_detail_community), data.title));
        mTvHelpfulYes.setText(String.format(getString(R.string.book_detail_post_count), data.postCount));

        recommendBooks = new Recommend.RecommendBooks();
        recommendBooks.title = data.title;
        recommendBooks._id = data._id;
        recommendBooks.cover = data.cover;
        recommendBooks.lastChapter = data.lastChapter;
        recommendBooks.updated = data.updated;
        refreshCollectionIcon();
        Glide.with(mContext)
                .load(Constant.IMG_BASE_URL + data.cover)
                .placeholder(R.drawable.cover_default)
                .transform(new GlideRoundTransform(mContext))
                .into(mIvBookCover);

    }
    /**
     * 刷新收藏图标
     */
    private void refreshCollectionIcon() {
        if (CollectionsManager.getInstance().isCollected(recommendBooks._id)) {
            initCollection(false);
        } else {
            initCollection(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshCollectionIcon(RefreshCollectionIconEvent event) {
        refreshCollectionIcon();
    }


    private void showHotWord() {
        int start, end;
        if (times < tagList.size() && times + 8 <= tagList.size()) {
            start = times;
            end = times + 8;
        } else if (times < tagList.size() - 1 && times + 8 > tagList.size()) {
            start = times;
            end = tagList.size() - 1;
        } else {
            start = 0;
            end = tagList.size() > 8 ? 8 : tagList.size();
        }
        times = end;
        if (end - start > 0) {
            List<String> batch = tagList.subList(start, end);
            List<TagColor> colors = TagColor.getRandomColors(batch.size());
            mTagGroup.setTags(colors, (String[]) batch.toArray(new String[batch.size()]));
        }
    }

    @Override
    public void loadHotReview(List<HotReview.Reviews> list) {
        mHotReviewAdapter.updateItems(list);
    }

    @Override
    public void loadRecommendBookList(List<RecommendBookList.RecommendBook> list) {
        mRecommendBookListAdapter.updateItems(list);
    }

    @Override
    protected void updateViews() {
        mHotReviewAdapter = new HotReviewAdapter(this);
        mRecommendBookListAdapter = new RecommendBookListAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(mContext, mRvHotReview, mHotReviewAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext, mRvRecommendBoookList, mRecommendBookListAdapter);
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        initToolBar(mCommonToolbar, true, "书籍详情");
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initInjector() {
        mBookId = getIntent().getStringExtra(BOOK_DETAIL_ID);
        DaggerBookDetailListComponent.builder()
                .appComponent(getAppComponent())
                .bookDetialListModule(new BookDetialListModule(this, mBookId))
                .build()
                .inject(this);
    }

    @OnClick({R.id.btnJoinCollection, R.id.btnRead})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnJoinCollection:
                if (!isJoinedCollections) {
                    if (recommendBooks != null) {
                        CollectionsManager.getInstance().add(recommendBooks);
                        ToastUtils.showToast(String.format(getString(
                                R.string.book_detail_has_joined_the_book_shelf), recommendBooks.title));
                        initCollection(false);
                    }
                } else {
                    CollectionsManager.getInstance().remove(recommendBooks._id);
                    ToastUtils.showToast(String.format(getString(
                            R.string.book_detail_has_remove_the_book_shelf), recommendBooks.title));
                    initCollection(true);
                }
                break;
            case R.id.btnRead:
                if (recommendBooks == null) return;
                ReadActivity.lunch(this, recommendBooks);
                break;
        }
    }

    private void initCollection(boolean coll) {
        if (coll) {
            mBtnJoinCollection.setText(R.string.book_detail_join_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.book_detail_info_add_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBtnJoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shape_common_btn_solid_normal));
            mBtnJoinCollection.setCompoundDrawables(drawable, null, null, null);
            mBtnJoinCollection.postInvalidate();
            isJoinedCollections = false;
        } else {
            mBtnJoinCollection.setText(R.string.book_detail_remove_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.book_detail_info_del_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBtnJoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.btn_join_collection_pressed));
            mBtnJoinCollection.setCompoundDrawables(drawable, null, null, null);
            mBtnJoinCollection.postInvalidate();
            isJoinedCollections = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static void lunchForNewTask(Context mContext, String id) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        intent.putExtra(BOOK_DETAIL_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
//        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
//        this.overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
    }
}
