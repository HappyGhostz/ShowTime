package com.example.rumens.showtime.music.searchmusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRecyclerViewItemClickListener;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.SearchMusic;
import com.example.rumens.showtime.base.BaseActivity;
import com.example.rumens.showtime.inject.component.DaggerSeachMusicShowListComponent;
import com.example.rumens.showtime.inject.modules.SeachMusicShowListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */

public class SearchMusicShow extends BaseActivity implements ISearchView {
    private static final String SEARCH_MUSIC_NAME = "searchmusicname";
    @BindView(R.id.tool_search_bar)
    Toolbar toolSearchBar;
    @BindView(R.id.rv_search_list)
    RecyclerView rvSearchList;
    @Inject
    BaseQuickAdapter adapter;

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        initToolBar(toolSearchBar,true,"搜索详情");
        RecyclerViewHelper.initRecyclerViewV(this,rvSearchList,true,adapter);
        adapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
//        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                SearchMusic.ResultBean.SongsBean searchMusic = (SearchMusic.ResultBean.SongsBean) adapter.getItem(position);
//                SearchMusicPlayActivity.lunch(getApplicationContext() ,searchMusic.getPage(),searchMusic.getName());
//            }
//        });
    }

    @Override
    protected int getContenView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initInjector() {
        String searchMusicName = getIntent().getStringExtra(SEARCH_MUSIC_NAME);
        DaggerSeachMusicShowListComponent.builder()
                .appComponent(getAppComponent())
                .seachMusicShowListModule(new SeachMusicShowListModule(this, searchMusicName))
                .build()
                .inject(this);
    }

    @Override
    public void loadSearchMusicList(SearchMusic searchMusic) {
        List<SearchMusic.ResultBean.SongsBean> songs = searchMusic.getResult().getSongs();
        adapter.updateItems(songs);
    }

    @Override
    public void loadMoreSearchMusicList(SearchMusic searchMusic) {
        List<SearchMusic.ResultBean.SongsBean> songs = searchMusic.getResult().getSongs();
        adapter.loadComplete();
        adapter.addItems(songs);
    }

    @Override
    public void loadNoSearchMusicList() {
        adapter.loadAbnormal();
    }

    public static void lunch(Context mContext, String musicName) {
        Intent intent = new Intent(mContext, SearchMusicShow.class);
        intent.putExtra(SEARCH_MUSIC_NAME, musicName);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit);
    }

}
