package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.MusicRankAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.music.rankmusic.MusicRankFragment;
import com.example.rumens.showtime.music.rankmusic.MusicRankPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */
@Module
public class MusicRankModule {
    private MusicRankFragment mView;

    public MusicRankModule(MusicRankFragment mView) {
        this.mView = mView;
    }
    @PerFragment
    @Provides
    public IBasePresenter providesPresenter(){
        return new MusicRankPresenter(mView);
    }
    @PerFragment
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new MusicRankAdapter(mView.getContext());
    }
}
