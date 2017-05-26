package com.example.rumens.showtime.music.listplay;

import com.example.rumens.showtime.api.bean.RankingListDetail;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public interface IBaseRankListPlayView extends IBaseView{
    void loadRankPlayList(RankingListDetail detail);
}
