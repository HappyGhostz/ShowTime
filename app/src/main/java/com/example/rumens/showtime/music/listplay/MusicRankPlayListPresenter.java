package com.example.rumens.showtime.music.listplay;

import com.example.rumens.showtime.api.IMusicsApi;
import com.example.rumens.showtime.api.bean.RankingListDetail;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public class MusicRankPlayListPresenter implements IBasePresenter {
    private final IBaseRankListPlayView mView;
    private final int mType;

    public MusicRankPlayListPresenter(IBaseRankListPlayView mView, int mType) {
        this.mView = mView;
        this.mType = mType;
    }

    @Override
    public void getData() {
        String mFields=encode("song_id,title,author,album_title,pic_big,pic_small,havehigh,all_rate,charge,has_mv_mobile,learn,song_source,korean_bb_song");
        RetrofitService.getRankPlayList(IMusicsApi.MUSIC_URL_FORMAT, IMusicsApi.MUSIC_URL_FROM,
                IMusicsApi.MUSIC_URL_METHOD_RANKING_DETAIL, mType, 0, 100, mFields)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<RankingListDetail>bindToLife())
                .subscribe(new Subscriber<RankingListDetail>() {
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
                    public void onNext(RankingListDetail rankingListDetail) {
                        mView.loadRankPlayList(rankingListDetail);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
    public String encode(String encode){
        if (encode == null) return "";

        try {
            return URLEncoder.encode(encode, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }
}
