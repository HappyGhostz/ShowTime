package com.example.rumens.showtime.api.bean;

/**
 * @author Zhaochen Ping
 * @create 2017/5/4
 * @description
 */

public class OldLiveVideoInfo {
    /**
     * error : 0
     * data : {"rtmp_cdn":"ws","room_id":"79631","room_name":"明皇直播：韩国训练第6天播到11点","rateSwitch":1,"hls_url":"http://hls3.douyucdn.cn/live/79631rwT9hvGtWgJ/playlist.m3u8?wsSecret=f20b8082924172fbd5cab393335e0402&wsTime=1489036472","live_url":"http://hdl3.douyucdn.cn/live/79631rwT9hvGtWgJ.flv?wsAuth=4484fe0cb51acf407d640e017b85d42c&token=cpn-pcclient-905619-79631-5189de0c9dc4e4e5f942a9b2a507f355&logo=0&expire=0"}
     */

    private int error;
    /**
     * rtmp_cdn : ws
     * room_id : 79631
     * room_name : 明皇直播：韩国训练第6天播到11点
     * rateSwitch : 1
     * hls_url : http://hls3.douyucdn.cn/live/79631rwT9hvGtWgJ/playlist.m3u8?wsSecret=f20b8082924172fbd5cab393335e0402&wsTime=1489036472
     * live_url : http://hdl3.douyucdn.cn/live/79631rwT9hvGtWgJ.flv?wsAuth=4484fe0cb51acf407d640e017b85d42c&token=cpn-pcclient-905619-79631-5189de0c9dc4e4e5f942a9b2a507f355&logo=0&expire=0
     */

    private DataEntity data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String rtmp_cdn;
        private String room_id;
        private String room_name;
        private int rateSwitch;
        private String hls_url;
        private String live_url;

        public String getRtmp_cdn() {
            return rtmp_cdn;
        }

        public void setRtmp_cdn(String rtmp_cdn) {
            this.rtmp_cdn = rtmp_cdn;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public int getRateSwitch() {
            return rateSwitch;
        }

        public void setRateSwitch(int rateSwitch) {
            this.rateSwitch = rateSwitch;
        }

        public String getHls_url() {
            return hls_url;
        }

        public void setHls_url(String hls_url) {
            this.hls_url = hls_url;
        }

        public String getLive_url() {
            return live_url;
        }

        public void setLive_url(String live_url) {
            this.live_url = live_url;
        }

        @Override
        public String toString() {
            return "{" +
                    "rtmp_cdn:'" + rtmp_cdn + '\'' +
                    ", room_id:'" + room_id + '\'' +
                    ", room_name:'" + room_name + '\'' +
                    ", rateSwitch:" + rateSwitch +
                    ", hls_url:'" + hls_url + '\'' +
                    ", live_url:'" + live_url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                "error:" + error +
                ", data:" + data +
                '}';
    }
}
