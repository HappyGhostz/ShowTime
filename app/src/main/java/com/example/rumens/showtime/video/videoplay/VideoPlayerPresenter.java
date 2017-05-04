package com.example.rumens.showtime.video.videoplay;

import android.text.TextUtils;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveBaseBean;
import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.utils.MD5Util;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;


/**
 * @author Zhaochen Ping
 * @create 2017/4/27
 * @description
 */

public class VideoPlayerPresenter implements IVideoPresenter {
    private final IVideoView mView;
    private DouyuLiveListItemBean.DataBean mDouyuData;
    private String mDouyuType;
    private  LiveListItemBean mVideoLiveData;
    private Observable.Transformer<LiveBaseBean<LiveDetailBean>, LiveDetailBean> mTransform = new Observable.Transformer<LiveBaseBean<LiveDetailBean>, LiveDetailBean>() {
        @Override
        public Observable<LiveDetailBean> call(Observable<LiveBaseBean<LiveDetailBean>> liveBaseBeanObservable) {
            return liveBaseBeanObservable.map(new Func1<LiveBaseBean<LiveDetailBean>, LiveDetailBean>() {
                @Override
                public LiveDetailBean call(LiveBaseBean<LiveDetailBean> liveDetailBeanLiveBaseBean) {
                    if(liveDetailBeanLiveBaseBean.getStatus().equals("ok")){
                        return liveDetailBeanLiveBaseBean.getResult();
                    }
                    return null;
                }
            }).compose(mView.<LiveDetailBean>bindToLife());
        }
    };
    public VideoPlayerPresenter(IVideoView mView, LiveListItemBean mVideoLiveData) {
        this.mView = mView;
        this.mVideoLiveData = mVideoLiveData;
    }


    @Override
    public void getData() {

            String gameType = mVideoLiveData.getGame_type();
            String liveId = mVideoLiveData.getLive_id();
            String liveType = mVideoLiveData.getLive_type();
            RetrofitService.getLiveDetail(liveType,liveId,gameType)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showLoading();
                        }
                    })
                    .compose(mTransform)
                    .subscribe(new Subscriber<LiveDetailBean>() {
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
                        public void onNext(LiveDetailBean liveDetailBean) {
                            mView.loadLiveData(liveDetailBean);
                        }
                    });
    }

    @Override
    public void getMoreData() {

    }

}
