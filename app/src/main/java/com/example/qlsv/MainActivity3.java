//package com.example.qlsv;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//public class MainActivity3 extends AppCompatActivity {
//    private ProgressBar pbWaiting;
//    private TextView tvTopCaption;
//    private EditText etInput;
//    private Button btnExecute;
//    private int globalValue, accum;
//    private long startTime;
//    private final String PATIENCE = "Some important data is being collected now.\nPlease be patient...wait...";
//    private Handler handler;
//    private Runnable fgRunnable, bgRunnable;
//    private Thread testThread;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//
//        handler = new Handler() {
//            @Override
//            public void publish(LogRecord record) {
//
//            }
//
//            @Override
//            public void flush() {
//
//            }
//
//            @Override
//            public void close() throws SecurityException {
//
//            }
//        };
//
//        tvTopCaption = (TextView) findViewById(R.id.tv_top_caption);
//        pbWaiting = (ProgressBar) findViewById(R.id.pb_waiting);
//        etInput = (EditText) findViewById(R.id.et_input);
//        btnExecute = (Button) findViewById(R.id.btn_execute);
//        initVariables();
//
//        btnExecute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String input=etInput.getText().toString();
//                Toast.makeText(MainActivity3.this, "You said "+input, Toast.LENGTH_SHORT).show();
//            }
//        });
//        testThread.start();
//        pbWaiting.incrementProgressBy(0);
//    }
//    private void initVariables(){
//        globalValue = 0;
//        accum = 0;
//        startTime = System.currentTimeMillis();
//        fgRunnable=new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    int progressStep=5;
//                    double totalTime=(System.currentTimeMillis()-startTime)/1000;
//                    synchronized (this){
//                        globalValue+=100;
//                    }
//                    tvTopCaption.setText(PATIENCE+totalTime+" - "+globalValue);
//                    pbWaiting.incrementProgressBy(progressStep);
//                    accum+= progressStep;
//                    if(accum>=pbWaiting.getMax()){
//                        tvTopCaption.setText(getString(R.string.bg_work_is_over));
//                        pbWaiting.setVisibility(View.GONE);
//                    }
//                    }
//                catch(Exception e){
//                    Log.e("fgRunable",e.getMessage());
//                }
//
//            }
//        };
//        bgRunnable=new Runnable() {
//            @Override
//            public void run() {
//                try{
//                   for(int i=0;i<20;i++){
//                       Thread.sleep(1000);
//                       synchronized (this){
//                           globalValue+=1;
//                       }
//                       handler.post(fgRunnable);
//                   }
//                }
//                catch(Exception e){
//                    Log.e("fgRunable",e.getMessage());
//                }
//            }
//        };
//        testThread=new Thread(bgRunnable);
//    }
//}
package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    private ProgressBar pbWaiting;
    private TextView tvTopCaption;
    private EditText etInput;
    private Button btnExecute,btnsang;
    private int globalValue, accum;
    private long startTime;
    private final String PATIENCE = "Some important data is being collected now.\nPlease be patient...wait...";
    private Handler handler;
    private Runnable fgRunnable, bgRunnable;
    private Thread testThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        handler = new Handler(Looper.getMainLooper());
        btnsang=findViewById(R.id.btnsang);
        btnsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intend=new Intent(getApplicationContext(),MainActivity4.class);
                startActivity(intend);
            }
        });
        tvTopCaption = findViewById(R.id.tv_top_caption);
        pbWaiting = findViewById(R.id.pb_waiting);
        etInput = findViewById(R.id.et_input);
        btnExecute = findViewById(R.id.btn_execute);
        initVariables();

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etInput.getText().toString();
                Toast.makeText(MainActivity3.this, "You said " + input, Toast.LENGTH_SHORT).show();
            }
        });

        testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                bgRunnable.run();
                fgRunnable.run();
            }
        });
        testThread.start();
        pbWaiting.incrementProgressBy(0);
    }

    private void initVariables() {
        globalValue = 0;
        accum = 0;
        startTime = System.currentTimeMillis();
        fgRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int progressStep = 5;
                    double totalTime = (System.currentTimeMillis() - startTime) / 1000;
                    synchronized (this) {
                        globalValue += 100;
                    }
                    tvTopCaption.setText(PATIENCE + totalTime + " - " + globalValue);
                    pbWaiting.incrementProgressBy(progressStep);
                    accum += progressStep;
                    if (accum >= pbWaiting.getMax()) {
                        tvTopCaption.setText(getString(R.string.bg_work_is_over));
                        pbWaiting.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Log.e("fgRunable", e.getMessage());
                }
            }
        };

        bgRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        Thread.sleep(1000);
                        synchronized (this) {
                            globalValue += 1;
                        }
                        handler.post(fgRunnable);
                    }
                } catch (Exception e) {
                    Log.e("fgRunable", e.getMessage());
                }
            }
        };
    }
}
