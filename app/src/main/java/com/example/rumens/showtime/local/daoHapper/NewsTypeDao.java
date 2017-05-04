package com.example.rumens.showtime.local.daoHapper;

import android.content.Context;

import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.local.NewsTypeInfo;
import com.example.rumens.showtime.local.NewsTypeInfoDao;
import com.example.rumens.showtime.utils.AssetsHelper;
import com.example.rumens.showtime.utils.GsonHelper;

import java.util.List;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */

public class NewsTypeDao {
    // 所有栏目
    private static List<NewsTypeInfo> sAllChannels;


    private NewsTypeDao() {
    }

    /**
     * 更新本地数据，如果数据库新闻列表栏目为 0 则添加头 3 个栏目
     * @param context
     * @param daoSession
     */
    public static void updateLocalData(Context context, DaoSession daoSession) {
        sAllChannels = GsonHelper.convertEntities(AssetsHelper.readData(context, "NewsChannel"), NewsTypeInfo.class);
        NewsTypeInfoDao beanDao = daoSession.getNewsTypeInfoDao();
        if (beanDao.count() == 0) {
            beanDao.insertInTx(sAllChannels.subList(0, 5));
        }
    }

    /**
     * 获取所有栏目
     * @return
     */
    public static List<NewsTypeInfo> getAllChannels() {
        return sAllChannels;
    }
}
