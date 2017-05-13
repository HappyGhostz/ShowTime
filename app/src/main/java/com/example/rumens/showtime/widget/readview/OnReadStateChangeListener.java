package com.example.rumens.showtime.widget.readview;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public interface OnReadStateChangeListener {
    void onChapterChanged(int chapter);

    void onPageChanged(int chapter, int page);

    void onLoadChapterFailure(int chapter);

    void onCenterClick();

    void onFlip();
}
