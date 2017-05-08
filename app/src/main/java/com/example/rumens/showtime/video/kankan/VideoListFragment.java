package com.example.rumens.showtime.video.kankan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.SlideInBottomAdapter;
import com.example.rumens.showtime.adapter.baseadapter.BaseQuickAdapter;
import com.example.rumens.showtime.adapter.helper.RecyclerViewHelper;
import com.example.rumens.showtime.adapter.listener.OnRequestDataListener;
import com.example.rumens.showtime.api.bean.VideoListItemBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.inject.component.DaggerVideoListComponent;
import com.example.rumens.showtime.inject.modules.VideoListModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */

public class VideoListFragment extends BaseFragment<IBasePresenter> implements IVideoListView<List<VideoListItemBean>> {
    private static final String VIDEO_TYPE = "videotype";
    @BindView(R.id.video_recyclerview)
    RecyclerView mVideoRecyclerview;
    @Inject
    BaseQuickAdapter mAdapter;
    private String mVideoType;

    @Override
    public void loadData(List<VideoListItemBean> videoListItemBeen) {

    }

    @Override
    public void loadLocalVideoData() {

    }

    @Override
    public void loadLocalVideoMoreData() {

    }

    @Override
    public void loadMoreData(List<VideoListItemBean> videoListItemBeen) {

    }

    @Override
    public void loadNoData() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_video_type_list;
    }

    @Override
    protected void initInjector() {
        mVideoType = getArguments().getString(VIDEO_TYPE);
        DaggerVideoListComponent.builder()
                .appComponent(getAppComponent())
                .videoListModule(new VideoListModule(this, mVideoType))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewG(mContext,mVideoRecyclerview,slideAdapter,2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
    }

    @Override
    protected void updateViews() {
//        mPresenter.getData();
    }

    public static Fragment lunch(String videoType) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_TYPE, videoType);
        fragment.setArguments(bundle);
        return fragment;
    }
}
