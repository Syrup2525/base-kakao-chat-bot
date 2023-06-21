package com.sanctuary.kakaotalkchatbot.app;

import android.app.Application;

import com.sanctuary.kakaotalkchatbot.util.LogUtil;

public class MyApplication extends Application {
    public static final String RUNNING_CHANNEL_ID = "running_channel_id";
    public static final String RUNNING_NOTIFICATION_NAME = "앱 실행 알림";

    public static final String COMMAND = "=";

    public static final int LOG_MAX_SIZE = 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(this, null);
    }
}