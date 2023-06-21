package com.sanctuary.kakaotalkchatbot.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.adapter.RuleSettingAdapter;
import com.sanctuary.kakaotalkchatbot.databinding.FragmentRuleSettingBinding;
import com.sanctuary.kakaotalkchatbot.dialog.NoticeDialog;
import com.sanctuary.kakaotalkchatbot.dialog.RuleSettingDialog;
import com.sanctuary.kakaotalkchatbot.fragment.BaseFragment;
import com.sanctuary.kakaotalkchatbot.models.Rule;
import com.sanctuary.kakaotalkchatbot.widght.MyDividerItemDecoration;

import java.util.ArrayList;

public class RuleSettingFragment extends BaseFragment {
    private FragmentRuleSettingBinding binding;

    private RuleSettingAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rule_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() == null) {
            return;
        }

        binding = FragmentRuleSettingBinding.bind(getView());

        initLayout();
        initData();
    }

    private void initLayout() {
        binding.swAllActive.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.swDirectActive.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.swGroupActive.setOnCheckedChangeListener(onCheckedChangeListener);

        initRecyclerview();

        binding.btAdd.setOnClickListener(v ->
                new RuleSettingDialog(getContext())
                        .setRuleSettingDialogCallbackListener(new RuleSettingDialog.RuleSettingDialogCallbackListener() {
                            @Override
                            public void positive(Rule rule) {
                                ArrayList<Rule> rules = myPreferencesManager.getRuleList();

                                if (rules.contains(rule)) {
                                    showToast("동일한 규칙이 존재합니다.");
                                    return;
                                }

                                rules.add(0, rule);
                                myPreferencesManager.setRuleList(rules);
                                itemAdapter.initItems(myPreferencesManager.getRuleList());

                                showToast("추가 되었습니다.");
                            }

                            @Override
                            public void negative() { }
                        }).show());
    }

    private void initRecyclerview() {
        itemAdapter = new RuleSettingAdapter(getContext());
        itemAdapter.setOnItemClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.lo_content:
                    new RuleSettingDialog(getContext())
                            .setRule(itemAdapter.getItem(position))
                            .setRuleSettingDialogCallbackListener(new RuleSettingDialog.RuleSettingDialogCallbackListener() {
                                @Override
                                public void positive(Rule rule) {
                                    ArrayList<Rule> rules = myPreferencesManager.getRuleList();
                                    rules.set(position, rule);

                                    myPreferencesManager.setRuleList(rules);
                                    itemAdapter.initItems(myPreferencesManager.getRuleList());

                                    showToast("수정 되었습니다.");
                                }

                                @Override
                                public void negative() { }
                            }).show();
                    break;

                case R.id.iv_delete:
                    new NoticeDialog(getContext())
                            .setMsg("삭제 하시겠습니까?")
                            .setNoticeDialogCallbackListener(new NoticeDialog.NoticeDialogCallbackListener() {
                                @Override
                                public void positive() {
                                    ArrayList<Rule> rules = myPreferencesManager.getRuleList();
                                    rules.remove(position);

                                    myPreferencesManager.setRuleList(rules);
                                    itemAdapter.initItems(myPreferencesManager.getRuleList());

                                    showToast("삭제 되었습니다.");
                                }

                                @Override
                                public void negative() { }
                            }).show();
                    break;
            }
        });

        if (getContext() == null) {
            return;
        }

        binding.rcvChattingRoomList.setHasFixedSize(true);
        binding.rcvChattingRoomList.addItemDecoration(new MyDividerItemDecoration(getContext(), MyDividerItemDecoration.VERTICAL_LIST, R.drawable.divider_recycler_list));
        binding.rcvChattingRoomList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvChattingRoomList.setAdapter(itemAdapter);
    }

    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (compoundButton, b) -> {
        if (!compoundButton.isPressed()) {
            return;
        }

        switch (compoundButton.getId()) {
            case R.id.sw_all_active:
                binding.swDirectActive.setChecked(b);
                binding.swGroupActive.setChecked(b);

                myPreferencesManager.setAllActive(b);
                myPreferencesManager.setAllDirectActive(b);
                myPreferencesManager.setAllGroupActive(b);
                break;

            case R.id.sw_direct_active:
                if (binding.swDirectActive.isChecked() && binding.swGroupActive.isChecked()) {
                    myPreferencesManager.setAllGroupActive(true);
                    binding.swAllActive.setChecked(true);
                } else {
                    myPreferencesManager.setAllGroupActive(false);
                    binding.swAllActive.setChecked(false);
                }

                myPreferencesManager.setAllDirectActive(b);
                break;

            case R.id.sw_group_active:
                if (binding.swDirectActive.isChecked() && binding.swGroupActive.isChecked()) {
                    myPreferencesManager.setAllGroupActive(true);
                    binding.swAllActive.setChecked(true);
                } else {
                    myPreferencesManager.setAllGroupActive(false);
                    binding.swAllActive.setChecked(false);
                }

                myPreferencesManager.setAllGroupActive(b);
                break;
        }
    };

    private void initData() {
        itemAdapter.initItems(myPreferencesManager.getRuleList());

        binding.swAllActive.setChecked(myPreferencesManager.isAllActive());
        binding.swDirectActive.setChecked(myPreferencesManager.isAllDirectActive());
        binding.swGroupActive.setChecked(myPreferencesManager.isAllGroupActive());
    }
}
