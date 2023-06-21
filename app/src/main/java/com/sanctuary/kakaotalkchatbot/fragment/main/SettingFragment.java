package com.sanctuary.kakaotalkchatbot.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.FragmentSettingBinding;
import com.sanctuary.kakaotalkchatbot.fragment.BaseFragment;
import com.sanctuary.kakaotalkchatbot.util.VersionUtils;

public class SettingFragment extends BaseFragment {
    private FragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentSettingBinding.bind(getView());

        initLayout();
        initData();
    }

    private void initLayout() {

    }

    private void initData() {
        binding.tvAppVersionName.setText(VersionUtils.getVersionName(getContext()));
    }
}