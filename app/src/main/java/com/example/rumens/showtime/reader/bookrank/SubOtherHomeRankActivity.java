package com.example.rumens.showtime.reader.bookrank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerSubOtherRankListComponent;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/19
 * @description
 */

public class SubOtherHomeRankActivity extends BaseActivity<IBasePresenter> implements IBaseSubOtherView {
    private static final String SUB_OTHER_RANK_ID = "id";
    private static final String SUB_OTHER_RANK_TITLE = "title";
    @BindView(R.id.tool_bar_sub)
    Toolbar mToolBarSub;
    @BindView(R.id.rv_list_sub)
    RecyclerView mRvListSub;
    private String mBookId;
    private String mTypeTitle;
    @Inject
    BaseQuickAdapter mAdapter;

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        initToolBar(mToolBarSub,true,mTypeTitle);
        RecyclerViewHelper.initRecyclerViewV(this,mRvListSub,true,mAdapter);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_sub_other_rank;
    }

    @Override
    protected void initInjector() {
        mBookId = getIntent().getStringExtra(SUB_OTHER_RANK_ID);
        mTypeTitle = getIntent().getStringExtra(SUB_OTHER_RANK_TITLE);
        DaggerSubOtherRankListComponent.builder()
                .appComponent(getAppComponent())
                .subOtherRankListModule(new SubOtherRankListModule(this, mBookId))
                .build()
                .inject(this);
    }

    public static void startActivity(Context mContext, String id, String title) {
        Intent intent = new Intent(mContext, SubOtherHomeRankActivity.class);
        intent.putExtra(SUB_OTHER_RANK_ID, id);
        intent.putExtra(SUB_OTHER_RANK_TITLE, title);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
    }

    @Override
    public void loadRankList(BooksByCats data) {
        mAdapter.updateItems(data.books);
    }

}
