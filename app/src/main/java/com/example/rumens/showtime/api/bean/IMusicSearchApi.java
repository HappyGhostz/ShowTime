package com.example.rumens.showtime.api.bean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */

public interface IMusicSearchApi {
    //网易音乐搜索http://s.music.163.com/search/get?type=1&s=白狐&limit=10&%20offset=0
    @GET("get")
    Observable<SearchMusic> getSearchMusic(@Query("type") int type,
                                           @Query("s") String musicName,
                                           @Query("limit") int limit,
                                           @Query("offset") int offset);
}
