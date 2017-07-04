package com.example.rumens.showtime.music.searchmusic;

import com.example.rumens.showtime.api.bean.SearchMusic;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.reader.booksearch.ISearchBaseView;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */

public class SearchMusicPresent implements IBasePresenter {
    private final ISearchView mView;
    private final String searchMusicName;
    private int limit = 30;
    private int offset=0;

    public SearchMusicPresent(ISearchView mView, String searchMusicName) {
        this.mView = mView;
        this.searchMusicName = searchMusicName;
    }

    @Override
    public void getData() {
        offset=0;
        RetrofitService.getSearchMusicList(searchMusicName,limit,offset)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<SearchMusic>bindToLife())
                .subscribe(new Subscriber<SearchMusic>() {
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
                    public void onNext(SearchMusic searchMusic) {
                        mView.loadSearchMusicList(searchMusic);
                        offset++;
                    }
                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getSearchMusicList(searchMusicName,limit,offset)
                .compose(mView.<SearchMusic>bindToLife())
                .subscribe(new Subscriber<SearchMusic>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadNoSearchMusicList();
                    }

                    @Override
                    public void onNext(SearchMusic searchMusic) {
                        mView.loadMoreSearchMusicList(searchMusic);
                        offset++;
                    }
                });
    }
}
