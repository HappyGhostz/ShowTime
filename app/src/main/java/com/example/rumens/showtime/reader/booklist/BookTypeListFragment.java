package com.example.rumens.showtime.reader.booklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.BookHelpList;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.base.IBookListBaseView;
import com.example.rumens.showtime.inject.component.DaggerBookTypeListComponent;
import com.example.rumens.showtime.inject.modules.BookTypeListModule;
import com.example.rumens.showtime.utils.CollectionsManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookTypeListFragment extends BaseFragment<IBasePresenter> implements IBookListBaseView {
    private static final String BOOK_LIST_TYPE = "booklisttype";
    @BindView(R.id.book_recyclerview)
    RecyclerView mRecyclerview;
    @Inject
    BaseQuickAdapter mRecommendAdapter;

    private String mBookListType;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_book_list;
    }

    @Override
    protected void initInjector() {
        DaggerBookTypeListComponent.builder()
                .appComponent(getAppComponent())
                .bookTypeListModule(new BookTypeListModule(this, mBookListType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerViewV(mContext,mRecyclerview,true,mRecommendAdapter );
        mRecommendAdapter.setRequestDataListener(new OnRequestDataListener() {
                @Override
                public void onLoadMore() {
                    mPresenter.getMoreData();
                }
        });
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    public static Fragment lunch(String bookType) {
        BookTypeListFragment fragment = new BookTypeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_LIST_TYPE, bookType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void loadRecommendList(List<Recommend.RecommendBooks> list) {
        mRecommendAdapter.updateItems(list);
        //推荐列表默认加入收藏
        for (Recommend.RecommendBooks bean : list) {
            //TODO 此处可优化：批量加入收藏->加入前需先判断是否收藏过
            CollectionsManager.getInstance().add(bean);
        }
    }

    @Override
    public void loadCommunityList(List<BookHelpList.HelpsBean> list) {
        mRecommendAdapter.updateItems(list);
    }

    @Override
    public void loadMoreCommunity(List<BookHelpList.HelpsBean> list) {
        mRecommendAdapter.loadComplete();
        mRecommendAdapter.addItems(list);
    }

    @Override
    public void loadNoData() {
        mRecommendAdapter.loadAbnormal();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mBookListType = getArguments().getString(BOOK_LIST_TYPE);
        super.onCreate(savedInstanceState);
    }
}
