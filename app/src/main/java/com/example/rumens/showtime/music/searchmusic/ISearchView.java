package com.example.rumens.showtime.music.searchmusic;

import com.example.rumens.showtime.api.bean.SearchMusic;
import com.example.rumens.showtime.base.IBaseView;

/**
 * @author Zhao Chenping
 * @creat 2017/7/3.
 * @description
 */

public interface ISearchView extends IBaseView{
    void loadSearchMusicList(SearchMusic searchMusic);

    void loadMoreSearchMusicList(SearchMusic searchMusic);

    void loadNoSearchMusicList();
}
