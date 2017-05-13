package com.example.rumens.showtime.reader.bookread;

import com.example.rumens.showtime.api.bean.BookMixATocBean;
import com.example.rumens.showtime.api.bean.ChapterReadBean;
import com.example.rumens.showtime.base.IBaseView;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public interface IReadBaseView extends IBaseView{
    void loadBookToc(List<BookMixATocBean.mixToc.Chapters> list);

    void loadChapterRead(ChapterReadBean.Chapter data, int chapter);

}
