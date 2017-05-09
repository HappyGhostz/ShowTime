package com.example.rumens.showtime.video.videoliveplay;

import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.base.IBaseView;
import com.example.rumens.showtime.local.VideoInfo;

import java.io.InputStream;

/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */

public interface IVideoView extends IBaseView{
    /**
     * 获取Video数据
     * @param data 数据
     */
    void loadLiveData(LiveDetailBean data);
    /**
     * 获取Video数据
     * @param data 数据
     */
    void loadLiveDouyuData(OldLiveVideoInfo data);
    /**
     * 获取Video数据
     * @param data 数据
     */
    void loadVideoData(VideoInfo data);

    /**
     * 获取弹幕数据
     * @param inputStream 数据
     */
    void loadDanmakuData(InputStream inputStream);
}
