package com.example.rumens.showtime.reader.booklist;

import android.text.TextUtils;

import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.base.IBookListBaseView;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.utils.RxBookUtil;
import com.example.rumens.showtime.utils.StringUtils;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/9
 * @description
 */

public class BookTypeListPresenter implements IBasePresenter {
    private final IBookListBaseView mView;
    private final String mBookListType;
    private RxBus rxBus;

    public BookTypeListPresenter(IBookListBaseView mView, String mBookListType) {
        this.mView = mView;
        this.mBookListType = mBookListType;
    }

    @Override
    public void getData() {
        if(TextUtils.equals(mBookListType,"书架")){
            String key = StringUtils.creatAcacheKey("recommend-list", "male");
            Observable<Recommend> fromNetWork = RetrofitService.getBookRackListInfo("male")
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showLoading();
                        }
                    })
                    .compose(RxBookUtil.<Recommend>rxCacheListHelper(key));
            Observable.concat(RxBookUtil.rxCreateDiskObservable(key, Recommend.class), fromNetWork)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Recommend>() {

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
                        public void onNext(Recommend recommend) {
                            if(recommend!=null){
                                List<Recommend.RecommendBooks> books = recommend.books;
                                if(books!=null&&!books.isEmpty()&&mView!=null){
                                    mView.loadRecommendList(books);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void getMoreData() {

    }
}
