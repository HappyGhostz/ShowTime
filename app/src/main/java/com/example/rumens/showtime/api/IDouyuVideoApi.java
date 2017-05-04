package com.example.rumens.showtime.api;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Zhaochen Ping
 * @create 2017/5/4
 * @description
 */

public interface IDouyuVideoApi {
    //请求斗鱼的直播视频详情
    @GET("lapi/live/thirdPart/getPlay/{roomId}")
    Observable<OldLiveVideoInfo> getDouyuLiveVideo(
            @Path("roomId") String roomId,
            @Query("rate") int rate,
            @Header("aid") String aid,
            @Header("auth") String auth,
            @Header("time") String time
    );
}
