package com.example.rumens.showtime.reader.bookclassify;

import com.example.rumens.showtime.api.bean.CategoryList;
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
 * @create 2017/5/11
 * @description
 */

public class BookClassifyListPresenter implements IBasePresenter {
    private final IBookClassifyBaseView mView;
    private final String mBookType;

    public BookClassifyListPresenter(IBookClassifyBaseView mView, String mBookType) {
        this.mView = mView;
        this.mBookType = mBookType;
    }

    @Override
    public void getData() {
        String key = StringUtils.creatAcacheKey("book-category-list");
        Observable<CategoryList> fromNetWork = RetrofitService.getBookClassifyInfo()
                .compose(RxBookUtil.<CategoryList>rxCacheBeanHelper(key));
        Observable.concat(RxBookUtil.rxCreateDiskObservable(key, CategoryList.class), fromNetWork)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryList>() {
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
                    public void onNext(CategoryList categoryList) {
                        mView.LoadCategoryList(categoryList);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
