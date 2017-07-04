package com.example.rumens.showtime.api.bean;

import java.util.List;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */

public class SearchMusic {

    /**
     * result : {"songCount":793,"songs":[{"id":30871322,"name":"白狐","artists":[{"id":10096,"name":"谢容儿","picUrl":null}],"album":{"id":3105514,"name":"白狐（蜀门版）","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/42KztZ0Ucbe_Ee-3JgmDEA==/7899991046015815.jpg"},"audio":"http://m2.music.126.net/gU8be87A3wBUaXFQdSxbxA==/7796636952981646.mp3","djProgramId":0,"page":"http://music.163.com/m/song/30871322"},{"id":212644,"name":"白狐","artists":[{"id":7228,"name":"陈瑞","picUrl":null}],"album":{"id":21543,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/asHBSEnDkv0Ofogkuq2kkw==/25288767450700.jpg"},"audio":"http://m2.music.126.net/zlHrBCqu1-nvC22Ca5YNWg==/1026943860351290.mp3","djProgramId":0,"page":"http://music.163.com/m/song/212644"},{"id":28310648,"name":"白狐","artists":[{"id":10370,"name":"云菲菲","picUrl":null}],"album":{"id":2771306,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/K7LRdYxIulXEyyPCLmP8Yg==/5958253511029700.jpg"},"audio":"http://m2.music.126.net/zT_RyHHPJYUT_CSkdOsjzA==/6059408580840504.mp3","djProgramId":0,"page":"http://music.163.com/m/song/28310648"},{"id":265828,"name":"白狐","artists":[{"id":8424,"name":"林子路","picUrl":null}],"album":{"id":26364,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/gv350ROQ-1paD05VJr-Ytg==/57174604659870.jpg"},"audio":"http://m2.music.126.net/BEhFgJbSSQ7rnOjebfhRDQ==/2146246697424260.mp3","djProgramId":0,"page":"http://music.163.com/m/song/265828"},{"id":122185,"name":"白狐","artists":[{"id":3752,"name":"李维","picUrl":null}],"album":{"id":11815,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/Fc98R9z4VyvHkNNOaXDAYw==/71468255818201.jpg"},"audio":"http://m2.music.126.net/sO3r54rmZ__vdY-qgJjKdA==/5687773650537315.mp3","djProgramId":0,"page":"http://music.163.com/m/song/122185"},{"id":27908268,"name":"白狐","artists":[{"id":10096,"name":"谢容儿","picUrl":null}],"album":{"id":2693145,"name":"其实我很在乎你","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/auxCH6kZljNlLwsZ_Ew9CQ==/1899956092923081.jpg"},"audio":"http://m2.music.126.net/ZIJox8s2Dd9fumcT1oDtrA==/5773535557457131.mp3","djProgramId":0,"page":"http://music.163.com/m/song/27908268"},{"id":32707379,"name":"白狐","artists":[{"id":7228,"name":"陈瑞","picUrl":null}],"album":{"id":3170282,"name":"把你藏心里","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/QKnlvoFTfFQ7AQnk2ZNJ3g==/2932397513654893.jpg"},"audio":"http://m2.music.126.net/uxc_2XvT7Qf32vS-cNHc3g==/7963762720775254.mp3","djProgramId":0,"page":"http://music.163.com/m/song/32707379"},{"id":212555,"name":"白狐","artists":[{"id":7228,"name":"陈瑞","picUrl":null}],"album":{"id":21538,"name":"女人心","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/vli7d3esXJSeR78EkgnjhQ==/944480488259851.jpg"},"audio":"http://m2.music.126.net/EYDGmflQz9c87JaCE2Mo_w==/996157534773672.mp3","djProgramId":0,"page":"http://music.163.com/m/song/212555"},{"id":81546,"name":"白狐","artists":[{"id":2714,"name":"EDIQ","picUrl":null}],"album":{"id":8048,"name":"朝辞白帝夕至潭柘","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/hmZoNQaqzZALvVp0rE7faA==/0.jpg"},"audio":"http://m2.music.126.net/5TLq-rO7UEysciE1UKBBTg==/5671280976153541.mp3","djProgramId":0,"page":"http://music.163.com/m/song/81546"},{"id":5234424,"name":"白狐","artists":[{"id":8424,"name":"林子路","picUrl":null}],"album":{"id":510841,"name":"3D顶级女声.极致立体特效","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/lA1jyddb6tjG5J-4olKv1A==/28587302334297.jpg"},"audio":"http://m2.music.126.net/Q-k_0hWRoEU2bkjTfgt98A==/1356797348718746.mp3","djProgramId":0,"page":"http://music.163.com/m/song/5234424"}]}
     * code : 200
     */

