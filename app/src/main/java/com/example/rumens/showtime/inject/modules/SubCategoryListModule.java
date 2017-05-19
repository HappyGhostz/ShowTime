package com.example.rumens.showtime.inject.modules;

import com.example.rumens.showtime.adapter.SubCategoryAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.PerFragment;
import com.example.rumens.showtime.reader.bookclassify.SubCategoryFragment;
import com.example.rumens.showtime.reader.bookclassify.SubCategoryListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */
@Module
public class SubCategoryListModule {
    private SubCategoryFragment mView;
    private final String mSex;
    private final String mMajor;
    private final String mType;

    public SubCategoryListModule(SubCategoryFragment mView, String mSex, String mMajor, String mType) {
        this.mView = mView;
        this.mSex = mSex;
        this.mMajor = mMajor;
        this.mType = mType;
    }
    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new SubCategoryListPresenter(mView,mSex,mMajor,mType);
    }
    @PerFragment
    @Provides
    public BaseQuickAdapter provideViewPagerAdapter(){
        return new SubCategoryAdapter(mView.getContext());
    }
}
