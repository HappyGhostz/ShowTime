package com.example.rumens.showtime.base;

import com.example.rumens.showtime.widget.EmptyErrLayout;
import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * @author Zhaochen Ping
 * @create 2017/4/17
 * @description
 */

public interface IBaseView {
    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误
     * @param onRetryListener 点击监听
     */
    void showNetError(EmptyErrLayout.OnRetryListener onRetryListener);
    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();
}
