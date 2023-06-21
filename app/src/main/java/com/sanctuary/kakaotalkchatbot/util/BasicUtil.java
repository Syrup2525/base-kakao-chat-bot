package com.sanctuary.kakaotalkchatbot.util;

import android.content.Context;
import android.os.PowerManager;

import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static android.content.Context.POWER_SERVICE;

public class BasicUtil {
    public static boolean isNotificationReadPermission(Context context) {
        Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (sets != null && sets.contains(context.getPackageName())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAllowWhiteList(Context context, String packageName) {
        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);

        if (pm != null) {
            return pm.isIgnoringBatteryOptimizations(packageName);
        }

        return false;
    }

    public static int random(int minNum, int maxNum) {
        return new Random().nextInt((maxNum - minNum) + 1) + minNum;
    }

    public static String getNowTime() {
        long nowTime = System.currentTimeMillis();
        Date date = new Date(nowTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

        return sdf.format(date);
    }
}
