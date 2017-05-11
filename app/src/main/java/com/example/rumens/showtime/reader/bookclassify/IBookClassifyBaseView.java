package com.example.rumens.showtime.reader.bookclassify;

import com.example.rumens.showtime.api.bean.CategoryList;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhaochen Ping
 * @create 2017/5/11
 * @description
 */

public interface IBookClassifyBaseView extends IBaseView{
    void showCategoryList(CategoryList data);
}
