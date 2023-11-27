package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    private ProgressBar pbFirst, pbSecond;
    private TextView tvMsgWorking, tvMsgReturned;
    private boolean isRunning;
    private int MAX_SEC;
    private int intTest;
    private Thread bgThread;
    private Handler handler;
    private Button btnStart,btnsang;

    private void findViewByIds () {
        pbFirst =(ProgressBar) findViewById(R.id.pb_first);
        pbSecond = (ProgressBar) findViewById(R.id.pb_second);
        tvMsgWorking= (TextView) findViewById(R.id.tv_working);
        tvMsgReturned = (TextView) findViewById(R.id.tv_return);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnsang=(Button)findViewById(R.id.btn_sangcau);
    }
    private void initBgThread(){
        bgThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<MAX_SEC&& isRunning;i++){
                        Thread.sleep(1000);
                        Random rnd=new Random();
                        String data="Thread value: "+(int) rnd.nextInt(101);
                        data+=getString(R.string.global_value_seen)+" "+intTest;
                        intTest++;
                        if(isRunning){
                            Message msg=handler.obtainMessage(1,(String)data);
                            handler.sendMessage(msg);
                        }
//                        pbFirst.setProgress(i + 1);
                    }
                    }catch (Throwable t){}
                }
        });
    }

    private void initVariables() {
        isRunning= false;
        intTest = 1;
        MAX_SEC=20;
        pbFirst.setMax (MAX_SEC);
        pbFirst.setProgress (0);

        pbFirst.setVisibility(View.GONE);
        pbSecond.setVisibility(View.GONE);

        handler = new Handler() {
            @Override
            public void handleMessage (Message msg) {
                super.handleMessage (msg);
                String returnedValue = (String) msg.obj;

                tvMsgReturned.setText(getString(R.string.returned_by_bg_thread)+ returnedValue);
                pbFirst.incrementProgressBy(2);

                if(pbFirst.getProgress()==MAX_SEC){
                tvMsgReturned.setText(getString(R.string.done_background_thread_has_been_stopped));
                tvMsgWorking.setText(getString(R.string. done));

                pbFirst.setVisibility(View.GONE);
                pbSecond.setVisibility (View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                isRunning = false;
            } else { tvMsgWorking.setText(getString(R.string.working) + pbFirst.getProgress());
            }}
    };

    }
    @Override
    protected void onStart() {
        super.onStart();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundThread();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopBackgroundThread();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewByIds();
        initVariables();
        initBgThread();


        btnsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }
        });
    }
    private void startBackgroundThread() {
        isRunning = true;
        pbFirst.setVisibility(View.VISIBLE);
        pbSecond.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.GONE);
        bgThread.start();
    }

    private void stopBackgroundThread() {
        isRunning = false;
    }
}