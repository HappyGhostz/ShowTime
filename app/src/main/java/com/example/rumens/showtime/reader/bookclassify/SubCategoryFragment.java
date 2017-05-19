package com.example.rumens.showtime.reader.bookclassify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerSubCategoryListComponent;
import com.example.rumens.showtime.inject.modules.SubCategoryListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class SubCategoryFragment extends BaseFragment<IBasePresenter> implements ICategoryBaseView {
    private static final String SCBCATEGORY_FRAGMENT_SEX = "subcategoryfragment";
    private static final String SCBCATEGORY_FRAGMENT_MAJOR = "major";
    private static final String SCBCATEGORY_FRAGMENT_TYPE = "type";
    @BindView(R.id.rv_category_list)
    RecyclerView mRvCategoryList;
    @Inject
    BaseQuickAdapter mAdapter;
    private String mSex;
    private String mMajor;
    private String mType;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_category_list;
    }

    @Override
    protected void initInjector() {
        Bundle arguments = getArguments();
        mMajor = arguments.getString(SCBCATEGORY_FRAGMENT_MAJOR);
        mSex = arguments.getString(SCBCATEGORY_FRAGMENT_SEX);
        mType = arguments.getString(SCBCATEGORY_FRAGMENT_TYPE);
        DaggerSubCategoryListComponent.builder()
                .appComponent(getAppComponent())
                .subCategoryListModule(new SubCategoryListModule(this,mSex,mMajor,mType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        RecyclerViewHelper.initRecyclerViewV(mContext,mRvCategoryList,true,mAdapter);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
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

    public static Fragment lunch(String major, String sex, String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SCBCATEGORY_FRAGMENT_SEX, sex);
        bundle.putString(SCBCATEGORY_FRAGMENT_MAJOR, major);
        bundle.putString(SCBCATEGORY_FRAGMENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void loadCategoryList(BooksByCats data) {
        List<BooksByCats.BooksBean> books = data.books;
        mAdapter.updateItems(books);
    }

    @Override
    public void loadMoreCategoryList(BooksByCats data) {
        List<BooksByCats.BooksBean> books = data.books;
        mAdapter.loadComplete();
        mAdapter.addItems(books);

    }
}
