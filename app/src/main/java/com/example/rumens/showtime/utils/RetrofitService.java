package com.example.rumens.showtime.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.rumens.showtime.api.IBookApi;
import com.example.rumens.showtime.api.IDouyuApi;
import com.example.rumens.showtime.api.IDouyuVideoApi;
import com.example.rumens.showtime.api.ILivesApi;
import com.example.rumens.showtime.api.INewsApi;
import com.example.rumens.showtime.api.IWelfareApi;
import com.example.rumens.showtime.api.bean.BookDetail;
import com.example.rumens.showtime.api.bean.BookHelp;
import com.example.rumens.showtime.api.bean.BookHelpList;
import com.example.rumens.showtime.api.bean.BookMixATocBean;
import com.example.rumens.showtime.api.bean.BooksByCats;
import com.example.rumens.showtime.api.bean.CategoryList;
import com.example.rumens.showtime.api.bean.ChapterReadBean;
import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.HotReview;
import com.example.rumens.showtime.api.bean.LiveBaseBean;
import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.api.bean.NewsDetailInfo;
import com.example.rumens.showtime.api.bean.NewsInfo;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.api.bean.PhotoInfo;
import com.example.rumens.showtime.api.bean.PhotoSetInfo;
import com.example.rumens.showtime.api.bean.RankingListBean;
import com.example.rumens.showtime.api.bean.Rankings;
import com.example.rumens.showtime.api.bean.Recommend;
import com.example.rumens.showtime.api.bean.RecommendBookList;
import com.example.rumens.showtime.api.bean.SpecialInfo;
import com.example.rumens.showtime.api.bean.WelfarePhotoInfo;
import com.example.rumens.showtime.api.bean.WelfarePhotoList;
import com.example.rumens.showtime.local.BeautyPhoto;
import com.example.rumens.showtime.local.VideoInfo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Zhaochen Ping
 * @create 2017/4/24
 * @description
 */

public class RetrofitService {
    private static final String HEAD_LINE_NEWS = "T1348647909107";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    private static final String NEWS_HOST = "http://c.3g.163.com/";
    private static final String WELFARE_HOST = "http://gank.io/";
    public static final String BASE_USER_URL = "http://api.douban.com/v2/movie/";
    private static final String BASE_EXPLORE_URL = "http://c.m.163.com";
    private static final String BASE_LIVE_URL = "http://api.maxjia.com";
    private static final String BASE_PANDA_URL = "http://www.panda.tv";
    private static final String BASE_DOUYU_URL = "http://capi.douyucdn.cn/api/v1/";
    private static final String BASE_LIVE_DOUYU_VIDEO_URL ="http://coapi.douyucdn.cn/" ;
    private static final String BASE_BOOKE_URL ="http://api.zhuishushenqi.com" ;

