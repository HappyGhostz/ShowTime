package com.example.rumens.showtime.music.localmusic;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideOnScaleAndeAlphaAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.inject.component.DaggerMusicLocalListComponent;
import com.example.rumens.showtime.inject.modules.MusicLocalListModule;
import com.example.rumens.showtime.music.musicplay.MusicPlay;

import java.util.ArrayList;
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
    private RelativeLayout mRlPlayAll;
    private ImageView mIvSetting;
    private TextView mTvPlayNumber;
    private List<SongLocalBean> songs = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_local_list;
    }

    @Override
    protected void initInjector() {
        DaggerMusicLocalListComponent.builder()
                .appComponent(getAppComponent())
                .musicLocalListModule(new MusicLocalListModule(this,songs))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        View view = View.inflate(getContext(),R.layout.item_music_rank_list_play_head, null);
//        View view = from.inflate(R.layout.item_book_help_detial_head, null);
        initHeadView(view);
        mAdapter.addHeaderView(view);
        SlideOnScaleAndeAlphaAdapter slideAdapter = new SlideOnScaleAndeAlphaAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext,mRvListMusic,true,slideAdapter);
    }

    private void initHeadView(View view) {
        mRlPlayAll = (RelativeLayout) view.findViewById(R.id.rl_play_all_layout);
        mIvSetting = (ImageView) view.findViewById(R.id.iv_detail_select);
        mTvPlayNumber = (TextView) view.findViewById(R.id.tv_play_all_number);
        mRlPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlay.lunch(mContext,(SongLocalBean) mAdapter.getItem(0),1,songs);
            }
        });
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
        songs.addAll(localBeanList);
        mAdapter.updateItems(localBeanList);
        initHeadData(localBeanList);
    }

    private void initHeadData(List<SongLocalBean> localBeanList) {
        mTvPlayNumber.setText("(共"+localBeanList.size()+"首)");
    }
}
