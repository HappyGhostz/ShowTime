package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.SubRankListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.reader.bookrank.SubRankFragment;
import com.example.rumens.showtime.reader.bookrank.SubRankListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */
@Module
public class SubRankListModule {
    private final SubRankFragment mView;
    private final String mRankType;

    public SubRankListModule(SubRankFragment mView, String mRankType) {
        this.mView = mView;
        this.mRankType = mRankType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new SubRankListPresenter(mView, mRankType);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new SubRankListAdapter(mView.getContext());

    }
}
