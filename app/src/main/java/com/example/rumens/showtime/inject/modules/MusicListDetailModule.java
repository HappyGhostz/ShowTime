package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.MusicListDetialAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.music.listplay.MusicListDetialActivity;
import com.example.rumens.showtime.music.listplay.MusicListDetialPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */
@Module
public class MusicListDetailModule {
    private final MusicListDetialActivity mView;
    private final String songListid;

    public MusicListDetailModule(MusicListDetialActivity mView, String songListid) {
        this.mView = mView;
        this.songListid = songListid;
    }
    @PerActivity
    @Provides
    public IBasePresenter providesPresenter(){
        return new MusicListDetialPresenter(mView,songListid);
    }
    @PerActivity
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new MusicListDetialAdapter(mView);
    }
}
