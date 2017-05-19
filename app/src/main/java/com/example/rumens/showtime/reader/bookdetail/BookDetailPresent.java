package com.example.rumens.showtime.reader.bookdetail;

import com.example.rumens.showtime.api.bean.BookDetail;
import com.example.rumens.showtime.api.bean.HotReview;
import com.example.rumens.showtime.api.bean.RecommendBookList;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/5/16
 * @description
 */

public class BookDetailPresent implements IBasePresenter {
    private final BookDetailBaseView mView;
    private final String mBookId;

    public BookDetailPresent(BookDetailBaseView mView, String mBookId) {
        this.mView = mView;
        this.mBookId = mBookId;
    }

    @Override
    public void getData() {
        RetrofitService.getBookDetailInfo(mBookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetail>() {
                    @Override
                    public void onNext(BookDetail data) {
                        if (data != null && mView != null) {
                            mView.loadBookDetail(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        RetrofitService.getHotReview(mBookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onNext(HotReview data) {
                        List<HotReview.Reviews> list = data.reviews;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.loadHotReview(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        RetrofitService.getRecommendBookList(mBookId, "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendBookList>() {
                    @Override
                    public void onNext(RecommendBookList data) {
                        List<RecommendBookList.RecommendBook> list = data.booklists;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.loadRecommendBookList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