    private ResultBean result;
    private int code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * songCount : 793
         * songs : [{"id":30871322,"name":"白狐","artists":[{"id":10096,"name":"谢容儿","picUrl":null}],"album":{"id":3105514,"name":"白狐（蜀门版）","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/42KztZ0Ucbe_Ee-3JgmDEA==/7899991046015815.jpg"},"audio":"http://m2.music.126.net/gU8be87A3wBUaXFQdSxbxA==/7796636952981646.mp3","djProgramId":0,"page":"http://music.163.com/m/song/30871322"},{"id":212644,"name":"白狐","artists":[{"id":7228,"name":"陈瑞","picUrl":null}],"album":{"id":21543,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/asHBSEnDkv0Ofogkuq2kkw==/25288767450700.jpg"},"audio":"http://m2.music.126.net/zlHrBCqu1-nvC22Ca5YNWg==/1026943860351290.mp3","djProgramId":0,"page":"http://music.163.com/m/song/212644"},{"id":28310648,"name":"白狐","artists":[{"id":10370,"name":"云菲菲","picUrl":null}],"album":{"id":2771306,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/K7LRdYxIulXEyyPCLmP8Yg==/5958253511029700.jpg"},"audio":"http://m2.music.126.net/zT_RyHHPJYUT_CSkdOsjzA==/6059408580840504.mp3","djProgramId":0,"page":"http://music.163.com/m/song/28310648"},{"id":265828,"name":"白狐","artists":[{"id":8424,"name":"林子路","picUrl":null}],"album":{"id":26364,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/gv350ROQ-1paD05VJr-Ytg==/57174604659870.jpg"},"audio":"http://m2.music.126.net/BEhFgJbSSQ7rnOjebfhRDQ==/2146246697424260.mp3","djProgramId":0,"page":"http://music.163.com/m/song/265828"},{"id":122185,"name":"白狐","artists":[{"id":3752,"name":"李维","picUrl":null}],"album":{"id":11815,"name":"白狐","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/Fc98R9z4VyvHkNNOaXDAYw==/71468255818201.jpg"},"audio":"http://m2.music.126.net/sO3r54rmZ__vdY-qgJjKdA==/5687773650537315.mp3","djProgramId":0,"page":"http://music.163.com/m/song/122185"},{"id":27908268,"name":"白狐","artists":[{"id":10096,"name":"谢容儿","picUrl":null}],"album":{"id":2693145,"name":"其实我很在乎你","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/auxCH6kZljNlLwsZ_Ew9CQ==/1899956092923081.jpg"},"audio":"http://m2.music.126.net/ZIJox8s2Dd9fumcT1oDtrA==/5773535557457131.mp3","djProgramId":0,"page":"http://music.163.com/m/song/27908268"},{"id":32707379,"name":"白狐","artists":[{"id":7228,"name":"陈瑞","picUrl":null}],"album":{"id":3170282,"name":"把你藏心里","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/QKnlvoFTfFQ7AQnk2ZNJ3g==/2932397513654893.jpg"},"audio":"http://m2.music.126.net/uxc_2XvT7Qf32vS-cNHc3g==/7963762720775254.mp3","djProgramId":0,"page":"http://music.163.com/m/song/32707379"},{"id":212555,"name":"白狐","artists":[{"id":7228,"name":"陈瑞","picUrl":null}],"album":{"id":21538,"name":"女人心","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/vli7d3esXJSeR78EkgnjhQ==/944480488259851.jpg"},"audio":"http://m2.music.126.net/EYDGmflQz9c87JaCE2Mo_w==/996157534773672.mp3","djProgramId":0,"page":"http://music.163.com/m/song/212555"},{"id":81546,"name":"白狐","artists":[{"id":2714,"name":"EDIQ","picUrl":null}],"album":{"id":8048,"name":"朝辞白帝夕至潭柘","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/hmZoNQaqzZALvVp0rE7faA==/0.jpg"},"audio":"http://m2.music.126.net/5TLq-rO7UEysciE1UKBBTg==/5671280976153541.mp3","djProgramId":0,"page":"http://music.163.com/m/song/81546"},{"id":5234424,"name":"白狐","artists":[{"id":8424,"name":"林子路","picUrl":null}],"album":{"id":510841,"name":"3D顶级女声.极致立体特效","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/lA1jyddb6tjG5J-4olKv1A==/28587302334297.jpg"},"audio":"http://m2.music.126.net/Q-k_0hWRoEU2bkjTfgt98A==/1356797348718746.mp3","djProgramId":0,"page":"http://music.163.com/m/song/5234424"}]
         */

        private int songCount;
        private List<SongsBean> songs;

        public int getSongCount() {
            return songCount;
        }

        public void setSongCount(int songCount) {
            this.songCount = songCount;
        }

        public List<SongsBean> getSongs() {
            return songs;
        }

        public void setSongs(List<SongsBean> songs) {
            this.songs = songs;
        }

        public static class SongsBean {
            /**
             * id : 30871322
             * name : 白狐
             * artists : [{"id":10096,"name":"谢容儿","picUrl":null}]
             * album : {"id":3105514,"name":"白狐（蜀门版）","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/42KztZ0Ucbe_Ee-3JgmDEA==/7899991046015815.jpg"}
             * audio : http://m2.music.126.net/gU8be87A3wBUaXFQdSxbxA==/7796636952981646.mp3
             * djProgramId : 0
             * page : http://music.163.com/m/song/30871322
             */

            private int id;
            private String name;
            private AlbumBean album;
            private String audio;
            private int djProgramId;
            private String page;
            private List<ArtistsBean> artists;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public AlbumBean getAlbum() {
                return album;
            }

            public void setAlbum(AlbumBean album) {
                this.album = album;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }

            public int getDjProgramId() {
                return djProgramId;
            }

            public void setDjProgramId(int djProgramId) {
                this.djProgramId = djProgramId;
            }

            public String getPage() {
                return page;
            }

            public void setPage(String page) {
                this.page = page;
            }

            public List<ArtistsBean> getArtists() {
                return artists;
            }

            public void setArtists(List<ArtistsBean> artists) {
                this.artists = artists;
            }

            public static class AlbumBean {
                /**
                 * id : 3105514
                 * name : 白狐（蜀门版）
                 * artist : {"id":0,"name":"","picUrl":null}
                 * picUrl : http://p1.music.126.net/42KztZ0Ucbe_Ee-3JgmDEA==/7899991046015815.jpg
                 */

                private int id;
                private String name;
                private ArtistBean artist;
                private String picUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public ArtistBean getArtist() {
                    return artist;
                }

                public void setArtist(ArtistBean artist) {
                    this.artist = artist;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public static class ArtistBean {
                    /**
                     * id : 0
                     * name :
                     * picUrl : null
                     */

                    private int id;
                    private String name;
                    private Object picUrl;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public Object getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(Object picUrl) {
                        this.picUrl = picUrl;
                    }
                }
            }

            public static class ArtistsBean {
                /**
                 * id : 10096
                 * name : 谢容儿
                 * picUrl : null
                 */

                private int id;
                private String name;
                private Object picUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(Object picUrl) {
                    this.picUrl = picUrl;
                }
            }
        }
    }
}
