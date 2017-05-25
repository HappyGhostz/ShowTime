package com.example.rumens.showtime.music.rankmusic;

import com.example.rumens.showtime.api.bean.RankingListItem;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public interface IMusicRankView extends IBaseView{
    void loadListMusic(List<RankingListItem.RangkingDetail> details);
}
