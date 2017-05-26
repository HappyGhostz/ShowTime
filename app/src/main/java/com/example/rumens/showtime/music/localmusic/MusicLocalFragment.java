package com.example.rumens.showtime.music.localmusic;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideOnScaleAndeAlphaAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.inject.component.DaggerMusicLocalListComponent;
import com.example.rumens.showtime.inject.modules.MusicLocalListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicLocalFragment extends BaseFragment implements IBaseLocalMusicView{
    @BindView(R.id.rv_list_music)
    RecyclerView mRvListMusic;
    @Inject
    BaseQuickAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_local_list;
    }

    @Override
    protected void initInjector() {
        DaggerMusicLocalListComponent.builder()
                .appComponent(getAppComponent())
                .musicLocalListModule(new MusicLocalListModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        SlideOnScaleAndeAlphaAdapter slideAdapter = new SlideOnScaleAndeAlphaAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext,mRvListMusic,true,slideAdapter);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    public static Fragment lunch() {
        MusicLocalFragment fragment = new MusicLocalFragment();
        return fragment;
    }

    @Override
    public void loadLocalMusicListInfo(List<SongLocalBean> localBeanList) {
        mAdapter.updateItems(localBeanList);
    }
}
