package com.example.rumens.showtime.reader.bookrank;

import com.example.rumens.showtime.api.bean.RankingListBean;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.utils.RxBookUtil;
import com.example.rumens.showtime.utils.StringUtils;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class BookRankListPresenter implements IBasePresenter {
    private final IBookRankView mView;
    private final String mBookType;

    public BookRankListPresenter(IBookRankView mView, String mBookType) {

        this.mView = mView;
        this.mBookType = mBookType;
    }

    @Override
    public void getData() {
        String key = StringUtils.creatAcacheKey("book-ranking-list");
        Observable<RankingListBean> fromNetWork = RetrofitService.getBookRankInfo()
                .compose(RxBookUtil.<RankingListBean>rxCacheBeanHelper(key));
        Observable.concat(RxBookUtil.rxCreateDiskObservable(key,RankingListBean.class),fromNetWork)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RankingListBean>() {
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
                    public void onNext(RankingListBean rankingListBean) {
                        mView.LoadRankList(rankingListBean);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
