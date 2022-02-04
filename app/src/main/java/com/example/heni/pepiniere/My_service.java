package com.example.heni.pepiniere;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

public class My_service extends Service {
int mstartmode;
IBinder mbinder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent br=new Intent(this,My_broadcast.class);
        sendBroadcast(br);

        return mstartmode;
    }
}
