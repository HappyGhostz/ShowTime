package com.example.rumens.showtime.reader.bookrank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerSubRankListComponent;
import com.example.rumens.showtime.inject.modules.SubRankListModule;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public class SubRankFragment extends BaseFragment<IBasePresenter> implements ISubRankBaseView {
    private static final String SUB_RANK_FRAGMENT_TYPE = "type";
    @BindView(R.id.rv_subrank_list)
    RecyclerView mRvSubrankList;
    private String mRankType;
    @Inject
    BaseQuickAdapter mAdapter;


    @Override
    public void loadRankList(BooksByCats data) {
        mAdapter.updateItems(data.books);
    }

    @Override
    public void loadMoreRankList(BooksByCats data) {

    }

    @Override
    public void loadNoData() {
        mAdapter.loadAbnormal();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_sub_rank;
    }

    @Override
    protected void initInjector() {
        mRankType = getArguments().getString(SUB_RANK_FRAGMENT_TYPE);
        DaggerSubRankListComponent.builder()
                .appComponent(getAppComponent())
                .subRankListModule(new SubRankListModule(this, mRankType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        SlideInRightAnimationAdapter animAdapter = new SlideInRightAnimationAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext, mRvSubrankList, true, new AlphaInAnimationAdapter(animAdapter));
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    public static Fragment newInstance(String mId) {
        SubRankFragment fragment = new SubRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SUB_RANK_FRAGMENT_TYPE, mId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
