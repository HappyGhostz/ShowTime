package com.example.rumens.showtime.reader.bookrank;

import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/19
 * @description
 */

public interface IBaseSubOtherView extends IBaseView{
    void loadRankList(BooksByCats data);
}
