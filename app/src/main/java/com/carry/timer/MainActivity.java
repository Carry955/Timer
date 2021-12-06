package com.carry.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bt_timer)
    Button bt_timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    int c_count;
    Handler handler = new Handler();
    private Runnable jumpThread = new Runnable() {
        @Override
        public void run() {
            if(c_count < 0){

            }else{
                bt_timer.setText("倒计时：" + c_count);
                c_count--;
                handler.postDelayed(jumpThread, 1000L);
            }
        }
    };
    @OnClick(R.id.bt_1)
    void bt_1(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i=5; i>=0; i--){
                    final int j=i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bt_timer.setText("倒计时："+j);
                        }
                    });
                    SystemClock.sleep(1000L);
                }
            }
        }.start();
        Toast.makeText(this,"THREAD",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.bt_2)
    public void bt_2(){
        c_count = 5;
        handler.post(jumpThread);
        Toast.makeText(this,"POSTDELAYED",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.bt_3)
    public void bt_3(){
        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bt_timer.setText("倒计时："+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
        Toast.makeText(this,"COUNTDOWNTIMER",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.bt_4)
    public void bt_4(){
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int cntstart = 6;
            @Override
            public void run() {
                cntstart--;
                if (cntstart <= 0){
                    timer.cancel();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bt_timer.setText("倒计时："+cntstart);
                    }
                });
                Log.d("TimerTask","TimerTask cntStart"+cntstart);
            }
        };
        timer.schedule(timerTask,0,1000);
        Toast.makeText(this, "HANDLER+TIMER+TIMERTASK",Toast.LENGTH_LONG).show();
    }
}
