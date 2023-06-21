package com.sanctuary.kakaotalkchatbot.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;
import com.sanctuary.kakaotalkchatbot.databinding.CommonToolbarBinding;
import com.sanctuary.kakaotalkchatbot.databinding.CustomToastBinding;
import com.sanctuary.kakaotalkchatbot.dialog.NoticeDialog;
import com.sanctuary.kakaotalkchatbot.network.NetworkPresenter;

public class BaseActivity extends AppCompatActivity {
    protected NetworkPresenter networkPresenter;
    protected MyPreferencesManager myPreferencesManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkPresenter = new NetworkPresenter(this);
        myPreferencesManager = MyPreferencesManager.getInstance(this);
    }

    protected void initCommonActionBarLayout(CommonToolbarBinding commonToolbarBinding, String title, boolean setBackButton) {
        setSupportActionBar(commonToolbarBinding.toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(setBackButton);
            actionBar.setDisplayShowTitleEnabled(false);
            commonToolbarBinding.toolbarTitle.setText(title);
        }
    }

    protected void showToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        CustomToastBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_toast, null, false);

        binding.tvMessage.setText(message);

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(binding.getRoot());
        toast.show();
    }

    protected void alert(String message) {
        new NoticeDialog(this)
                .setShowNegativeButton(false)
                .setBackPressButton(false)
                .setMsg(message)
                .show();
    }

    // 뒤로가기 버튼 세팅
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
