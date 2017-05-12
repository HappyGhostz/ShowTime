package com.example.rumens.showtime.reader.bookrank;

import android.view.View;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public interface OnRvItemClickListener<T> {
    void onItemClick(View view, int position, T data);
}
