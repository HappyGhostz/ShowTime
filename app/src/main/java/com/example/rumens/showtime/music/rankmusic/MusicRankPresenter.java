package com.example.rumens.showtime.music.rankmusic;

import com.example.rumens.showtime.api.IMusicsApi;
import com.example.rumens.showtime.api.bean.RankingListItem;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicRankPresenter implements IBasePresenter {
    private IMusicRankView mView;

    public MusicRankPresenter(IMusicRankView mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {
        RetrofitService.getRankMusicListAll(IMusicsApi.MUSIC_URL_FORMAT,IMusicsApi.MUSIC_URL_FROM,
                IMusicsApi.MUSIC_URL_METHOD_RANKINGLIST,IMusicsApi.MUSIC_URL_RANKINGLIST_FLAG)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<RankingListItem>bindToLife())
                .subscribe(new Subscriber<RankingListItem>() {
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
                    public void onNext(RankingListItem rankingListItem) {
                        List<RankingListItem.RangkingDetail> detailList = rankingListItem.getContent();
                        mView.loadListMusic(detailList);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
