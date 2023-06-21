package com.sanctuary.kakaotalkchatbot.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sanctuary.kakaotalkchatbot.models.Log;
import com.sanctuary.kakaotalkchatbot.models.Rule;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyPreferencesManager {
    @SuppressLint("StaticFieldLeak")
    private static MyPreferencesManager INSTANCE;

    private final String preferenceName;
    private final SharedPreferences.Editor editor;
    private final Context context;

    private final Gson gson;

    private static final int PRIVATE_MODE = 0;

    private static final String ALL_ACTIVE = "all_active";
    private static final String ALL_GROUP_ACTIVE = "all_group_active";
    private static final String ALL_DIRECT_ACTIVE = "all_direct_active";

    private static final String SERVER_LIST = "server_list";
    private static final String SEND_MESSAGE = "send_message";
    private static final String LOG_LIST = "log_list";
    private static final String RULE_LIST = "rule_list";

    @SuppressLint("CommitPrefEdits")
    private MyPreferencesManager(Context context) {
        this.context = context;
        preferenceName = context.getPackageName();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(preferenceName, PRIVATE_MODE);
        editor = sharedPreferences.edit();

        gson = new Gson();
    }

    public static synchronized MyPreferencesManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MyPreferencesManager(context);
        }
        return INSTANCE;
    }

    public void setAllActive(boolean b) {
        editor.putBoolean(ALL_ACTIVE, b);
        editor.apply();
    }

    public boolean isAllActive() {
        return context.getSharedPreferences(preferenceName, PRIVATE_MODE).getBoolean(ALL_ACTIVE, false);
    }

    public void setAllGroupActive(boolean b) {
        editor.putBoolean(ALL_GROUP_ACTIVE, b);
        editor.apply();
    }

    public boolean isAllGroupActive() {
        return context.getSharedPreferences(preferenceName, PRIVATE_MODE).getBoolean(ALL_GROUP_ACTIVE, false);
    }

    public void setAllDirectActive(boolean b) {
        editor.putBoolean(ALL_DIRECT_ACTIVE, b);
        editor.apply();
    }

    public boolean isAllDirectActive() {
        return context.getSharedPreferences(preferenceName, PRIVATE_MODE).getBoolean(ALL_DIRECT_ACTIVE, false);
    }

    public void setSendMessage(boolean b) {
        editor.putBoolean(SEND_MESSAGE, b);
        editor.apply();
    }

    public boolean isSendMessage() {
        return context.getSharedPreferences(preferenceName, PRIVATE_MODE).getBoolean(SEND_MESSAGE, true);
    }

    public void setLogList(ArrayList<Log> itemList) {
        try {
            String json = gson.toJson(itemList);

            editor.putString(LOG_LIST, json);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Log> getLogList() {
        try {
            Type type = new TypeToken<ArrayList<Log>>() {}.getType();
            String json = context.getSharedPreferences(preferenceName, PRIVATE_MODE).getString(LOG_LIST, "");

            if (json.equals("")) {
                return new ArrayList<>();
            }

            return gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    public void setRuleList(ArrayList<Rule> itemList) {
        try {
            String json = gson.toJson(itemList);

            editor.putString(RULE_LIST, json);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Rule> getRuleList() {
        try {
            Type type = new TypeToken<ArrayList<Rule>>() {}.getType();
            String json = context.getSharedPreferences(preferenceName, PRIVATE_MODE).getString(RULE_LIST, "");

            if (json.equals("")) {
                return new ArrayList<>();
            }

            return gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }
}
