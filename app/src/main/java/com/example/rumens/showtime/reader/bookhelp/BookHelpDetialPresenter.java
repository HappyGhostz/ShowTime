package com.example.rumens.showtime.reader.bookhelp;

import com.example.rumens.showtime.api.IBookApi;
import com.example.rumens.showtime.api.bean.BookHelp;
import com.example.rumens.showtime.api.bean.CommentList;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/5/17
 * @description
 */

public class BookHelpDetialPresenter implements IBasePresenter {
    private final IBookHelpBaseView mView;
    private final String mHelpBeanId;
    private int mAddStart;

    public BookHelpDetialPresenter(IBookHelpBaseView mView, String mHelpBeanId) {

        this.mView = mView;
        this.mHelpBeanId = mHelpBeanId;
    }

    @Override
    public void getData() {
        getBookHelpDetail();
        getBestComments();
        getBookHelpComments();
    }

    private void getBookHelpComments() {
        mAddStart=0;
        RetrofitService.getBookReviewComments(mHelpBeanId, IBookApi.start + "",IBookApi.limit+ "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

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
                    public void onNext(CommentList list) {
                        mView.loadBookHelpComments(list);
                        mAddStart = mAddStart +list.comments.size();
                    }
                });
    }



    private void getBestComments() {
        RetrofitService.getBestComments(mHelpBeanId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

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
                    public void onNext(CommentList list) {
                        mView.loadBestComments(list);
                    }
                });
    }

    private void getBookHelpDetail() {
        RetrofitService.getBookHelpDetail(mHelpBeanId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookHelp>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                            @Override
                            public void onRetry() {
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
                    public void onNext(BookHelp bookHelp) {
                        mView.loadBookHelpDetail(bookHelp);
                    }
                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getBookReviewComments(mHelpBeanId, IBookApi.start+mAddStart + "",IBookApi.limit+ "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CommentList list) {
                        mView.loadMoreBookHelpComments(list);
                        mAddStart = mAddStart +list.comments.size();
                    }
                });
    }
}
