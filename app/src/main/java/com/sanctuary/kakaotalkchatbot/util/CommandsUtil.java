package com.sanctuary.kakaotalkchatbot.util;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.service.notification.StatusBarNotification;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.app.MyApplication;
import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;
import com.sanctuary.kakaotalkchatbot.models.Log;
import com.sanctuary.kakaotalkchatbot.network.NetworkPresenter;
import com.sanctuary.kakaotalkchatbot.models.Action;

@SuppressLint("StaticFieldLeak")
public class CommandsUtil {
    private static StatusBarNotification sbn;
    private static Context context;
    private static NetworkPresenter networkPresenter;
    private static Log log;

    public static void commandProcess(Log log, StatusBarNotification sbn, Context context) {
        CommandsUtil.log = log;
        CommandsUtil.sbn = sbn;
        CommandsUtil.context = context;
        MyPreferencesManager myPreferencesManager = MyPreferencesManager.getInstance(context);
        networkPresenter = new NetworkPresenter(context);

        LogUtil.i(log.getMessageBody());

        // 공백 제거
        String command = log.getMessageBody().trim();

        // = 포함되어 있지 않은 경우
        if (!command.contains(MyApplication.COMMAND)) {
            LogUtil.i("NOT COMMAND");
            return;
        }

        // = 가 제일 처음에 없는 경우
        if (command.indexOf(MyApplication.COMMAND) != 0) {
            LogUtil.i("NOT COMMAND");
            return;
        }

        // = 만 있는 경우
        if (MyApplication.COMMAND.equals(command)) {
            LogUtil.i("NOT COMMAND");
            return;
        }

        // = 제거
        command = command.substring(1);

        // 사용 가능한 명령어 목록
        if ("명령어".equals(command)) {
            sendMessage(context.getString(R.string.help_string));
            return;
        }


        String[] commandSplit = command.split(" ");

        if (commandSplit.length == 1) {
            command = commandSplit[0];
        }
    }

    // 메시지 전송
    private static void sendMessage(String msg) {
        Action action = NotificationUtils.getQuickReplyAction(sbn.getNotification(), context.getPackageName());

        if (action != null) {
            try {
                action.sendReply(context, msg);

                log.setMessage(msg);
                log.setDate(BasicUtil.getNowTime());

                ChatLogUtil.add(context, log);

                LogUtil.i("send message : \n" + msg);
            } catch (PendingIntent.CanceledException e) {
                LogUtil.i("send message fail : " + e.toString());
            }
        } else {
            LogUtil.i("send message fail : action is null");
        }
    }
}
