package com.example.recipesapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.recipesapp.MainActivity;
import com.example.recipesapp.R;
import com.example.recipesapp.NotificationHelper;

public class RecetaService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private Handler handler = new Handler();
    private Runnable notificationRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createNotificationChannel(this);
        startForeground(NOTIFICATION_ID, createNotification());

        notificationRunnable = () -> {
            sendNotification();
            handler.postDelayed(notificationRunnable, 30 * 60 * 1000); // 30 minutos
        };

        handler.post(notificationRunnable);
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID)
                .setContentTitle("¡Hora de cocinar!")
                .setContentText("Revisa nuevas recetas o tus favoritas.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID)
                .setContentTitle("¿Qué receta probarás hoy?")
                .setContentText("Revisa la app y encuentra algo delicioso.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(notificationRunnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
