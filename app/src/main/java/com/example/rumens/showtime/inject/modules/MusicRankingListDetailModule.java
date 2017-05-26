package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.MusicRankPlayListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.music.listplay.MusicRankPlayListPresenter;
import com.example.rumens.showtime.music.listplay.MusicRankingListDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */
@Module
public class MusicRankingListDetailModule {
    private final MusicRankingListDetailActivity mView;
    private final int mType;

    public MusicRankingListDetailModule(MusicRankingListDetailActivity mView, int mType) {
        this.mView = mView;
        this.mType = mType;
    }
    @PerActivity
    @Provides
    public IBasePresenter providesPeresenter(){
        return new MusicRankPlayListPresenter(mView,mType);
    }
    @PerActivity
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new MusicRankPlayListAdapter(mView);
    }
}
