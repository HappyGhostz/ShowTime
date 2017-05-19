package com.example.rumens.showtime.reader.bookclassify;

import com.example.rumens.showtime.api.IBookApi;
import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.utils.RxBookUtil;
import com.example.rumens.showtime.utils.StringUtils;
import com.example.rumens.showtime.utils.ToastUtils;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class SubCategoryListPresenter implements IBasePresenter {
    private ICategoryBaseView mView;
    private final String mSex;
    private final String mMajor;
    private final String mType;
    private int mStart = 0;
    private int mLimit = 0;

    public SubCategoryListPresenter(ICategoryBaseView mView, String mSex, String mMajor, String mType) {
        this.mView = mView;
        this.mSex = mSex;
        this.mMajor = mMajor;
        this.mType = mType;
    }

    @Override
    public void getData() {
        String key = StringUtils.creatAcacheKey("category-list", mSex,mType, mMajor, "", IBookApi.start, IBookApi.limit);
        Observable<BooksByCats> fromNetWork = RetrofitService.getBooksByCatsInfo(mSex, mType, mMajor, "", IBookApi.start, IBookApi.limit)
                .compose(RxBookUtil.<BooksByCats>rxCacheListHelper(key));
        Observable.concat(RxBookUtil.rxCreateDiskObservable(key, BooksByCats.class), fromNetWork)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BooksByCats>() {
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
                    public void onNext(BooksByCats booksByCats) {
                        mView.loadCategoryList(booksByCats);
                        mStart = mStart+booksByCats.books.size();
                    }
                });
    }

    @Override
    public void getMoreData() {
        String key = StringUtils.creatAcacheKey("category-list", mSex,mType, mMajor, "", IBookApi.start+mStart, IBookApi.limit);
        Observable<BooksByCats> fromNetWork = RetrofitService.getBooksByCatsInfo(mSex, mType, mMajor, "", IBookApi.start+mStart, IBookApi.limit)
                .compose(RxBookUtil.<BooksByCats>rxCacheListHelper(key));
        Observable.concat(RxBookUtil.rxCreateDiskObservable(key, BooksByCats.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BooksByCats>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast("小主,没资源了-o-");
                    }

                    @Override
                    public void onNext(BooksByCats booksByCats) {
                        mView.loadMoreCategoryList(booksByCats);
                        mStart = mStart+booksByCats.books.size();
                    }
                });
    }
}
