package com.example.rumens.showtime;

import android.app.Application;
import android.content.Context;

import com.example.rumens.showtime.inject.component.AppComponent;
import com.example.rumens.showtime.inject.component.DaggerAppComponent;
import com.example.rumens.showtime.inject.modules.AppModule;
import com.example.rumens.showtime.local.DaoMaster;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.local.daoHapper.NewsTypeDao;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.utils.RetrofitService;

import org.greenrobot.greendao.database.Database;

import java.io.File;


/**
 * @author Zhaochen Ping
 * @create 2017/4/19
 * @description
 */
public class App extends Application{
    private static final String DB_NAME = "news-db";
    private static Context sContext;
    private static AppComponent sAppComponent;
    private Application application;
    private DaoSession mDaoSession;
    private RxBus mRxBus = new RxBus();
    public Context applicationContext;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        application = new App();
        mContext = getApplicationContext();
        sContext = getApplication();
        initDataDao();
        initInhect();
        initConfig();
    }

    private void initConfig() {
//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(getApplication());
//            Logger.init("LogTAG");
//        }
        RetrofitService.init(mContext);
//        ToastUtils.init(getApplication());
//        DownloaderWrapper.init(mRxBus, mDaoSession.getVideoInfoDao());
//        FileDownloader.init(getApplication());
//        DownloadConfig config = new DownloadConfig.Builder()
//                .setDownloadDir(PreferencesUtils.getSavePath(getApplication()) + File.separator + "video/").build();
//        FileDownloader.setConfig(config);
    }

    private void initDataDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME, null);
        Database database = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
        NewsTypeDao.updateLocalData(this, mDaoSession);
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
    public static Context getContext() {
        return sContext;
    }
    private void initInhect() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this,mDaoSession, mRxBus))
                .build();

    }
    public  Application getApplication() {
        return application;
    }

}
