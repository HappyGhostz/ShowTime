package com.example.rumens.showtime.reader.bookrank;

import com.example.rumens.showtime.adapter.SubRankListAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/19
 * @description
 */
@Module
public class SubOtherRankListModule {
    private final SubOtherHomeRankActivity mView;
    private final String mBookId;

    public SubOtherRankListModule(SubOtherHomeRankActivity mView, String mBookId) {
        this.mView = mView;
        this.mBookId = mBookId;
    }
    @PerActivity
    @Provides
    public BaseQuickAdapter providesAdapter(){
        return new SubRankListAdapter(mView.getApplicationContext());
    }
    @PerActivity
    @Provides
    public IBasePresenter providesPresent(){
        return new SubOtherRankListPresenter(mView, mBookId);
    }
}
