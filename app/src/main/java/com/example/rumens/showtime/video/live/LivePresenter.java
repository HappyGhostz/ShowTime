package com.example.rumens.showtime.video.live;

import android.text.TextUtils;

import com.example.rumens.showtime.api.ILivesApi;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveBaseBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * @author Zhaochen Ping
 * @create 2017/4/26
 * @description
 */

public class LivePresenter implements IBasePresenter {
    private final IVideoLiveView mView;
    private String mGameType;
    private String mPlatformType;
    private int mOffSet=0;
    private String isDouyu = "douyu";
    private Observable.Transformer<LiveBaseBean<List<LiveListItemBean>>, List<LiveListItemBean>> mTransform=new Observable.Transformer<LiveBaseBean<List<LiveListItemBean>>, List<LiveListItemBean>>() {
        @Override
        public Observable<List<LiveListItemBean>> call(Observable<LiveBaseBean<List<LiveListItemBean>>> liveBaseBeanObservable) {
            return liveBaseBeanObservable.map(new Func1<LiveBaseBean<List<LiveListItemBean>>, List<LiveListItemBean>>() {
                @Override
                public List<LiveListItemBean> call(LiveBaseBean<List<LiveListItemBean>> listLiveBaseBean) {
                    if(listLiveBaseBean.getStatus().equals("ok")){
                        return listLiveBaseBean.getResult();
                    }
                    return null;
                }

            }).compose(mView.<List<LiveListItemBean>>bindToLife());
        }
    };

    public LivePresenter(IVideoLiveView mView, String mGameType, String mPlatformType) {
        this.mView = mView;
        this.mGameType = mGameType;
        this.mPlatformType = mPlatformType;
    }

    @Override
    public void getData() {
                 mOffSet=0;
        if(TextUtils.equals(mPlatformType,isDouyu)){
            if(TextUtils.equals(mGameType,"live")){
                mGameType="";
            }
            if(TextUtils.equals(mGameType,"hs")){
                mGameType="how";
            }
            if(TextUtils.equals(mGameType,"ow")){
                mGameType="overwatch";
            }
            RetrofitService.getDouyuLiveList(mOffSet,ILivesApi.DOUYU_LIMIT,mGameType)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showLoading();
                        }
                    })
                    .compose(mView.<DouyuLiveListItemBean>bindToLife())
                    .subscribe(new Subscriber<DouyuLiveListItemBean>() {
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
                        public void onNext(DouyuLiveListItemBean douyuLiveListItemBeen) {
                            int error = douyuLiveListItemBeen.getError();
                            List<DouyuLiveListItemBean.DataBean> data = douyuLiveListItemBeen.getData();
                            mView.loadDouyuData(data);
                            mOffSet=mOffSet+ILivesApi.DOUYU_LIMIT;
                        }
                    });


        }else{
            RetrofitService.getLiveList(mOffSet, ILivesApi.LIMIT,mPlatformType,mGameType)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showLoading();
                        }
                    })
                    .compose(mTransform)
                    .subscribe(new Subscriber<List<LiveListItemBean>>() {
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
                        public void onNext(List<LiveListItemBean> liveListItemBeen) {
                            mView.loadData(liveListItemBeen);
                            mOffSet=mOffSet+ILivesApi.LIMIT;
                        }
                    });

        }

    }

    @Override
    public void getMoreData() {
        if(TextUtils.equals(mPlatformType,isDouyu)){
            RetrofitService.getDouyuLiveList(mOffSet,ILivesApi.DOUYU_LIMIT,mGameType)
                    .compose(mView.<DouyuLiveListItemBean>bindToLife())
                    .subscribe(new Subscriber<DouyuLiveListItemBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.loadNoData();
                        }

                        @Override
                        public void onNext(DouyuLiveListItemBean douyuLiveListItemBeen) {
                            List<DouyuLiveListItemBean.DataBean> dataInfos = douyuLiveListItemBeen.getData();
                            mView.loadDouyuMoreData(dataInfos);
                            mOffSet=mOffSet+ILivesApi.DOUYU_LIMIT;
                        }
                    });

        }else {
            RetrofitService.getLiveList(mOffSet,ILivesApi.LIMIT,mPlatformType,mGameType)
                    .compose(mTransform)
                    .subscribe(new Subscriber<List<LiveListItemBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.loadNoData();
                        }

                        @Override
                        public void onNext(List<LiveListItemBean> liveListItemBeen) {
                            mView.loadMoreData(liveListItemBeen);
                            mOffSet=mOffSet+ILivesApi.LIMIT;
                        }
                    });
        }


    }

}
