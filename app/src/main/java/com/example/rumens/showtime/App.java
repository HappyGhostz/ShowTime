package com.example.rumens.showtime;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.rumens.showtime.inject.component.AppComponent;
import com.example.rumens.showtime.inject.component.DaggerAppComponent;
import com.example.rumens.showtime.inject.modules.AppModule;
import com.example.rumens.showtime.local.DaoMaster;
import com.example.rumens.showtime.local.DaoSession;
import com.example.rumens.showtime.local.daoHapper.NewsTypeDao;
import com.example.rumens.showtime.rxBus.RxBus;
import com.example.rumens.showtime.utils.RetrofitService;
import com.example.rumens.showtime.utils.SharedPreferencesUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import org.greenrobot.greendao.database.Database;


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
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        application = new App();
        mContext = getApplicationContext();
        sContext = getApplication();
        initDataDao();
        initInhect();
        initConfig();
        initTbs();
    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //TbsDownloader.needDownload(getApplicationContext(), false);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app","onDownloadFinish is " + i);
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app","onInstallFinish is " + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app","onDownloadProgress:"+i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(),  cb);
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
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);

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

    public static Context getAppContext(){
        return mContext;
    }

}
