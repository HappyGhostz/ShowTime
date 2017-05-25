package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.MusicListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.music.listmusic.MusicListFragment;
import com.example.rumens.showtime.music.listmusic.MusicListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */
@Module
public class MusicLisModule {
    private MusicListFragment mView;

    public MusicLisModule(MusicListFragment mView) {
        this.mView = mView;
    }
    @PerFragment
    @Provides
    public IBasePresenter providesPresenter(){
        return new MusicListPresenter(mView);
    }
    @PerFragment
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new MusicListAdapter(mView.getContext());
    }
}
