package com.sanctuary.kakaotalkchatbot.service;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;
import com.sanctuary.kakaotalkchatbot.models.Log;
import com.sanctuary.kakaotalkchatbot.models.Rule;
import com.sanctuary.kakaotalkchatbot.util.BasicUtil;
import com.sanctuary.kakaotalkchatbot.util.ChatLogUtil;
import com.sanctuary.kakaotalkchatbot.util.CommandsUtil;
import com.sanctuary.kakaotalkchatbot.util.LogUtil;
import com.sanctuary.kakaotalkchatbot.util.NotificationUtils;

import java.util.ArrayList;

public class MyNotificationListener extends NotificationListenerService {
    MyPreferencesManager myPreferencesManager;

    private static String lastMessage = "";
    private static long lastMessageTime = 0;

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

//        LogUtil.d("onNotificationRemoved ~ " +
//                " packageName: " + sbn.getPackageName() +
//                " id: " + sbn.getId());
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        myPreferencesManager = MyPreferencesManager.getInstance(getApplicationContext());

        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

        if (sbn.getPackageName().equals("com.kakao.talk")) {
            final String sender = title;
            final String messageBody;
            final String groupRoomName;

            // 발신자가 없는 경우 대화가 아니다.
            // 예) 1개의 안읽은 메시지.
            if (sender == null) {
                return;
            }

            if (text == null) {
                messageBody  = "";
            } else {
                messageBody = text.toString();
            }

            if (subText == null) {
                groupRoomName = "";
            } else {
                groupRoomName = subText.toString();
            }

            LogUtil.i("\n" +
                    " sender: " + sender + "\n" +
                    " messageBody: " + messageBody + "\n" +
                    " groupRoomName: " + groupRoomName);

            Log log = new Log();
            log.setSender(sender);
            log.setMessageBody(messageBody);
            log.setGroupRoomName(groupRoomName);

            if (!NotificationUtils.isAllowNotification(getApplicationContext())) {
                log.setErrorMessage("실행 중 알림이 비활성화 상태입니다.");
                saveLog(log);
                return;
            }

            if (!myPreferencesManager.isSendMessage()) {
                log.setErrorMessage("전송이 비활성화 상태입니다.");
                saveLog(log);
                return;
            }

            if (isMessageDuplication(sender, messageBody, groupRoomName)) {
                return;
            }

            if (!isRuleSend(groupRoomName, sender)) {
                return;
            }

            CommandsUtil.commandProcess(log, sbn, getApplicationContext());
        }
    }

    private boolean isRuleSend(String groupRoomName, String sender) {
        if (myPreferencesManager.isAllActive()) {
            return true;
        }

        if ("".equals(groupRoomName)) {
            if (myPreferencesManager.isAllDirectActive()) {
                return true;
            }
        } else {
           if (myPreferencesManager.isAllGroupActive()) {
               return true;
           }
        }

        ArrayList<Rule> rules = myPreferencesManager.getRuleList();

        for (Rule rule : rules) {
            // 그룹, 사용자 이름 중 일치하는 항목을 찾는 경우
            if (rule.getRoomName().contains(groupRoomName) || rule.getRoomName().contains(sender)) {
                // 개인방 에서 온 카톡
                if ("".equals(groupRoomName)) {
                    switch (rule.getRoomType()) {
                        case Rule.ROOM_TYPE_ALL: // 전체
                        case Rule.ROOM_TYPE_DIRECT: // 개인
                            return isRuleDirect(sender, rule);

                        case Rule.ROOM_TYPE_GROUP:  // 단체
                            return false;
                    }
                } else { // 단체 방에서 온 카톡
                    switch (rule.getRoomType()) {
                        case Rule.ROOM_TYPE_ALL: // 전체
                        case Rule.ROOM_TYPE_GROUP:  // 단체
                            return isRuleGroup(groupRoomName, rule);

                        case Rule.ROOM_TYPE_DIRECT: // 개인
                            return false;
                    }
                }
            }
        }

        return false;
    }

    private boolean isRuleGroup(String roomName, Rule rule) {
        switch (rule.getMatchType()) {
            case Rule.MATCH_TYPE_EXACT:
                return roomName.equals(rule.getRoomName());

            case Rule.MATCH_TYPE_INCLUDING:
                return roomName.contains(rule.getRoomName());
        }

        return false;
    }

    private boolean isRuleDirect(String sender, Rule rule) {
        switch (rule.getMatchType()) {
            case Rule.MATCH_TYPE_EXACT:
                return sender.equals(rule.getRoomName());

            case Rule.MATCH_TYPE_INCLUDING:
                return sender.contains(rule.getRoomName());
        }

        return false;
    }

    // 메시지 중복 검사
    private boolean isMessageDuplication(String sender, String messageBody, String groupRoomName) {
        /*
         * 0.5초안에 같은 내용의 노티가 오는 경우 전송하지 않는다.
         */
        // 마지막 노티를 받은지  0.5초가 경과되지 않은 경우
        if (System.currentTimeMillis() <= lastMessageTime + 500) {
            // 내용이 같을 경우 중복 노티로 간주하고 해당 내용을 보내지 않는다.
            if (lastMessage.equals(sender + messageBody + groupRoomName)) {
                LogUtil.d("중복 노티 감지");
                return true;
            }
        }

        // 마지막 노티를 받은지 2초가 지날 경우 현재 시간으로 갱신시킨다.
        if (System.currentTimeMillis() > lastMessageTime + 2000) {
            lastMessageTime = System.currentTimeMillis();
        }

        lastMessage = sender + messageBody + groupRoomName;
        return false;
    }

    private void saveLog(Log log) {
        log.setDate(BasicUtil.getNowTime());
        ChatLogUtil.add(getApplicationContext(), log);
    }
}


// 노티 정보 출력
//            LogUtil.i("\n" +
//                    " packageName: " + sbn.getPackageName() + "\n" +
//                    " id: " + sbn.getId() + "\n" +
//                    " postTime: " + sbn.getPostTime() + "\n" +
//                    " title: " + title + "\n" +
//                    " text : " + text + "\n" +
//                    " subText: " + subText);

//        Notification notification = sbn.getNotification();
//        Icon smallIcon = notification.getSmallIcon();
//        Icon largeIcon = notification.getLargeIcon();