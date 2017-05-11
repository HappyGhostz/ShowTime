package com.example.rumens.showtime.reader.booklist;

import android.text.TextUtils;

import com.example.rumens.showtime.api.IBookApi;
import com.example.rumens.showtime.api.bean.BookHelpList;
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
    private int addStart =0;
    private int addLimit =0;

    public BookTypeListPresenter(IBookListBaseView mView, String mBookListType) {
        this.mView = mView;
        this.mBookListType = mBookListType;
    }

    @Override
    public void getData() {
        if(TextUtils.equals(mBookListType,"书架")){
            String key = StringUtils.creatAcacheKey("recommend-list", "male");
            Observable<Recommend> fromNetWork = RetrofitService.getBookRackListInfo("male")
                    .compose(RxBookUtil.<Recommend>rxCacheListHelper(key));
            Observable.concat(RxBookUtil.rxCreateDiskObservable(key, Recommend.class), fromNetWork)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showLoading();
                        }
                    })
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
        }else if(TextUtils.equals(mBookListType,"社区")){
            addStart=0;
            addLimit=0;
            String key = StringUtils.creatAcacheKey("book-help-list", "all", "updated", IBookApi.start+addStart + "", IBookApi.limit + "", "");
            Observable<BookHelpList> fromNetWork = RetrofitService.getBookHelpListInfo( IBookApi.start+addStart  + "", IBookApi.limit+"" )
                    .compose(RxBookUtil.<BookHelpList>rxCacheListHelper(key));
            Observable.concat(RxBookUtil.rxCreateDiskObservable(key, BookHelpList.class), fromNetWork)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showLoading();
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BookHelpList>() {
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
                        public void onNext(BookHelpList bookHelpList) {
                            mView.loadCommunityList(bookHelpList.helps);
                            addStart = addStart+bookHelpList.helps.size();
//                            addLimit = addLimit+IBookApi.limit;
                        }
                    });
        }
    }

    @Override
    public void getMoreData() {
        if(TextUtils.equals(mBookListType,"社区")){
            String key = StringUtils.creatAcacheKey("book-help-list", "all", "updated", IBookApi.start+addStart + "", IBookApi.limit + addLimit+"", "");
            Observable<BookHelpList> fromNetWork = RetrofitService.getBookHelpListInfo( IBookApi.start+addStart  + "", IBookApi.limit+addLimit+"" )
                    .compose(RxBookUtil.<BookHelpList>rxCacheListHelper(key));
            Observable.concat(RxBookUtil.rxCreateDiskObservable(key, BookHelpList.class), fromNetWork)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BookHelpList>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.loadNoData();
                        }

                        @Override
                        public void onNext(BookHelpList bookHelpList) {
                            mView.loadMoreCommunity(bookHelpList.helps);
                            addStart = addStart+ bookHelpList.helps.size();
//                            addLimit = addLimit+IBookApi.limit;
                        }
                    });
        }

    }
}
