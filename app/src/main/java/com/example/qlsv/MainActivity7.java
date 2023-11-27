package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity7 extends AppCompatActivity {

        Button btn;
        private BroadcastReceiver broadcastReceiver;
        private IntentFilter intentFilter;

        private void processReceive(Context context, Intent intent) {
            Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();
            TextView tvContent = findViewById(R.id.tv_content);

            final String SMS_EXTRA = "pdus";
            Bundle bundle = intent.getExtras();
            Object[] messages = (Object[]) bundle.get(SMS_EXTRA);

            String sms = "";

            SmsMessage smsMsg;

            for (int i = 0; i < messages.length; i++) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String format = bundle.getString("format");
                    smsMsg = SmsMessage.createFromPdu((byte[]) messages[i], format);
                } else {
                    smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
                }

                String smsBody = smsMsg.getMessageBody();
                String address = smsMsg.getDisplayOriginatingAddress();

                sms += address + ":\n" + smsBody + "\n";
            }

            tvContent.setText(sms);
        }

        private void initBroadcastReceiver() {
            intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    processReceive(context, intent);
                }
            };
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main6);
            initBroadcastReceiver();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(getApplicationContext(),MainActivity8.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        protected void onResume() {
            super.onResume();
            if (broadcastReceiver == null) {
                initBroadcastReceiver();
            }
            registerReceiver(broadcastReceiver, intentFilter);
        }

        @Override
        protected void onStop() {
            super.onStop();
            unregisterReceiver(broadcastReceiver);
        }



}