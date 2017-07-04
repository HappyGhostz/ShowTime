package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.SearchMusicAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;
import com.example.rumens.showtime.music.searchmusic.SearchMusicPresent;
import com.example.rumens.showtime.music.searchmusic.SearchMusicShow;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */
@Module
public class SeachMusicShowListModule {
    private final SearchMusicShow mView;
    private final String searchMusicName;

    public SeachMusicShowListModule(SearchMusicShow mView, String searchMusicName) {
        this.mView = mView;
        this.searchMusicName = searchMusicName;
    }
    @PerActivity
    @Provides
    public IBasePresenter providesPresent(){
        return new SearchMusicPresent(mView,searchMusicName);
    }
    @PerActivity
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new SearchMusicAdapter(mView.getApplicationContext());
    }
}
