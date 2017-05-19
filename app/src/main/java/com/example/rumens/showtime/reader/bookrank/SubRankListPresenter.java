package com.example.rumens.showtime.reader.bookrank;

import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.api.bean.Rankings;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public class SubRankListPresenter implements IBasePresenter {
    private final ISubRankBaseView mView;
    private final String mRankType;

    public SubRankListPresenter(ISubRankBaseView mView, String mRankType) {
        this.mView = mView;
        this.mRankType = mRankType;
    }

    @Override
    public void getData() {
        RetrofitService.getRanking(mRankType)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rankings>() {
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
                    public void onNext(Rankings ranking) {
                        List<Rankings.RankingBean.BooksBean> books = ranking.ranking.books;

                        BooksByCats cats = new BooksByCats();
                        cats.books = new ArrayList<>();
                        for (Rankings.RankingBean.BooksBean bean : books) {
                            cats.books.add(new BooksByCats.BooksBean(bean._id, bean.cover, bean.title, bean.author, bean.cat, bean.shortIntro, bean.latelyFollower, bean.retentionRatio));
                        }
                        mView.loadRankList(cats);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
