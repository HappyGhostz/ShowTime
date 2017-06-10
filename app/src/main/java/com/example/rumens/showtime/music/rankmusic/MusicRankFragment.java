package com.example.rumens.showtime.music.rankmusic;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideOnScaleAndeAlphaAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.IMusicsApi;
import com.example.rumens.showtime.api.bean.RankingListItem;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.inject.component.DaggerMusicRankComponent;
import com.example.rumens.showtime.inject.modules.MusicRankModule;
import com.example.rumens.showtime.utils.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicRankFragment extends BaseFragment implements IMusicRankView {
    @BindView(R.id.rv_list_music)
    RecyclerView mRvListMusic;
    @Inject
    BaseQuickAdapter mAdapter;
    private List<RankingListItem.RangkingDetail> mRangkingDetails = new ArrayList<>();


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_music_rank;
    }

    @Override
    protected void initInjector() {
        DaggerMusicRankComponent.builder()
                .appComponent(getAppComponent())
                .musicRankModule(new MusicRankModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        SlideOnScaleAndeAlphaAdapter slideAdapter = new SlideOnScaleAndeAlphaAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(getContext(),mRvListMusic,true,slideAdapter);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    public static Fragment lunch() {
        MusicRankFragment fragment = new MusicRankFragment();
        return fragment;
    }

    @Override
    public void loadListMusic(List<RankingListItem.RangkingDetail> details) {
        mAdapter.updateItems(details);
        mRangkingDetails.addAll(details);
    }
}