    private static INewsApi sNewsService;
    private static IWelfareApi sWelfareService;
    private static ILivesApi sLivesService;
    private static IDouyuApi sDouyuLivesService;
    private static IDouyuVideoApi sDouyuLiveVideoService;
    private static IBookApi sBookService;
    // 递增页码
    private static final int INCREASE_PAGE = 20;
    private static Context mContext;


    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务
     * @param mContext
     */
    public static void init(Context mContext) {
        RetrofitService.mContext = mContext;
        File cacheDir = mContext.getCacheDir();
        File file = new File(cacheDir, "HttpCache");
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(file,
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
                .addInterceptor(sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NEWS_HOST)
                .build();
        sNewsService = retrofit.create(INewsApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(WELFARE_HOST)
                .build();
        sWelfareService = retrofit.create(IWelfareApi.class);

//        retrofit = new Retrofit.Builder()
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl(BASE_PANDA_URL)
//                .build();
//        sLivesService = retrofit.create(ILivesApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_LIVE_URL)
                .build();
        sLivesService = retrofit.create(ILivesApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_DOUYU_URL)
                .build();
        sDouyuLivesService = retrofit.create(IDouyuApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_LIVE_DOUYU_VIDEO_URL)
                .build();
        sDouyuLiveVideoService = retrofit.create(IDouyuVideoApi.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_BOOKE_URL)
                .build();
        sBookService = retrofit.create(IBookApi.class);


    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(mContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Log.e("RetrofitService","no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(mContext)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Log.d("LogTAG", "request.body() == null");
            }
            //打印url信息
//            Log.i(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            System.out.println(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /************************************ API *******************************************/
    /**
     * 获取直播列表
     */
    public static Observable<LiveBaseBean<List<LiveListItemBean>>> getLiveList(int offset, int limit, String liveType, String gameType){
        return sLivesService.getLiveList(offset,limit,liveType,gameType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
    /**
     * 获取直播列表
     */
    public static Observable<LiveBaseBean<LiveDetailBean>>getLiveDetail(String liveType, String liveId, String gameType){
        return sLivesService.getLiveDetail(liveType,liveId,gameType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     *获取斗鱼直播列表
     */
    public static Observable<DouyuLiveListItemBean> getDouyuLiveList(int offset, int limit, String gameType) {
        return sDouyuLivesService.getDouyuLiveList(gameType,offset,limit,"android")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取新闻列表
     * @return
     */
    public static Observable<NewsInfo> getNewsList(String newsId, int page) {
        String type;
        if (newsId.equals(HEAD_LINE_NEWS)) {
            type = "headline";
        } else {
            type = "list";
        }
        return sNewsService.getNewsList(type, newsId, page * INCREASE_PAGE)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapNews(newsId));
    }

    /**
     * 获取专题数据
     * @param specialId
     * @return
     */
    public static Observable<SpecialInfo> getSpecial(String specialId) {
        return sNewsService.getSpecial(specialId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapSpecial(specialId));
    }

    /**
     * 获取新闻详情
     * @param newsId 新闻ID
     * @return
     */
    public static Observable<NewsDetailInfo> getNewsDetail(final String newsId) {
        return sNewsService.getNewsDetail(newsId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Map<String, NewsDetailInfo>, Observable<NewsDetailInfo>>() {
                    @Override
                    public Observable<NewsDetailInfo> call(Map<String, NewsDetailInfo> newsDetailMap) {
                        return Observable.just(newsDetailMap.get(newsId));
                    }
                });
    }

    /**
     * 获取图集
     * @param photoId 图集ID
     * @return
     */
    public static Observable<PhotoSetInfo> getPhotoSet(String photoId) {
        return sNewsService.getPhotoSet(StringUtils.clipPhotoSetId(photoId))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取图片列表
     * @return
     */
    public static Observable<List<PhotoInfo>> getPhotoList() {
        return sNewsService.getPhotoList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取更多图片列表
     * @return
     */
    public static Observable<List<PhotoInfo>> getPhotoMoreList(String setId) {
        return sNewsService.getPhotoMoreList(setId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取美女图片
     * 注: 因为网易这个原接口参数一大堆，我只传了部分参数，返回的数据会出现图片重复的情况，请不要在意这个问题- -
     * @return
     */
    public static Observable<List<BeautyPhoto>> getBeautyPhoto(int page) {
        return sNewsService.getBeautyPhoto(page * INCREASE_PAGE)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapPhotos());
    }

    /**
     * 获取福利图片
     * @return
     */
    public static Observable<WelfarePhotoInfo> getWelfarePhoto(int page) {
        return sWelfareService.getWelfarePhoto(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapWelfarePhotos());
    }

    /**
     * 获取视频列表
     * @return
     */
    public static Observable<List<VideoInfo>> getVideoList(String videoId, int page) {
        return sNewsService.getVideoList(videoId, page * INCREASE_PAGE / 2)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapVideo(videoId));
    }
    /**
     * 获取斗鱼直播房间详情列
     * @return
     */
    public static Observable<OldLiveVideoInfo> getDouyuLiveVideoInfo(String roomId,String auth,String time){
        return sDouyuLiveVideoService.getDouyuLiveVideo(roomId,0,"pcclient",auth,time)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 获取书架列表
     */
    public static Observable<Recommend> getBookRackListInfo(String gender){
        return sBookService.getRecomend(gender);

    }
    /**
     * 获取社区书荒区列表
     */
    public static Observable<BookHelpList>getBookHelpListInfo(String start, String limit){
        return sBookService.getBookHelpList("all","updated",start,limit,"");
    }
    /**
     * 获取分类
     */
    public static Observable<CategoryList>getBookClassifyInfo(){
        return sBookService.getCategoryList();
    }
    /**
     * 获取所有排行榜
     */
    public static Observable<RankingListBean>getBookRankInfo(){
        return sBookService.getRanking();
    }
    /**
     * 获取正版源
     */
    public static Observable<BookMixATocBean>getBookMixATocInfo(String bookId, String view){
        return sBookService.getBookMixAToc(bookId,view);
    }
    /**
     * 获取章节内容
     */
    public static Observable<ChapterReadBean>getBookChapterInfo(String url){
        return sBookService.getChapterRead(url);
    }

    /**
     * 获取分类列表
     * @param mSex
     * @param mType
     * @param mMajor
     * @param mMinor
     * @param start
     * @param limit
     * @return
     */
    public static Observable<BooksByCats> getBooksByCatsInfo(String mSex, String mType, String mMajor, String mMinor, int start, int limit) {
        return sBookService.getBooksByCats(mSex,mType,mMajor,mMinor,start,limit);
    }

    /**
     * 获取书籍详情
     * @param mBookId
     * @return
     */
    public static Observable<BookDetail> getBookDetailInfo(String mBookId) {
        return sBookService.getBookDetail(mBookId);
    }

    public static Observable<HotReview> getHotReview(String mBookId) {
        return sBookService.getHotReview(mBookId);
    }

    public static Observable<RecommendBookList> getRecommendBookList(String mBookId, String limit) {
        return sBookService.getRecommendBookList(mBookId,limit);
    }

    public static Observable<BookHelp> getBookHelpDetail(String mHelpBeanId) {
        return sBookService.getBookHelpDetail(mHelpBeanId);
    }

    public static Observable getBestComments(String mHelpBeanId) {
        return sBookService.getBestComments(mHelpBeanId);
    }

    public static Observable getBookReviewComments(String mHelpBeanId, String start, String limit) {
        return sBookService.getBookReviewComments(mHelpBeanId,start,limit);
    }

    public static Observable<Rankings> getRanking(String mRankType) {
        return sBookService.getRanking(mRankType);
    }

    public static Observable getSearchResult(String mBookName) {
        return sBookService.searchBooks(mBookName);
    }


    /******************************************* 转换器 **********************************************/

    /**
     * 转换器，因为 Key 是动态变动，所以用这种不太合适
     * @param <T>
     */
    @Deprecated
    public static class FlatMapTransformer<T> implements Observable.Transformer<Map<String, List<T>>, T> {

        private String mMapKey;

        public FlatMapTransformer<T> setMapKey(String mapKey) {
            mMapKey = mapKey;
            return this;
        }

        @Override
        public Observable<T> call(Observable<Map<String, List<T>>> mapObservable) {
            return  mapObservable.flatMap(new Func1<Map<String, List<T>>, Observable<T>>() {
                @Override
                public Observable<T> call(Map<String, List<T>> stringListMap) {
                    if (TextUtils.isEmpty(mMapKey)) {
                        return Observable.error(new Throwable("Map Key is empty"));
                    }
                    return Observable.from(stringListMap.get(mMapKey));
                }
            }).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
    /**
     * 类型转换
     * @param liveType zhibo类型
     * @return
     */
    private static Func1<Map<String, List<LiveListItemBean>>, Observable<List<LiveListItemBean>>> flatMapLives(final String liveType) {
        return new Func1<Map<String, List<LiveListItemBean>>, Observable<List<LiveListItemBean>>>() {
            @Override
            public Observable<List<LiveListItemBean>> call(Map<String, List<LiveListItemBean>> stringListMap) {
                return Observable.just(stringListMap.get(liveType));
            }
        };
    }

    /**
     * 类型转换
     * @param typeStr 新闻类型
     * @return
     */
    private static Func1<Map<String, List<NewsInfo>>, Observable<NewsInfo>> _flatMapNews(final String typeStr) {
        return new Func1<Map<String, List<NewsInfo>>, Observable<NewsInfo>>() {
            @Override
            public Observable<NewsInfo> call(Map<String, List<NewsInfo>> newsListMap) {
                return Observable.from(newsListMap.get(typeStr));
            }
        };
    }

    /**
     * 类型转换
     * @param typeStr 视频类型
     * @return
     */
    private static Func1<Map<String, List<VideoInfo>>, Observable<List<VideoInfo>>> _flatMapVideo(final String typeStr) {
        return new Func1<Map<String, List<VideoInfo>>, Observable<List<VideoInfo>>>() {
            @Override
            public Observable<List<VideoInfo>> call(Map<String, List<VideoInfo>> newsListMap) {
                return Observable.just(newsListMap.get(typeStr));
            }
        };
    }

    /**
     * 类型转换
     * @param specialId 专题id
     * @return
     */
    private static Func1<Map<String, SpecialInfo>, Observable<SpecialInfo>> _flatMapSpecial(final String specialId) {
        return new Func1<Map<String, SpecialInfo>, Observable<SpecialInfo>>() {
            @Override
            public Observable<SpecialInfo> call(Map<String, SpecialInfo> specialMap) {
                return Observable.just(specialMap.get(specialId));
            }
        };
    }

    /**
     * 类型转换
     * @return
     */
    private static Func1<Map<String, List<BeautyPhoto>>, Observable<List<BeautyPhoto>>> _flatMapPhotos() {
        return new Func1<Map<String, List<BeautyPhoto>>, Observable<List<BeautyPhoto>>>() {
            @Override
            public Observable<List<BeautyPhoto>> call(Map<String, List<BeautyPhoto>> newsListMap) {
                return Observable.just(newsListMap.get("美女"));
            }
        };
    }

    /**
     * 类型转换
     * @return
     */
    private static Func1<WelfarePhotoList, Observable<WelfarePhotoInfo>> _flatMapWelfarePhotos() {
        return new Func1<WelfarePhotoList, Observable<WelfarePhotoInfo>>() {
            @Override
            public Observable<WelfarePhotoInfo> call(WelfarePhotoList welfarePhotoList) {
                if (welfarePhotoList.getResults().size() == 0) {
                    return Observable.empty();
                }
                return Observable.from(welfarePhotoList.getResults());
            }
        };
    }
}
