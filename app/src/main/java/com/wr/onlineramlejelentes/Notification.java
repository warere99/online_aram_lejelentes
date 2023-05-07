package com.wr.onlineramlejelentes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification {
    private Context context;
    private NotificationManager notificationManager;

    private static final String CHANNEL_ID = "meroora_notification";
    private static int NOTIFICATION_NUMBER = 0;

    public Notification(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Online Áram Jelentés", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Online Áram jelentés értesítés");
        this.notificationManager.createNotificationChannel(notificationChannel);
    }

    public void send(String message) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle("Online Áram Jelentés").setContentText(message).setSmallIcon(R.drawable.ic_measure);
        this.notificationManager.notify(NOTIFICATION_NUMBER, notificationBuilder.build());
    }
}
