package com.sanctuary.kakaotalkchatbot.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.activity.SplashActivity;
import com.sanctuary.kakaotalkchatbot.app.MyApplication;
import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;

@SuppressWarnings("ConstantConditions")
public class MyService extends Service {
    public static final int RUNNING_NOTIFICATION_ID = 1;

    private final IBinder mIBinder = new MyBinder();

    MyPreferencesManager myPreferencesManager;

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myPreferencesManager = MyPreferencesManager.getInstance(getApplicationContext());

        // 리시버 설정
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName());
        registerReceiver(buttonBroadcastReceiver, intentFilter);

//         앱 실행중 노티 초기화
        initNotification(
                getRemoteViews(myPreferencesManager.isSendMessage()),
                myPreferencesManager.isSendMessage() ? R.drawable.ic_launcher : R.drawable.ic_launcher_disable
        );
    }

    // 브로드캐스트 리시버 정의
    private final BroadcastReceiver buttonBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSendMessage = myPreferencesManager.isSendMessage();

            myPreferencesManager.setSendMessage(!isSendMessage);

            initNotification(
                    getRemoteViews(myPreferencesManager.isSendMessage()),
                    myPreferencesManager.isSendMessage() ? R.drawable.ic_launcher : R.drawable.ic_launcher_disable
            );
        }
    };

    private RemoteViews getRemoteViews(boolean isChecked) {
        Intent intent = new Intent(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews notificationLayout = new RemoteViews(
                getPackageName(),
                isChecked ? R.layout.notification_enable : R.layout.notification_disable);

        notificationLayout.setOnClickPendingIntent(R.id.iv_notification_send, pendingIntent);

        return notificationLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(RUNNING_NOTIFICATION_ID);
    }

    private void initNotification(RemoteViews notificationLayout, int icon) {
        Intent intent = new Intent(this, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyApplication.RUNNING_CHANNEL_ID)
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContent(notificationLayout)
                .setSmallIcon(icon)
                .setContentIntent(pendingIntent)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.app_name) + " 앱이 실행 중 입니다.")
                .setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(RUNNING_NOTIFICATION_ID,  builder.build());
        } else {
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(RUNNING_NOTIFICATION_ID, builder.build());
        }
    }
}
