package com.sanctuary.kakaotalkchatbot.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.adapter.MainPagerAdapter;
import com.sanctuary.kakaotalkchatbot.databinding.ActivityMainBinding;
import com.sanctuary.kakaotalkchatbot.databinding.CommonToolbarBinding;
import com.sanctuary.kakaotalkchatbot.service.MyService;
import com.sanctuary.kakaotalkchatbot.util.KeyHandleUtil;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private CommonToolbarBinding commonToolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        commonToolbarBinding = DataBindingUtil.bind(binding.commonToolbar.getRoot());

        initLayout();
        startService();
    }

    private void initLayout() {
        commonToolbarBinding.toolbarTitle.setText("규칙 관리");

        initViewPager();
        initBottomTabLayout();
    }

    private void initViewPager() {
        MainPagerAdapter itemAdapter = new MainPagerAdapter(this.getSupportFragmentManager(), 1);

        binding.vpMain.setOffscreenPageLimit(MainPagerAdapter.FRAGMENT_COUNT - 1);
        binding.vpMain.setAdapter(itemAdapter);
        binding.vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTabLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomTabLayout() {
        binding.loChattingSetting.setOnClickListener(v -> {
            selectTabLayout(MainPagerAdapter.TAB1);
            binding.vpMain.setCurrentItem(MainPagerAdapter.TAB1);
            commonToolbarBinding.toolbarTitle.setText("규칙 관리");
        });

        binding.loSendHistory.setOnClickListener(v -> {
            selectTabLayout(MainPagerAdapter.TAB2);
            binding.vpMain.setCurrentItem(MainPagerAdapter.TAB2);
            commonToolbarBinding.toolbarTitle.setText("전송 내역");
        });

        binding.loCommand.setOnClickListener(v -> {
            selectTabLayout(MainPagerAdapter.TAB3);
            binding.vpMain.setCurrentItem(MainPagerAdapter.TAB3);
            commonToolbarBinding.toolbarTitle.setText("명령어");
        });

        binding.loSetting.setOnClickListener(v -> {
            selectTabLayout(MainPagerAdapter.TAB4);
            binding.vpMain.setCurrentItem(MainPagerAdapter.TAB4);
            commonToolbarBinding.toolbarTitle.setText("설정");
        });

        binding.loSupport.setOnClickListener(v -> {
            selectTabLayout(MainPagerAdapter.TAB5);
            binding.vpMain.setCurrentItem(MainPagerAdapter.TAB5);
            commonToolbarBinding.toolbarTitle.setText("문제 해결");
        });
    }

    private void selectTabLayout(int num) {
        tabUnSelect(binding.ivChattingSetting, binding.tvChattingSetting);
        tabUnSelect(binding.ivSendHistory, binding.tvSendHistory);
        tabUnSelect(binding.ivCommand, binding.tvCommand);
        tabUnSelect(binding.ivSetting, binding.tvSetting);
        tabUnSelect(binding.ivSupport, binding.tvSupport);

        switch (num) {
            case MainPagerAdapter.TAB1:
                tabSelect(binding.ivChattingSetting, binding.tvChattingSetting);
                break;

            case MainPagerAdapter.TAB2:
                tabSelect(binding.ivSendHistory, binding.tvSendHistory);
                break;

            case MainPagerAdapter.TAB3:
                tabSelect(binding.ivCommand, binding.tvCommand);
                break;

            case MainPagerAdapter.TAB4:
                tabSelect(binding.ivSetting, binding.tvSetting);
                break;

            case MainPagerAdapter.TAB5:
                tabSelect(binding.ivSupport, binding.tvSupport);
                break;
        }
    }

    private void tabUnSelect(ImageView imageView, TextView textView) {
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.un_select));
        textView.setTextColor(ContextCompat.getColor(this, R.color.un_select));
    }

    private void tabSelect(ImageView imageView, TextView textView) {
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    private void startService() {
        if (!isServiceRunningCheck(getPackageName() + ".service.MyService")) {
            Intent intent = new Intent(MainActivity.this, MyService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
    }

    public boolean isServiceRunningCheck(String serviceName) {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        KeyHandleUtil.doubleBackFinish(this);
    }
}