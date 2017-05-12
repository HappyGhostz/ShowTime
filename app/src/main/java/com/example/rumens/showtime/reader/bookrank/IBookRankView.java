package com.example.rumens.showtime.reader.bookrank;

import com.example.rumens.showtime.api.bean.RankingListBean;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public interface IBookRankView extends IBaseView{
    void LoadRankList(RankingListBean rankingList);
}
