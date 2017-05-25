package com.example.rumens.showtime.music.listmusic;

import com.example.rumens.showtime.api.IMusicsApi;
import com.example.rumens.showtime.api.bean.WrapperSongListInfo;
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

public class MusicListPresenter implements IBasePresenter {
    private IListMusicView mView;
    private int mStartPage= 0;
    private int mPageSize=0;

    public MusicListPresenter(IListMusicView mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {
        mStartPage= 1;
        mPageSize=0;
        RetrofitService.getMusicListAll(IMusicsApi.MUSIC_URL_FORMAT,IMusicsApi.MUSIC_URL_FROM,IMusicsApi.MUSIC_URL_METHOD_GEDAN,IMusicsApi.pageSize,mStartPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<WrapperSongListInfo>bindToLife())
                .subscribe(new Subscriber<WrapperSongListInfo>() {
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
                    public void onNext(WrapperSongListInfo songListInfos) {
                        List<WrapperSongListInfo.SongListInfo> listInfos = songListInfos.getContent();
                        mView.loadListMusic(listInfos);
                        mStartPage++;
                    }
                });

    }

    @Override
    public void getMoreData() {
        RetrofitService.getMusicListAll(IMusicsApi.MUSIC_URL_FORMAT,IMusicsApi.MUSIC_URL_FROM,IMusicsApi.MUSIC_URL_METHOD_GEDAN,IMusicsApi.pageSize,mStartPage)
                .compose(mView.<WrapperSongListInfo>bindToLife())
                .subscribe(new Subscriber<WrapperSongListInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(WrapperSongListInfo songListInfos) {
                        List<WrapperSongListInfo.SongListInfo> listInfos = songListInfos.getContent();
                        mView.loadMoreListMusic(listInfos);
                        mStartPage++;
                    }
                });
    }
}
