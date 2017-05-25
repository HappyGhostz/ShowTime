package com.example.rumens.showtime.music.listmusic;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideOnScaleAndeAlphaAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.WrapperSongListInfo;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerMusicListComponent;
import com.example.rumens.showtime.inject.modules.MusicLisModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicListFragment extends BaseFragment<IBasePresenter> implements IListMusicView {
    @BindView(R.id.rv_list_music)
    RecyclerView mRvListMusic;
    @Inject
    BaseQuickAdapter mAdapter;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_list;
    }

    @Override
    protected void initInjector() {
        DaggerMusicListComponent.builder()
                .appComponent(getAppComponent())
                .musicLisModule(new MusicLisModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        SlideOnScaleAndeAlphaAdapter slideAdapter = new SlideOnScaleAndeAlphaAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewG(mContext,mRvListMusic,slideAdapter,2);
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

    public static Fragment lunch() {
        MusicListFragment fragment = new MusicListFragment();
        return fragment;
    }

    @Override
    public void loadListMusic(List<WrapperSongListInfo.SongListInfo> infos) {
        mAdapter.updateItems(infos);
    }

    @Override
    public void loadMoreListMusic(List<WrapperSongListInfo.SongListInfo> infos) {
        mAdapter.loadComplete();
        mAdapter.addItems(infos);
    }

    @Override
    public void loadNoData() {
        mAdapter.loadAbnormal();
    }

}
