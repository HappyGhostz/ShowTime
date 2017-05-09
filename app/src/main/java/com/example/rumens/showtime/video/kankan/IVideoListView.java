package com.example.rumens.showtime.video.kankan;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.api.bean.VideoListItemBean;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/8
 * @description
 */

public interface IVideoListView<T> extends IBaseView{
    /**
     * 加载数据
     * @param videoListItemBeen 数据
     */
    void loadData(List<VideoListItemBean> videoListItemBeen);
    /**
     * 加载更多
     * @param videoListItemBeen 数据
     */
    void loadMoreData(List<VideoListItemBean> videoListItemBeen);

    /**
     * 没有数据
     */
    void loadNoData();
}
