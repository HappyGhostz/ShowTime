package com.example.rumens.showtime.reader.bookclassify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/15
 * @description
 */

public class SubCategoryListActivity extends BaseActivity  {
    private static final String CATEGORY_LIST_DATA = "categorylistdata";
    private static final String CATEGORY_LIST_DATA_TYPE = "categorylisttupe";
    @BindView(R.id.tool_category_bar)
    Toolbar mToolCategoryBar;
    @BindView(R.id.tab_category_layout)
    TabLayout mTabCategoryLayout;
    @BindView(R.id.category_vp)
    ViewPager mCategoryVp;
    private String mCategoryType;
    private ViewPagerAdapter mViewPagerAdapter;
    private String mCategoryName;

    public static void startActivity(Context mContext, String name, String male) {
        Intent intent = new Intent(mContext, SubCategoryListActivity.class);
        intent.putExtra(CATEGORY_LIST_DATA, name);
        intent.putExtra(CATEGORY_LIST_DATA_TYPE, male);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {
        mCategoryType = getIntent().getStringExtra(CATEGORY_LIST_DATA_TYPE);
        mCategoryName = getIntent().getStringExtra(CATEGORY_LIST_DATA);
        initToolBar(mToolCategoryBar,true,mCategoryName);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        initToolayoutAndViewPager();

    }

    private void initToolayoutAndViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("新书");
        titles.add("热门");
        titles.add("口碑");
        titles.add("完结");
        List<Fragment>fragments = new ArrayList<>();
        fragments.add(SubCategoryFragment.lunch(mCategoryName,mCategoryType,"new"));
        fragments.add(SubCategoryFragment.lunch(mCategoryName,mCategoryType,"hot"));
        fragments.add(SubCategoryFragment.lunch(mCategoryName,mCategoryType,"reputation"));
        fragments.add(SubCategoryFragment.lunch(mCategoryName,mCategoryType,"over"));
        mViewPagerAdapter.setItems(fragments,titles);
        mCategoryVp.setAdapter(mViewPagerAdapter);
        mTabCategoryLayout.setupWithViewPager(mCategoryVp);

    }

    @Override
    protected int getContenView() {
        return R.layout.activity_subcategory;
    }

    @Override
    protected void initInjector() {

    }

}
