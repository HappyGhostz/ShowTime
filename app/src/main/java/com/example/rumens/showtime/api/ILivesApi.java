package com.example.rumens.showtime.api;





import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.LiveBaseBean;
import com.example.rumens.showtime.api.bean.LiveDetailBean;
import com.example.rumens.showtime.api.bean.LiveListItemBean;
import com.example.rumens.showtime.api.bean.LivePandaBean;
import com.example.rumens.showtime.local.VideoInfo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by long on 2016/8/22.
 * API 接口
 */
public interface ILivesApi {
    int LIMIT = 20;
    int DOUYU_LIMIT = 30;

    //请求获取不同游戏的直播列表
    @GET("/api/live/list/")
    Observable<LiveBaseBean<List<LiveListItemBean>>> getLiveList(
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("live_type") String live_type,
            @Query("game_type") String game_type
    );

    //请求获取直播详情
    @GET("/api/live/detail/")
    Observable<LiveBaseBean<LiveDetailBean>> getLiveDetail(
            @Query("live_type") String live_type,
            @Query("live_id") String live_id,
            @Query("game_type") String game_type
    );

    //请求获取弹幕聊天室详情
    @GET("/ajax_chatinfo")
    Observable<LivePandaBean> getPandaChatroom(
            @Query("roomid") String roomid
    );

}
