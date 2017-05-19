package com.example.rumens.showtime.reader.bookrank;

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
 * @create 2017/5/17
 * @description
 */

public class SubRankActivity extends BaseActivity {
    private static final String SUB_RANK_ID = "id";
    private static final String SUB_RANK_MONTH = "monthrank";
    private static final String SUB_RANK_TOTAL = "total";
    private static final String SUB_RANK_TITLE = "title";
    @BindView(R.id.tool_category_bar)
    Toolbar mSubRankToolBar;
    @BindView(R.id.tab_category_layout)
    TabLayout mSubRankTabLayout;
    @BindView(R.id.category_vp)
    ViewPager mSubRankVp;
    private String mRankTitle;
    private String mId;
    private String mRankMonth;
    private String mRankTotal;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initView() {
        mId = getIntent().getStringExtra(SUB_RANK_ID);
        mRankMonth = getIntent().getStringExtra(SUB_RANK_MONTH);
        mRankTotal = getIntent().getStringExtra(SUB_RANK_TOTAL);
        mRankTitle = getIntent().getStringExtra(SUB_RANK_TITLE).split(" ")[0];
        initToolBar(mSubRankToolBar,true,mRankTitle);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        initData();

    }

    private void initData() {
        List<String>titles = new ArrayList<>();
        titles.add("周榜");
        titles.add("月榜");
        titles.add("总榜");
        List<Fragment>fragments = new ArrayList<>();
        fragments.add(SubRankFragment.newInstance(mId));
        fragments.add(SubRankFragment.newInstance(mRankMonth));
        fragments.add(SubRankFragment.newInstance(mRankTotal));
        viewPagerAdapter.setItems(fragments,titles);
        mSubRankVp.setAdapter(viewPagerAdapter);
        mSubRankTabLayout.setupWithViewPager(mSubRankVp);
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_sub_rank;
    }

    @Override
    protected void initInjector() {

    }

    public static void startActivity(Context mContext, String id, String monthRank, String totalRank, String title) {
        Intent intent = new Intent(mContext, SubRankActivity.class);
        intent.putExtra(SUB_RANK_ID, id);
        intent.putExtra(SUB_RANK_MONTH, monthRank);
        intent.putExtra(SUB_RANK_TOTAL, totalRank);
        intent.putExtra(SUB_RANK_TITLE, title);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }
}
