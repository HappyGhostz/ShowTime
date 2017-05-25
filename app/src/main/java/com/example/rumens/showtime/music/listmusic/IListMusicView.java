package com.example.rumens.showtime.music.listmusic;

import com.example.rumens.showtime.api.bean.WrapperSongListInfo;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public interface IListMusicView extends IBaseView{
    void loadListMusic(List<WrapperSongListInfo.SongListInfo> infos);
    void loadMoreListMusic(List<WrapperSongListInfo.SongListInfo> infos);
    void loadNoData();
}
