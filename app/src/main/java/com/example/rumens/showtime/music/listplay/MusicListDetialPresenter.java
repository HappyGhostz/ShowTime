package com.example.rumens.showtime.music.listplay;

import com.example.rumens.showtime.api.IMusicsApi;
import com.example.rumens.showtime.api.bean.SongListDetail;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * @author Zhaochen Ping
 * @create 2017/5/26
 * @description
 */

public class MusicListDetialPresenter implements IBasePresenter {
    private final IBaseMusicListView mView;
    private final String songListid;

    public MusicListDetialPresenter(IBaseMusicListView mView, String songListid) {
        this.mView = mView;
        this.songListid = songListid;
    }

    @Override
    public void getData() {
        RetrofitService.getMusicListDetialAll(IMusicsApi.MUSIC_URL_FORMAT, IMusicsApi.MUSIC_URL_FROM,
                IMusicsApi.MUSIC_URL_METHOD_SONGLIST_DETAIL, songListid)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<SongListDetail>bindToLife())
                .subscribe(new Subscriber<SongListDetail>() {
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
                    public void onNext(SongListDetail songListDetail) {
                        mView.loadMusicListDetial(songListDetail);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
