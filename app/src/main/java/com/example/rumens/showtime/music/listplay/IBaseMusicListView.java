package com.example.rumens.showtime.music.listplay;

import com.example.rumens.showtime.api.bean.SongListDetail;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public interface IBaseMusicListView extends IBaseView{
    void loadMusicListDetial(SongListDetail list);
}
