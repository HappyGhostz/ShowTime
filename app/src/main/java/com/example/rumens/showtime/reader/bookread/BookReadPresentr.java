package com.example.rumens.showtime.reader.bookread;

import com.example.rumens.showtime.api.bean.BookMixATocBean;
import com.example.rumens.showtime.api.bean.ChapterReadBean;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.utils.RxBookUtil;
import com.example.rumens.showtime.utils.StringUtils;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/5/12
 * @description
 */

public class BookReadPresentr implements IBookReadPresenter {
    private IReadBaseView mView;

    public BookReadPresentr(IReadBaseView mView) {
        this.mView = mView;
    }
    public BookReadPresentr() {
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getBookMixAToc(final String bookId, final String view) {
        String key = StringUtils.creatAcacheKey("book-toc", bookId, view);
        Observable<BookMixATocBean.mixToc> fromNetWork = RetrofitService.getBookMixATocInfo(bookId, view)
                .map(new Func1<BookMixATocBean, BookMixATocBean.mixToc>() {
                    @Override
                    public BookMixATocBean.mixToc call(BookMixATocBean bookMixATocBean) {
                        return bookMixATocBean.mixToc;
                    }
                })
                .compose(RxBookUtil.<BookMixATocBean.mixToc>rxCacheListHelper(key));
        Observable.concat(RxBookUtil.rxCreateDiskObservable(key,BookMixATocBean.mixToc.class),fromNetWork)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BookMixATocBean.mixToc>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                            @Override
                            public void onRetry() {
                                getBookMixAToc(bookId, view);
                            }
                        });
                    }

                    @Override
                    public void onNext(BookMixATocBean.mixToc data) {
                        List<BookMixATocBean.mixToc.Chapters> chapters = data.chapters;
                        if(!chapters.isEmpty()&&chapters!=null&&mView!=null){
                            mView.loadBookToc(chapters);
                        }
                    }
                });
    }

    @Override
    public void getChapterRead(final String url, final int chapter) {
        RetrofitService.getBookChapterInfo(url)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChapterReadBean>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getChapterRead(url,chapter);
                    }

                    @Override
                    public void onNext(ChapterReadBean chapterReadBean) {
                        if (chapterReadBean!=null&&mView!=null){
                            mView.loadChapterRead(chapterReadBean.chapter,chapter);
                        }
                    }
                });
    }
}
