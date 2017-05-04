package com.example.rumens.showtime.adapter.item;

import com.example.rumens.showtime.api.bean.NewsItemInfo;
import com.example.rumens.showtime.entity.SectionEntity;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public class SpecialItem extends SectionEntity<NewsItemInfo> {
    public SpecialItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SpecialItem(NewsItemInfo newsItemBean) {
        super(newsItemBean);
    }


}
