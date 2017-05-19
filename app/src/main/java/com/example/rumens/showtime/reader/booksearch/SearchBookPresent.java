package com.example.rumens.showtime.reader.booksearch;

import com.example.rumens.showtime.api.bean.SearchDetail;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/5/18
 * @description
 */

public class SearchBookPresent implements IBasePresenter {
    private final ISearchBaseView mView;
    private final String mBookName;

    public SearchBookPresent(ISearchBaseView mView, String mBookName) {
        this.mView = mView;
        this.mBookName = mBookName;
    }

    @Override
    public void getData() {
         RetrofitService.getSearchResult(mBookName)
                 .subscribeOn(Schedulers.io())
                 .doOnSubscribe(new Action0() {
                     @Override
                     public void call() {
                         mView.showLoading();
                     }
                 })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchDetail>() {
                    @Override
                    public void onNext(SearchDetail bean) {
                        List<SearchDetail.SearchBooks> list = bean.books;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.loadSearchResultList(list);
                        }
                    }

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
                });
    }

    @Override
    public void getMoreData() {

    }
}
