package com.example.rumens.showtime.reader;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.reader.booklist.BookTypeListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/4/18
 * @description
 */

public class ReaderFragment extends BaseFragment {

    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.bt_search)
    ImageButton mBtSearch;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_new_layout)
    TabLayout mTabNewLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private List<String> titles;
    private List<Fragment> fragments;
    private ViewPagerAdapter pagerAdapter;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_reader_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar, true, "小说");
        setHasOptionsMenu(true);
        pagerAdapter = new ViewPagerAdapter(getFragmentManager());


    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("书架");
        titles.add("分类");
        titles.add("社区");
        titles.add("排行榜");
        fragments = new ArrayList<>();
        fragments.add(BookTypeListFragment.lunch("书架"));
        fragments.add(BookTypeListFragment.lunch("分类"));
        fragments.add(BookTypeListFragment.lunch("社区"));
        fragments.add(BookTypeListFragment.lunch("排行榜"));

    }

    @Override
    protected void updateViews() {
        initData();
        pagerAdapter.setItems(fragments,titles);
        mViewpager.setAdapter(pagerAdapter);
        mTabNewLayout.setupWithViewPager(mViewpager);
    }
}
