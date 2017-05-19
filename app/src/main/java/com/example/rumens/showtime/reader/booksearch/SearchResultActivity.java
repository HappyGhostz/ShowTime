package com.example.rumens.showtime.reader.booksearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.SearchDetail;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerSeachBookListComponent;
import com.example.rumens.showtime.inject.modules.SeachBookListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */

public class SearchResultActivity extends BaseActivity<IBasePresenter> implements ISearchBaseView {
    private static final String SEARCH_BOOK_RESULT = "search";
    @BindView(R.id.tool_search_bar)
    Toolbar mToolSearchBar;
    @BindView(R.id.rv_search_list)
    RecyclerView mRvSearchList;
    private String mBookName;
    @Inject
    BaseQuickAdapter mAdapter;

    @Override
    public void loadSearchResultList(List<SearchDetail.SearchBooks> list) {
         mAdapter.updateItems(list);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        initToolBar(mToolSearchBar,true,"搜索详情");
        RecyclerViewHelper.initRecyclerViewV(this,mRvSearchList,true,mAdapter);

    }

    @Override
    protected int getContenView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initInjector() {
        mBookName = getIntent().getStringExtra(SEARCH_BOOK_RESULT);
        DaggerSeachBookListComponent.builder()
                .appComponent(getAppComponent())
                .seachBookListModule(new SeachBookListModule(this, mBookName))
                .build()
                .inject(this);
    }

    public static void lunch(Context mContext, String bookName) {
        Intent intent = new Intent(mContext, SearchResultActivity.class);
        intent.putExtra(SEARCH_BOOK_RESULT, bookName);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
    }

}
