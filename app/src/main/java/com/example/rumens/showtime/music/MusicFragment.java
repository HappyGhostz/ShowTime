package com.example.rumens.showtime.music;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.ViewPagerAdapter;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.music.listmusic.MusicListFragment;
import com.example.rumens.showtime.music.localmusic.MusicLocalFragment;
import com.example.rumens.showtime.music.rankmusic.MusicRankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/4/18
 * @description
 */

public class MusicFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_music_layout)
    TabLayout mTabMusicLayout;
    @BindView(R.id.tv_music_search)
    TextView mTvMusicSearch;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar,true,"音乐爽听");
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        initData();
        searchMusic();
    }

    private void searchMusic() {
        mTvMusicSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {
        List<String> titils = new ArrayList<>();
        titils.add("本地音乐");
        titils.add("推荐歌单");
        titils.add("排行榜");
        List<Fragment>fragments = new ArrayList<>();
        fragments.add(MusicLocalFragment.lunch());
        fragments.add(MusicListFragment.lunch());
        fragments.add(MusicRankFragment.lunch());
        mViewPagerAdapter.setItems(fragments,titils);
        mViewpager.setAdapter(mViewPagerAdapter);
        mTabMusicLayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected void updateViews() {

    }

}
