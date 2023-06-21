package com.sanctuary.kakaotalkchatbot.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.adapter.SendHistoryAdapter;
import com.sanctuary.kakaotalkchatbot.databinding.FragmentSendHistoryBinding;
import com.sanctuary.kakaotalkchatbot.fragment.BaseFragment;
import com.sanctuary.kakaotalkchatbot.widght.MyDividerItemDecoration;

public class SendHistoryFragment extends BaseFragment {
    private FragmentSendHistoryBinding binding;
    private SendHistoryAdapter sendHistoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentSendHistoryBinding.bind(getView());

        initLayout();
        initData();
    }

    private void initLayout() {
        initAdapter();
    }

    private void initAdapter() {
        sendHistoryAdapter = new SendHistoryAdapter(getContext());
        binding.rcvLogList.setHasFixedSize(true);
        binding.rcvLogList.addItemDecoration(new MyDividerItemDecoration(getContext(), MyDividerItemDecoration.VERTICAL_LIST, R.drawable.divider_recycler_list));
        binding.rcvLogList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvLogList.setAdapter(sendHistoryAdapter);
    }

    private void initData() {
        sendHistoryAdapter.initItems(myPreferencesManager.getLogList());
    }
}