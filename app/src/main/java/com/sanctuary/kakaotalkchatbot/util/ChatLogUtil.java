package com.sanctuary.kakaotalkchatbot.util;

import android.content.Context;

import com.sanctuary.kakaotalkchatbot.app.MyApplication;
import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;
import com.sanctuary.kakaotalkchatbot.models.Log;

import java.util.ArrayList;

public class ChatLogUtil {
    public static void add(Context context, Log log) {
        MyPreferencesManager myPreferencesManager = MyPreferencesManager.getInstance(context);

        ArrayList<Log> logs = new ArrayList<>(myPreferencesManager.getLogList());
        logs.add(0, log);

        if (logs.size() > MyApplication.LOG_MAX_SIZE) {
            logs.remove(logs.size() - 1);
        }

        myPreferencesManager.setLogList(logs);
    }

    public static void clear(Context context) {
        MyPreferencesManager myPreferencesManager = MyPreferencesManager.getInstance(context);

        myPreferencesManager.setLogList(new ArrayList<>());
    }
}
