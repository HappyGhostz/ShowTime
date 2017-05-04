package com.example.rumens.showtime.news.special;

import com.example.rumens.showtime.adapter.item.SpecialItem;
import com.example.rumens.showtime.api.bean.SpecialInfo;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

interface ISpecialView extends IBaseView{

    /**
     * 显示数据
     * @param specialItems 新闻
     */
    void loadData(List<SpecialItem> specialItems);

    /**
     * 添加头部
     * @param specialBean
     */
    void loadBanner(SpecialInfo specialBean);
}
