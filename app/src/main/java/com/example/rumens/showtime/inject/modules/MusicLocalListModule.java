package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.MusicLocalListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.music.localmusic.MusicLocalFragment;
import com.example.rumens.showtime.music.localmusic.MusicLocalListPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */
@Module
public class MusicLocalListModule {
    private MusicLocalFragment mView;
    private List<SongLocalBean> songs;

    public MusicLocalListModule(MusicLocalFragment mView, List<SongLocalBean> songs) {
        this.mView = mView;
        this.songs = songs;
    }
    @PerFragment
    @Provides
    public IBasePresenter providesPresenter(){
        return new MusicLocalListPresenter(mView,mView.getContext());
    }
    @PerFragment
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new MusicLocalListAdapter(mView.getContext(),songs);
    }
}
