package com.example.rumens.showtime.home;

import android.content.Intent;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rumens.showtime.R;


import java.util.concurrent.TimeUnit;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    private Button mBtNext;
    private boolean isStart =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initView() {
        mBtNext = (Button) findViewById(R.id.downtime);
        mBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.downtime:
                        startHomeActivity();
                        break;
                }
            }
        });
    }

    private void startHomeActivity() {
        if(!isStart){
            isStart=true;
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    private void initData() {
        countDown(5).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                startHomeActivity();
            }

            @Override
            public void onError(Throwable e) {
                startHomeActivity();
            }

            @Override
            public void onNext(Integer integer) {
                mBtNext.setText("倒计时:"+integer);
                mBtNext.setTextColor(Color.parseColor("#000000"));
            }
        });
    }
    private Observable<Integer> countDown(int time){
        final int countTime = time;
        return Observable.interval(0,1,TimeUnit.SECONDS)
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return countTime-aLong.intValue();
                    }
                })
                .take(countTime+1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
