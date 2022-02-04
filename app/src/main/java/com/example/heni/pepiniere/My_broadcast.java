package com.example.heni.pepiniere;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class My_broadcast extends BroadcastReceiver  {
    List<plante> ls_control_water=new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Intent br=new Intent(context,MainActivity.class);
        br.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(br);*/

            try {
                ls_control_water = new control_level_water(context).execute().get();
                for (int i = 0; i < ls_control_water.size(); i++) {

                    if (ls_control_water.get(i).getNiv_eau() <= 15) {
                        new createNotif(context, i).notification(ls_control_water.get(i).getName(),
                                Integer.toString(ls_control_water.get(i).getNiv_eau()));

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

    }

    }


