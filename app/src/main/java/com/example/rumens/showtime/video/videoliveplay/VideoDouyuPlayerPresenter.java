package com.example.rumens.showtime.video.videoliveplay;

import android.util.Log;

import com.example.rumens.showtime.api.bean.DouyuLiveListItemBean;
import com.example.rumens.showtime.api.bean.OldLiveVideoInfo;
import com.example.rumens.showtime.base.IVideoPresenter;
import com.example.rumens.showtime.utils.MD5Util;
import com.example.rumens.showtime.widget.EmptyErrLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author Zhaochen Ping
 * @create 2017/5/4
 * @description
 */

public class VideoDouyuPlayerPresenter implements IVideoPresenter {
    private final IVideoView mView;
    private final DouyuLiveListItemBean.DataBean mDouyuData;
    private final String mDouyuType;

    public VideoDouyuPlayerPresenter(IVideoView mView, DouyuLiveListItemBean.DataBean mDouyuData, String mDouyuType) {
        this.mView = mView;
        this.mDouyuData = mDouyuData;
        this.mDouyuType = mDouyuType;
    }

    @Override
    public void getData() {
        String roomId = mDouyuData.getRoom_id();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        client.newCall(getRequest(roomId)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("error",e.getMessage()+"---");
                mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                    @Override
                    public void onRetry() {
                        getData();
                    }
                });
            }
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                String json =response.body().string().toString();
                Log.e("onResponse",json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getInt("error")==0) {
                        Gson gson = new Gson();
                        OldLiveVideoInfo mLiveVideoInfo = gson.fromJson(json, OldLiveVideoInfo.class);
                        mView.loadLiveDouyuData(mLiveVideoInfo);
                    } else {
                        mView.showNetError(new EmptyErrLayout.OnRetryListener() {
                            @Override
                            public void onRetry() {
                                getData();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Request getRequest(String roomId) {
        /**
         * 房间加密处理
         */
        int time = (int)(System.currentTimeMillis() / 1000) ;
        String str = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
        String auth = MD5Util.getToMd5Low32(str);
//        L.e("地址为:"+NetWorkApi.baseUrl + NetWorkApi.getLiveVideo + room_id+"?"+tempParams.toString());
        Request requestPost = new Request.Builder()
                .url("http://coapi.douyucdn.cn/lapi/live/thirdPart/getPlay/"+ roomId + "?rate=0")
                .get()
                .addHeader("aid","pcclient")
                .addHeader("auth",auth)
                .addHeader("time",time+"")
                .build();
        return requestPost;
    }

    @Override
    public void getMoreData() {

    }
}
