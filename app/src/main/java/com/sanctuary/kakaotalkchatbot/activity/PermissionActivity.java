package com.sanctuary.kakaotalkchatbot.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.ActivityPermissionBinding;
import com.sanctuary.kakaotalkchatbot.dialog.NoticeDialog;
import com.sanctuary.kakaotalkchatbot.util.BasicUtil;
import com.sanctuary.kakaotalkchatbot.util.NotificationUtils;

public class PermissionActivity extends BaseActivity{
    private ActivityPermissionBinding binding;

    private final int REQUEST_NOTIFICATION_LISTENER_SETTINGS = 10000;
    private final int REQUEST_IGNORE_BATTERY = 10001;
    private final int REQUEST_ALLOW_NOTIFICATION = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);

        initLayout();
    }

    private void initLayout() {
        binding.btApplyPermissions.setOnClickListener(v -> checkNotificationReadPermission());
    }

    // 노티 읽기 권한 확인
    private void checkNotificationReadPermission() {
        if (BasicUtil.isNotificationReadPermission(PermissionActivity.this)) {
            registerWhiteList();
        } else {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivityForResult(intent, REQUEST_NOTIFICATION_LISTENER_SETTINGS);
        }
    }


    // 앱 화이트 리스트 등록
    @SuppressLint("BatteryLife")
    private void registerWhiteList() {
        String packageName = getPackageName();

        // 화이트 리스트 등록 되어 있을때
        if (BasicUtil.isAllowWhiteList(PermissionActivity.this, packageName)) {
            checkNotificationSendPermission();
        } else {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
            startActivityForResult(intent, REQUEST_IGNORE_BATTERY);
        }
    }

    // 노티 알림 기능 확인
    private void checkNotificationSendPermission() {
        if (NotificationUtils.isAllowNotification(this)) {
            finish();
        } else {
            new NoticeDialog(PermissionActivity.this)
                    .setShowNegativeButton(false)
                    .setBackPressButton(false)
                    .setMsg(getString(R.string.app_name) + " 앱의 모든 알림을 허용 해주세요.")
                    .setNoticeDialogCallbackListener(new NoticeDialog.NoticeDialogCallbackListener() {
                        @Override
                        public void positive() {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

                            //for Android 5-7
                            intent.putExtra("app_package", getPackageName());
                            intent.putExtra("app_uid", getApplicationInfo().uid);

                            // for Android O
                            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

                            startActivityForResult(intent, REQUEST_ALLOW_NOTIFICATION);
                        }

                        @Override
                        public void negative() { }
                    }).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_NOTIFICATION_LISTENER_SETTINGS:
                checkNotificationReadPermission();
                break;

            case REQUEST_IGNORE_BATTERY:
                registerWhiteList();
                break;

            case REQUEST_ALLOW_NOTIFICATION:
                checkNotificationSendPermission();
                break;
        }
    }

    @Override
    public void onBackPressed() { }
}