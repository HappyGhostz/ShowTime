package com.example.rumens.showtime.reader.bookhelp;

import com.example.rumens.showtime.api.bean.BookHelp;
import com.example.rumens.showtime.api.bean.CommentList;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public interface IBookHelpBaseView extends IBaseView{
    void loadBookHelpDetail(BookHelp data);

    void loadBestComments(CommentList list);

    void loadBookHelpComments(CommentList list);

    void loadMoreBookHelpComments(CommentList list);

}
