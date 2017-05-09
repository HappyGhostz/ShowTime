package com.example.rumens.showtime.video.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideInBottomAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerLiveComponent;
import com.example.rumens.showtime.inject.modules.LiveModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */

public class LiveFragment extends BaseFragment<IBasePresenter> implements IVideoLiveView<List<LiveListItemBean>> {
    private static final String GAME_TYPE = "gametype";
    private static final String PLATFORM_TYPE = "platformtype";
    private static final String LIVE_TEPY_DOUYU = "livetypedouyu";
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Inject
    BaseQuickAdapter mAdapter;
    private String mGameType;
    private int mOffSet;
    private String mPlatformType;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initInjector() {
        DaggerLiveComponent.builder()
                .appComponent(getAppComponent())
                .liveModule(new LiveModule(this, mGameType,mPlatformType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {

        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewG(mContext,mRecyclerview,slideAdapter,2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
    }

    @Override
    protected void updateViews() {
        mOffSet = mAdapter.getData().size();
        mPresenter.getData();
    }

    public static Fragment newInstance(String gameType, String platformType) {
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        args.putString(GAME_TYPE, gameType);
        args.putString(PLATFORM_TYPE,platformType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadData(List<LiveListItemBean> liveListItemBeen) {
        mAdapter.updateItems(liveListItemBeen);
    }

    @Override
    public void loadDouyuData(List<DouyuLiveListItemBean.DataBean> dataInfoBeen) {
        mAdapter.updateItems(dataInfoBeen);
    }

    @Override
    public void loadDouyuMoreData(List<DouyuLiveListItemBean.DataBean> dataInfoBeen) {
        mAdapter.loadComplete();
        mAdapter.addItems(dataInfoBeen);
    }

    @Override
    public void loadMoreData(List<LiveListItemBean> liveListItemBeen) {
        mAdapter.loadComplete();
        mAdapter.addItems(liveListItemBeen);
    }

    @Override
    public void loadNoData() {
        mAdapter.loadAbnormal();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGameType = getArguments().getString(GAME_TYPE);
            mPlatformType = getArguments().getString(PLATFORM_TYPE);
        }
    }

}
