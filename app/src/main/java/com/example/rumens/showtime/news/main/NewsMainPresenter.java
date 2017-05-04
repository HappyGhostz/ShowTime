package com.example.rumens.showtime.news.main;

import android.util.Log;

import com.example.rumens.showtime.base.IRxBusPresenter;
import com.example.rumens.showtime.local.NewsTypeInfo;
import com.example.rumens.showtime.local.NewsTypeInfoDao;
import com.example.rumens.showtime.rxBus.RxBus;

import java.util.List;


import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author Zhaochen Ping
 * @create 2017/4/19
 * @description
 */

public class NewsMainPresenter implements IRxBusPresenter {
    private INewsView fragment;
    private NewsTypeInfoDao newsTypeInfoDao;
    private RxBus rxBus;

    public NewsMainPresenter(INewsView view, NewsTypeInfoDao newsTypeInfoDao, RxBus rxBus) {
        this.fragment = view;
        this.newsTypeInfoDao = newsTypeInfoDao;
        this.rxBus = rxBus;
    }

    @Override
    public void getData() {
        newsTypeInfoDao.queryBuilder().rx().list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NewsTypeInfo>>() {
                    @Override
                    public void call(List<NewsTypeInfo> newsTypeBeen) {
                        fragment.loadData(newsTypeBeen);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = rxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("NewsMainPresenter",throwable.toString());
            }
        });
        rxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        rxBus.unSubscribe(this);
    }
}
