package com.example.lab6_2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Powerstatechange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();

        Log.d("Powerstatechange", "Received intent: " + intent.getAction());
        if (context == null) {
            Toast.makeText(context, "hoho", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Toast.makeText(context, "hihi", Toast.LENGTH_SHORT).show();
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(context, "Dang sac nha", Toast.LENGTH_LONG).show();
            }

            if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Toast.makeText(context, "Da ngat sac", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("loisai",e.getMessage());
        }
    }
}
