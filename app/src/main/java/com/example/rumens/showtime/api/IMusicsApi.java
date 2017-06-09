package com.example.rumens.showtime.api;

import com.example.rumens.showtime.api.bean.RankingListDetail;
import com.example.rumens.showtime.api.bean.RankingListItem;
import com.example.rumens.showtime.api.bean.SongDetailInfo;
import com.example.rumens.showtime.api.bean.SongListDetail;
import com.example.rumens.showtime.api.bean.WrapperSongListInfo;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Zhaochen Ping
 * @create 2017/5/25
 * @description
 */

public interface IMusicsApi {
    public static final  String MUSIC_URL_FROM = "webapp_music";
    public static final String MUSIC_URL_FORMAT = "json";
    public static final String MUSIC_URL_METHOD_GEDAN ="baidu.ting.diy.gedan";
    public static final String MUSIC_URL_METHOD_RANKINGLIST ="baidu.ting.billboard.billCategory";
    public static final  int MUSIC_URL_RANKINGLIST_FLAG = 1;
    public static final String MUSIC_URL_METHOD_SONGLIST_DETAIL ="baidu.ting.diy.gedanInfo";
    public static final String MUSIC_URL_METHOD_RANKING_DETAIL ="baidu.ting.billboard.billList";
    public static final String MUSIC_URL_FROM_2 = "android";
    public static final String MUSIC_URL_VERSION = "5.6.5.6";
    public static final String MUSIC_URL_METHOD_SONG_DETAIL ="baidu.ting.song.play";
    public static final int  pageSize = 40;
    public static final int startPage=0;

    //获取全部歌单
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    Observable<WrapperSongListInfo> getSongListAll(@Query("format") String format,
                                                   @Query("from") String from,
                                                   @Query("method") String method,
                                                   @Query("page_size") int page_size,
                                                   @Query("page_no") int page_no);

    //获取全部榜单
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    Observable<RankingListItem> getRankingListAll(@Query("format") String format,
                                                  @Query("from") String from,
                                                  @Query("method") String method,
                                                  @Query("kflag") int kflag);

    //获取某个榜单中歌曲信息
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    Observable<RankingListDetail> getRankingListDetail(@Query("format") String format,
                                                       @Query("from") String from,
                                                       @Query("method") String method,
                                                       @Query("type") int type,
                                                       @Query("offset") int offset,
                                                       @Query("size") int size,
                                                       @Query("fields") String fields);

    //获取某个歌单中的信息
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    Observable<SongListDetail> getSongListDetail(@Query("format") String format,
                                                 @Query("from") String from,
                                                 @Query("method") String method,
                                                 @Query("listid") String listid);

    //获取某个歌曲的信息
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    Observable<SongDetailInfo> getSongDetail(@Query("from") String from,
                                             @Query("version") String version,
                                             @Query("format") String format,
                                             @Query("method") String method,
                                             @Query("songid") String songid);
}
