package com.sanctuary.kakaotalkchatbot.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.sanctuary.kakaotalkchatbot.app.MyApplication;
import com.sanctuary.kakaotalkchatbot.models.Action;

public class NotificationUtils {

    private static final String[] REPLY_KEYWORDS = {"reply", "android.intent.extra.text"};
    private static final CharSequence REPLY_KEYWORD = "reply";
    private static final CharSequence INPUT_KEYWORD = "input";

    public static Action getQuickReplyAction(Notification n, String packageName) {
        NotificationCompat.Action action = null;
        if (Build.VERSION.SDK_INT >= 24)
            action = getQuickReplyAction(n);
        if (action == null)
            action = getWearReplyAction(n);
        if (action == null)
            return null;
        return new Action(action, packageName, true);
    }

    private static NotificationCompat.Action getQuickReplyAction(Notification n) {
        for (int i = 0; i < NotificationCompat.getActionCount(n); i++) {
            NotificationCompat.Action action = NotificationCompat.getAction(n, i);
            if (action.getRemoteInputs() != null) {
                for (int x = 0; x < action.getRemoteInputs().length; x++) {
                    RemoteInput remoteInput = action.getRemoteInputs()[x];
                    if (isKnownReplyKey(remoteInput.getResultKey()))
                        return action;
                }
            }
        }
        return null;
    }

    private static NotificationCompat.Action getWearReplyAction(Notification n) {
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(n);
        for (NotificationCompat.Action action : wearableExtender.getActions()) {
            if (action.getRemoteInputs() != null) {
                for (int x = 0; x < action.getRemoteInputs().length; x++) {
                    RemoteInput remoteInput = action.getRemoteInputs()[x];
                    if (isKnownReplyKey(remoteInput.getResultKey()))
                        return action;
                    else if (remoteInput.getResultKey().toLowerCase().contains(INPUT_KEYWORD))
                        return action;
                }
            }
        }
        return null;
    }

    private static boolean isKnownReplyKey(String resultKey) {
        if (TextUtils.isEmpty(resultKey))
            return false;

        resultKey = resultKey.toLowerCase();
        for (String keyword : REPLY_KEYWORDS)
            if (resultKey.contains(keyword))
                return true;

        return false;
    }

    // 알림 채널 생성
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // 실행중 알림 등록
            NotificationChannel runningNotification = new NotificationChannel(
                    MyApplication.RUNNING_CHANNEL_ID,
                    MyApplication.RUNNING_NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_LOW);
            runningNotification.setDescription("실행중 알림");

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(runningNotification);
            }
        }
    }

    // 알림 허용 유무 확인
    @SuppressWarnings("ConstantConditions")
    public static boolean isAllowNotification(Context context) {
        boolean isAllowNotification;

        // Oreo 이상일 경우 채널별 알림을 확인 해야한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel;

            channel = manager.getNotificationChannel(MyApplication.RUNNING_CHANNEL_ID);
            isAllowNotification = channel.getImportance() != NotificationManager.IMPORTANCE_NONE;
        // Oreo 미만 버전일 경우
        } else {
            isAllowNotification = NotificationManagerCompat.from(context).areNotificationsEnabled();
        }

        return isAllowNotification;
    }
}
