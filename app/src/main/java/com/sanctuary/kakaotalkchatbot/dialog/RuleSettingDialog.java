package com.sanctuary.kakaotalkchatbot.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;
import com.sanctuary.kakaotalkchatbot.databinding.CustomToastBinding;
import com.sanctuary.kakaotalkchatbot.databinding.DialogRuleSettingBinding;
import com.sanctuary.kakaotalkchatbot.models.Rule;

public class RuleSettingDialog extends AlertDialog {
    private DialogRuleSettingBinding binding;

    private final Context context;
    private RuleSettingDialogCallbackListener ruleSettingDialogCallbackListener;
    private Rule rule;

    public RuleSettingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public RuleSettingDialog setRule(Rule rule) {
        this.rule = rule;
        return this;
    }

    public RuleSettingDialog setRuleSettingDialogCallbackListener(RuleSettingDialogCallbackListener ruleSettingDialogCallbackListener) {
        this.ruleSettingDialogCallbackListener = ruleSettingDialogCallbackListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_rule_setting, null, false);
        setContentView(binding.getRoot());

        if (getWindow() != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }

        initLayout();
        initData();
    }

    private void initLayout() {
        // 일치 여부
        binding.btMatchTypeExact.setOnClickListener(matchTypeOnClickListener);
        binding.btMatchTypeInclude.setOnClickListener(matchTypeOnClickListener);

        // 채팅방 타입
        binding.btRoomTypeAll.setOnClickListener(roomTypeOnClickListener);
        binding.btRoomTypeGroup.setOnClickListener(roomTypeOnClickListener);
        binding.btRoomTypeDirect.setOnClickListener(roomTypeOnClickListener);

        binding.btPositive.setOnClickListener(v -> {
            String word = binding.etInput.getText().toString().trim();

            if ("".equals(word)) {
                showToast("채팅방 이름을 입력 해주세요.");
                return;
            }

            rule.setRoomName(word);

            MyPreferencesManager myPreferencesManager = MyPreferencesManager.getInstance(getContext());
            if (myPreferencesManager.getRuleList().contains(rule)) {
                showToast("이미 존재하는 규칙입니다.");
                return;
            }

            ruleSettingDialogCallbackListener.positive(rule);
            dismiss();
        });

        binding.btNegative.setOnClickListener(v ->{
            ruleSettingDialogCallbackListener.negative();
            dismiss();
        });
    }

    private final View.OnClickListener matchTypeOnClickListener = v -> {
        tabUnSelect(binding.btMatchTypeExact);
        tabUnSelect(binding.btMatchTypeInclude);

        switch (v.getId()) {
            case R.id.bt_match_type_exact:
                tabSelect(binding.btMatchTypeExact);
                rule.setMatchType(Rule.MATCH_TYPE_EXACT);
                break;

            case R.id.bt_match_type_include:
                tabSelect(binding.btMatchTypeInclude);
                rule.setMatchType(Rule.MATCH_TYPE_INCLUDING);
                break;
        }
    };

    private final View.OnClickListener roomTypeOnClickListener = v -> {
        tabUnSelect(binding.btRoomTypeAll);
        tabUnSelect(binding.btRoomTypeGroup);
        tabUnSelect(binding.btRoomTypeDirect);

        switch (v.getId()) {
            case R.id.bt_room_type_all:
                tabSelect(binding.btRoomTypeAll);
                rule.setRoomType(Rule.ROOM_TYPE_ALL);
                break;

            case R.id.bt_room_type_group:
                tabSelect(binding.btRoomTypeGroup);
                rule.setRoomType(Rule.ROOM_TYPE_GROUP);
                break;

            case R.id.bt_room_type_direct:
                tabSelect(binding.btRoomTypeDirect);
                rule.setRoomType(Rule.ROOM_TYPE_DIRECT);
                break;
        }
    };

    private void initData() {
        if (rule == null) {
            rule = new Rule();
            rule.setMatchType(Rule.MATCH_TYPE_EXACT);
            rule.setRoomType(Rule.ROOM_TYPE_ALL);
        } else {
            binding.etInput.setText(rule.getRoomName());
        }

        // 일치 여부
        switch (rule.getMatchType()) {
            case Rule.MATCH_TYPE_EXACT:
                binding.btMatchTypeExact.callOnClick();
                break;

            case Rule.MATCH_TYPE_INCLUDING:
                binding.btMatchTypeInclude.callOnClick();
                break;
        }

        // 채팅방 타입
        switch (rule.getRoomType()) {
            case Rule.ROOM_TYPE_ALL:
                binding.btRoomTypeAll.callOnClick();
                break;

            case Rule.ROOM_TYPE_GROUP:
                binding.btRoomTypeGroup.callOnClick();
                break;

            case Rule.ROOM_TYPE_DIRECT:
                binding.btRoomTypeDirect.callOnClick();
                break;
        }
    }

    private void tabUnSelect(Button button) {
        button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.un_select));
    }

    private void tabSelect(Button button) {
        button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    protected void showToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        CustomToastBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_toast, null, false);

        binding.tvMessage.setText(message);

        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(binding.getRoot());
        toast.show();
    }

    public interface RuleSettingDialogCallbackListener {
        void positive(Rule rule);

        void negative();
    }
}
