package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity6 extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        tvContent= findViewById(R.id.tv_content);
        initBroadcastReceiver();
    }

    public void processReceive(Context context, Intent intent){
        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_SHORT).show();

        final String SMS="pdus";
        Bundle bundle= intent.getExtras();
        Object[] messages=(Object[]) bundle.get(SMS);
        String sms="";
        SmsMessage smsMsg;
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();

        for(int i=0;i<messages.length;i++){
            if(Build.VERSION.SDK_INT>=23){
                smsMsg=SmsMessage.createFromPdu((byte[]) messages[i],"");
            }else{
                smsMsg=SmsMessage.createFromPdu((byte[]) messages[i]);
            }
            String msgBody=smsMsg.getMessageBody();
            String address=smsMsg.getDisplayOriginatingAddress();
            sms+=address+":\n"+msgBody+"\n";
        }
        tvContent.setText(sms);

    }
    private void initBroadcastReceiver(){
        filter =new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context,intent);
            }
        };
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (broadcastReceiver == null) initBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
    }
    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}