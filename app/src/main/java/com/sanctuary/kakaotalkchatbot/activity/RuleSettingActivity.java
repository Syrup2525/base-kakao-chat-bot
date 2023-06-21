package com.sanctuary.kakaotalkchatbot.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.adapter.ChattingSettingAdapter;
import com.sanctuary.kakaotalkchatbot.databinding.ActivityChattingSettingBinding;
import com.sanctuary.kakaotalkchatbot.databinding.CommonToolbarBinding;
import com.sanctuary.kakaotalkchatbot.fragment.BaseFragment;

public class RuleSettingActivity extends BaseFragment {
    private ActivityChattingSettingBinding binding;
    private CommonToolbarBinding commonToolbarBinding;
    private ChattingSettingAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chatting_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() == null) {
            return;
        }

        binding = ActivityChattingSettingBinding.bind(getView());
        commonToolbarBinding = DataBindingUtil.bind(binding.commonToolbar.getRoot());

//        itemAdapter = new ChattingSettingAdapter(getContext().getSupportFragmentManager(), 1);

        initLayout();
        initData();
    }

    private void initLayout() {
//        binding.vpChatting.setOffscreenPageLimit(2);
    }

    private void initData() {
//        itemAdapter.addItem(new GroupChattingSettingFragment());
//        itemAdapter.addItem(new DirectChattingSettingFragment());

//        binding.vpChatting.setAdapter(itemAdapter);
    }
}