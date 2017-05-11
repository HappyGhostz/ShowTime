package com.example.rumens.showtime.reader.bookclassify;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.api.bean.CategoryList;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */

public class BookClassifyListFragment extends BaseFragment<IBasePresenter> implements IBookClassifyBaseView{
    private static final String BOOK_CLASSIFY_LIST = "bookclassifylist";

    @Override
    public void showCategoryList(CategoryList data) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }

    public static Fragment lunch(String mBookType) {
        BookClassifyListFragment fragment = new BookClassifyListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_CLASSIFY_LIST,mBookType);
        fragment.setArguments(bundle);
        return fragment;
    }
}
