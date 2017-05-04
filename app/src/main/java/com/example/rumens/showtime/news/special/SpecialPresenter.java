package com.example.rumens.showtime.news.special;

import com.example.rumens.showtime.adapter.item.SpecialItem;
import com.example.rumens.showtime.api.bean.NewsItemInfo;
import com.example.rumens.showtime.api.bean.SpecialInfo;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public class SpecialPresenter implements IBasePresenter {
    private final ISpecialView mView;
    private final String mSpecialId;

    public SpecialPresenter(ISpecialView mView, String mSpecialId) {
        this.mView = mView;
        this.mSpecialId = mSpecialId;
    }

    @Override
    public void getData() {
        RetrofitService.getSpecial(mSpecialId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .flatMap(new Func1<SpecialInfo, Observable<SpecialItem>>() {
                    @Override
                    public Observable<SpecialItem> call(SpecialInfo specialInfo) {
                        mView.loadBanner(specialInfo);
                        return convertSpecialBeanToItem(specialInfo);
                    }
                })
                .toList()
                .compose(mView.<List<SpecialItem>>bindToLife())
                .subscribe(new Subscriber<List<SpecialItem>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                            @Override
                            public void onRetry() {
                                getData();
                            }
                        });
                    }

                    @Override
                    public void onNext(List<SpecialItem> specialItems) {
                         mView.loadData(specialItems);
                    }
                });
    }

    private Observable<SpecialItem> convertSpecialBeanToItem(SpecialInfo specialBean) {
        // 这边 +1 是接口数据还有个 topicsplus 的字段可能是穿插在 topics 字段列表中间。这里没有处理 topicsplus
        final SpecialItem[] specialItems = new SpecialItem[specialBean.getTopics().size() + 1];
        return Observable.from(specialBean.getTopics())
                // 获取头部
                .doOnNext(new Action1<SpecialInfo.TopicsEntity>() {
                    @Override
                    public void call(SpecialInfo.TopicsEntity topicsEntity) {
                        specialItems[topicsEntity.getIndex() - 1] = new SpecialItem(true,
                                topicsEntity.getIndex() + "/" + specialItems.length + " " + topicsEntity.getTname());
                    }
                })
                // 排序
                .toSortedList(new Func2<SpecialInfo.TopicsEntity, SpecialInfo.TopicsEntity, Integer>() {
                    @Override
                    public Integer call(SpecialInfo.TopicsEntity topicsEntity, SpecialInfo.TopicsEntity topicsEntity2) {
                        return topicsEntity.getIndex() - topicsEntity2.getIndex();
                    }
                })
                // 拆分
                .flatMap(new Func1<List<SpecialInfo.TopicsEntity>, Observable<SpecialInfo.TopicsEntity>>() {
                    @Override
                    public Observable<SpecialInfo.TopicsEntity> call(List<SpecialInfo.TopicsEntity> topicsEntities) {
                        return Observable.from(topicsEntities);
                    }
                })
                .flatMap(new Func1<SpecialInfo.TopicsEntity, Observable<SpecialItem>>() {
                    @Override
                    public Observable<SpecialItem> call(SpecialInfo.TopicsEntity topicsEntity) {
                        // 转换并在每个列表项增加头部
                        return Observable.from(topicsEntity.getDocs())
                                .map(new Func1<NewsItemInfo, SpecialItem>() {
                                    @Override
                                    public SpecialItem call(NewsItemInfo newsItemBean) {
                                        return new SpecialItem(newsItemBean);
                                    }
                                })
                                .startWith(specialItems[topicsEntity.getIndex() - 1]);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
