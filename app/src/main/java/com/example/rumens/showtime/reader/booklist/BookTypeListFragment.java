package com.example.rumens.showtime.reader.booklist;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.base.IBookListBaseView;
import com.example.rumens.showtime.inject.component.DaggerBookTypeListComponent;
import com.example.rumens.showtime.inject.modules.BookTypeListModule;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookTypeListFragment extends BaseFragment<IBasePresenter> implements IBookListBaseView {
    private static final String BOOK_LIST_TYPE = "booklisttype";
    private String mBookListType;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_book_list;
    }

    @Override
    protected void initInjector() {
        mBookListType = getArguments().getString(BOOK_LIST_TYPE);
        DaggerBookTypeListComponent.builder()
                .appComponent(getAppComponent())
                .bookTypeListModule(new BookTypeListModule(this, mBookListType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }

    public static Fragment lunch(String bookType) {
        BookTypeListFragment fragment = new BookTypeListFragment();
        Bundle  bundle = new Bundle();
        bundle.putString(BOOK_LIST_TYPE,bookType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showRecommendList(List<Recommend.RecommendBooks> list) {

    }
}
