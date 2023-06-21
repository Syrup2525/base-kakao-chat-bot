package com.sanctuary.kakaotalkchatbot.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.util.ActivityUtil;
import com.sanctuary.kakaotalkchatbot.util.BasicUtil;
import com.sanctuary.kakaotalkchatbot.util.NotificationUtils;

public class SplashActivity extends BaseActivity{
    private final int REQUEST_PERMISSION= 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 노티피케이션 채널 생성
        NotificationUtils.createNotificationChannel(this);

        permissionCheck();
    }

    private void permissionCheck() {
        Intent intent = new Intent(SplashActivity.this, PermissionActivity.class);

        // 노티 읽기 권한 확인
        if (!BasicUtil.isNotificationReadPermission(SplashActivity.this)) {
            startActivityForResult(intent, REQUEST_PERMISSION);
            return;
        }

        if (!BasicUtil.isAllowWhiteList(SplashActivity.this,  getPackageName())) {
            startActivityForResult(intent, REQUEST_PERMISSION);
            return;
        }

        if (!NotificationUtils.isAllowNotification(this)) {
            startActivityForResult(intent, REQUEST_PERMISSION);
            return;
        }

        ActivityUtil.startNewActivity(SplashActivity.this, MainActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION) {
            permissionCheck();
        }
    }
}