package com.example.rumens.showtime.reader.bookhelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.BestCommentListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.BookHelp;
import com.example.rumens.showtime.api.bean.CommentList;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerBookHelpDetialComponent;
import com.example.rumens.showtime.inject.modules.BookHelpDetialModule;
import com.example.rumens.showtime.utils.Constant;
import com.example.rumens.showtime.utils.FormatUtils;
import com.example.rumens.showtime.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public class BookHelpDetailActivity extends BaseActivity<IBasePresenter> implements IBookHelpBaseView {
    private static final String BOOK_HELP_DETIAL_ID = "id";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_news_list)
    RecyclerView mRvBookHelpList;
    private String mHelpBeanId;
    @Inject
    BaseQuickAdapter mAdapter;
    private TextView mTvNickName;
    private CircleImageView mCircleCorve;
    private TextView mTvTime;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvCommentCount;
    private TextView mTvBaseComment;
    private RecyclerView mRvBestComments;
    private List<CommentList.CommentsBean> mBestCommentList = new ArrayList<>();
    private BestCommentListAdapter mBestCommentListAdapter;
    private LayoutInflater from;

    @Override
    public void loadBookHelpDetail(BookHelp data) {
        initHeadData(data);
    }

    private void initHeadData(BookHelp data) {
        mTvNickName.setText(data.help.author.nickname);
        mTvTime.setText(FormatUtils.getDescriptionTimeFromDateString(data.help.created));
        mTvTitle.setText(data.help.title);
        mTvContent.setText(data.help.content);
        mTvCommentCount.setText(String.format(this.getString(R.string.comment_comment_count), data.help.commentCount));
        ImageLoader.loadCenterCrop(mContext,Constant.IMG_BASE_URL + data.help.author.avatar,mCircleCorve,R.mipmap.avatar_default);
    }

    private void initHeadView(View view) {
        mTvNickName = (TextView) view.findViewById(R.id.tvBookTitle);
        mCircleCorve = (CircleImageView) view.findViewById(R.id.ivBookCover);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitle);
        mTvContent = (TextView) view.findViewById(R.id.tvContent);
        mTvCommentCount = (TextView) view.findViewById(R.id.tvCommentCount);
        mTvBaseComment = (TextView) view.findViewById(R.id.tvBestComments);
        mRvBestComments = (RecyclerView) view.findViewById(R.id.rvBestComments);
    }

    @Override
    public void loadBestComments(CommentList list) {
        if (list.comments.isEmpty()) {
                gone(mToolbar,mRvBestComments);
        } else {
            mBestCommentList.addAll(list.comments);
            mBestCommentListAdapter = new BestCommentListAdapter(this,mBestCommentList);
           RecyclerViewHelper.initRecyclerViewV(mContext,mRvBestComments,mBestCommentListAdapter);
            visible(mTvBaseComment, mRvBestComments);
        }
    }

    @Override
    public void loadBookHelpComments(CommentList list) {
        mAdapter.updateItems(list.comments);
    }

    @Override
    public void loadMoreBookHelpComments(CommentList list) {
        mAdapter.loadComplete();
        mAdapter.addItems(list.comments);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        View view = View.inflate(this,R.layout.item_book_help_detial_head, null);
//        View view = from.inflate(R.layout.item_book_help_detial_head, null);
        initHeadView(view);
        initToolBar(mToolbar,true,"书荒互助区详情");
        mToolbar.setNavigationIcon(R.mipmap.ab_back);
        mAdapter.addHeaderView(view);
        RecyclerViewHelper.initRecyclerViewV(mContext,mRvBookHelpList,mAdapter);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_book_help_detial;
    }

    @Override
    protected void initInjector() {
        mHelpBeanId = getIntent().getStringExtra(BOOK_HELP_DETIAL_ID);
        DaggerBookHelpDetialComponent.builder()
                .appComponent(getAppComponent())
                .bookHelpDetialModule(new BookHelpDetialModule(this, mHelpBeanId))
                .build()
                .inject(this);
    }

    public static void lunch(Context mContext, String id) {
        Intent intent = new Intent(mContext, BookHelpDetailActivity.class);
        intent.putExtra(BOOK_HELP_DETIAL_ID, id);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }
}
