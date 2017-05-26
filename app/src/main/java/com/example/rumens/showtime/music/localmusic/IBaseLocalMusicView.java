package com.example.rumens.showtime.music.localmusic;

import com.example.rumens.showtime.api.bean.SongLocalBean;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public interface IBaseLocalMusicView extends IBaseView{
    void loadLocalMusicListInfo(List<SongLocalBean> localBeanList);
}
