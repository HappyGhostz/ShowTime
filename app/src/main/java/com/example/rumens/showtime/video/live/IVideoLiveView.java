package com.example.rumens.showtime.video.live;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */

public interface IVideoLiveView<T>extends IBaseView {
    /**
     * 加载数据
     * @param liveListItemBeen 数据
     */
    void loadData(List<LiveListItemBean> liveListItemBeen);
    /**
     * 加载数据
     * @param dataInfoBeen 数据
     */
    void loadDouyuData(List<DouyuLiveListItemBean.DataBean> dataInfoBeen);
    /**
     * 加载数据
     * @param dataInfoBeen 数据
     */
    void loadDouyuMoreData(List<DouyuLiveListItemBean.DataBean> dataInfoBeen);

    /**
     * 加载更多
     * @param liveListItemBeen 数据
     */
    void loadMoreData(List<LiveListItemBean> liveListItemBeen);

    /**
     * 没有数据
     */
    void loadNoData();
}
