package com.example.rumens.showtime.api;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Zhaochen Ping
 * @create 2017/5/2
 * @description
 */

public interface IDouyuApi {
    //请求斗鱼的不同游戏的直播列表
    @GET("live/{type}/")
    Observable<DouyuLiveListItemBean> getDouyuLiveList(
            @Path("type") String game_type,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("client_sys")String client
    );
}
