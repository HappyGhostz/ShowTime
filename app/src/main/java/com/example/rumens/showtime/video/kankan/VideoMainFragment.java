package com.example.rumens.showtime.video.kankan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */

public class VideoMainFragment extends BaseFragment {
    @BindView(R.id.tab_new_layout)
    TabLayout mTabNewLayout;
    @BindView(R.id.tv_live_type)
    TextView mTvLiveType;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> fragments;
    private List<String> titles;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initData();
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        mViewPagerAdapter.setItems(fragments,titles);
        mViewpager.setAdapter(mViewPagerAdapter);
        mTabNewLayout.setupWithViewPager(mViewpager);

    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("热门");
        titles.add("娱乐");
        titles.add("搞笑");
        titles.add("动漫");
        titles.add("微电影");
        titles.add("本地");
        fragments = new ArrayList<>();
        fragments.add(VideoListFragment.lunch( "热门"));
        fragments.add(VideoListFragment.lunch( "娱乐"));
        fragments.add(VideoListFragment.lunch( "搞笑"));
        fragments.add(VideoListFragment.lunch( "动漫"));
        fragments.add(VideoListFragment.lunch( "微电影"));
        fragments.add(VideoListFragment.lunch( "本地"));
    }

    @Override
    protected void updateViews() {
    }
}
