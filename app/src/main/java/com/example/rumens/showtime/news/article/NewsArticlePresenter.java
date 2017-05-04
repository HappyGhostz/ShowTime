package com.example.rumens.showtime.news.article;

import com.example.rumens.showtime.api.bean.NewsDetailInfo;
import com.example.rumens.showtime.base.IBasePresenter;
import com.example.rumens.showtime.utils.ListUtils;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.widget.EmptyErrLayout;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Zhaochen Ping
 * @create 2017/4/25
 * @description
 */

public class NewsArticlePresenter implements IBasePresenter {
    private static final String HTML_IMG_TEMPLATE = "<img src=\"http\" />";
    private final String mNewsId;
    private final INewsArticleView mView;

    public NewsArticlePresenter(String mNewsId, INewsArticleView  mView) {
        this.mNewsId = mNewsId;
        this.mView = mView;
    }

    @Override
    public void getData() {
        RetrofitService.getNewsDetail(mNewsId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .doOnNext(new Action1<NewsDetailInfo>() {
                    @Override
                    public void call(NewsDetailInfo newsDetailInfo) {
                        handlRichTextWithImg(newsDetailInfo);
                    }
                })
                .compose(mView.<NewsDetailInfo>bindToLife())
                .subscribe(new Subscriber<NewsDetailInfo>() {
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
                    public void onNext(NewsDetailInfo newsDetailInfo) {
                        mView.loadData(newsDetailInfo);
                    }
                });
    }

    private void handlRichTextWithImg(NewsDetailInfo newsDetailInfo) {
        if (!ListUtils.isEmpty(newsDetailInfo.getImg())) {
            String body = newsDetailInfo.getBody();
            for (NewsDetailInfo.ImgEntity imgEntity : newsDetailInfo.getImg()) {
                String ref = imgEntity.getRef();
                String src = imgEntity.getSrc();
                String img = HTML_IMG_TEMPLATE.replace("http", src);
                body = body.replaceAll(ref, img);
            }
            newsDetailInfo.setBody(body);
        }
    }

    @Override
    public void getMoreData() {

    }
}
