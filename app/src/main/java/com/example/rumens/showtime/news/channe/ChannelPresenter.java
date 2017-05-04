package com.example.rumens.showtime.news.channe;

import com.example.rumens.showtime.local.NewsTypeInfo;
import com.example.rumens.showtime.local.NewsTypeInfoDao;
import com.example.rumens.showtime.local.daoHapper.NewsTypeDao;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.rxBus.event.ChannelEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public class ChannelPresenter implements IChannelPresenter<NewsTypeInfo> {
    private final IChannelView mView;
    private final NewsTypeInfoDao newsTypeInfoDao;
    private final RxBus rxBus;

    public ChannelPresenter(IChannelView mView, NewsTypeInfoDao newsTypeInfoDao, RxBus rxBus) {
        this.mView = mView;
        this.newsTypeInfoDao = newsTypeInfoDao;
        this.rxBus = rxBus;
    }

    @Override
    public void getData() {
        final List<NewsTypeInfo> checkList = newsTypeInfoDao.queryBuilder().list();
        final ArrayList<String> typeInfos = new ArrayList<>();
        for (NewsTypeInfo typeInfo:checkList) {
            typeInfos.add(typeInfo.getTypeId());
        }
        Observable.from(NewsTypeDao.getAllChannels())
                .filter(new Func1<NewsTypeInfo, Boolean>() {
                    @Override
                    public Boolean call(NewsTypeInfo newsTypeInfo) {
                        return !typeInfos.contains(newsTypeInfo.getTypeId());
                    }
                })
                .toList()
                .subscribe(new Subscriber<List<NewsTypeInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<NewsTypeInfo> newsTypeInfos) {
                         mView.loadData(checkList,newsTypeInfos);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void swap(int fromPos, int toPos) {
         rxBus.post(new ChannelEvent(ChannelEvent.SWAP_EVENT,fromPos,toPos));
    }

    @Override
    public void insert(final NewsTypeInfo data) {
         newsTypeInfoDao.rx().insert(data)
                 .subscribeOn(Schedulers.io())
                 .subscribe(new Subscriber<NewsTypeInfo>() {
                     @Override
                     public void onCompleted() {
                         rxBus.post(new ChannelEvent(ChannelEvent.ADD_EVENT,data));
                     }

                     @Override
                     public void onError(Throwable e) {

                     }

                     @Override
                     public void onNext(NewsTypeInfo newsTypeInfo) {

                     }
                 });
    }

    @Override
    public void delete(final NewsTypeInfo data) {
        newsTypeInfoDao.rx().delete(data)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        rxBus.post(new ChannelEvent(ChannelEvent.DEL_EVENT,data));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });

    }

    @Override
    public void update(List<NewsTypeInfo> list) {
        Observable.from(list)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        newsTypeInfoDao.deleteAll();
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NewsTypeInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsTypeInfo newsTypeInfo) {
                        newsTypeInfo.setId(null);
                        newsTypeInfoDao.save(newsTypeInfo);
                    }
                });
    }
}
