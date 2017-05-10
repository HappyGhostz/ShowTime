package com.example.rumens.showtime.video.kankan;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rumens.showtime.R;
import com.example.rumens.showtime.adapter.VideoLocalListAdapter;
import com.example.rumens.showtime.api.bean.VideoLocalListItemBean;
import com.example.rumens.showtime.base.BaseFragment;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.MyAsyncQueryHandler;
import com.example.rumens.showtime.video.videoliveplay.VideoPlayActivity;
import com.example.rumens.showtime.video.videonetplay.VideoNetPlayActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class VideoLocalListFragment extends BaseFragment<IBasePresenter> {
    private static final String VIDEO_LOCAL_TYPE = "VIDEOLOCALTYPE";
    @BindView(R.id.video_listView)
    ListView mVideoListView;
    private VideoLocalListAdapter adapter;
    private String mVideoType;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_layout_local_video;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        mVideoType = getArguments().getString(VIDEO_LOCAL_TYPE);
        ContentResolver resolver = getContext().getContentResolver();
        MyAsyncQueryHandler queryHandler = new MyAsyncQueryHandler(resolver);
        adapter = new VideoLocalListAdapter(getContext(), null, false);
        mVideoListView.setAdapter(adapter);
        queryHandler.startQuery(0, adapter, MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{io.vov.vitamio.provider.MediaStore.Video.Media._ID, io.vov.vitamio.provider.MediaStore.Video.Media.TITLE, io.vov.vitamio.provider.MediaStore.Video.Media.DURATION, io.vov.vitamio.provider.MediaStore.Video.Media.SIZE, io.vov.vitamio.provider.MediaStore.Video.Media.DATA}, null, null, null);
        initListeren();
    }

    private void initListeren() {
        mVideoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);
                ArrayList<VideoLocalListItemBean> videoLocalListItem =getVideoLocalItem(cursor);
                VideoNetPlayActivity.lunchLocalVideo(mContext,videoLocalListItem,position,mVideoType);
            }
        });
    }

    private ArrayList<VideoLocalListItemBean> getVideoLocalItem(Cursor cursor) {
        ArrayList<VideoLocalListItemBean> videoList = new ArrayList<>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            VideoLocalListItemBean itemBean = VideoLocalListItemBean.getInstanceFromCursor(cursor);
            videoList.add(itemBean);
        }
        return videoList;
    }

    @Override
    protected void updateViews() {
    }

    public static Fragment lunch(String mVideoType) {
        VideoLocalListFragment fragment = new VideoLocalListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_LOCAL_TYPE, mVideoType);
        fragment.setArguments(bundle);
        return fragment;
    }
}
