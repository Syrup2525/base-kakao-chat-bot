package com.sanctuary.kakaotalkchatbot.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.FragmentSupportBinding;
import com.sanctuary.kakaotalkchatbot.fragment.BaseFragment;

public class SupportFragment extends BaseFragment {
    private FragmentSupportBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentSupportBinding.bind(getView());

        initLayout();
        initData();
    }

    private void initLayout() {

    }

    private void initData() {

    }
}
