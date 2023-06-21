package com.sanctuary.kakaotalkchatbot.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.DialogNoticeBinding;

public class NoticeDialog extends AlertDialog {
    private final Context context;
    private CharSequence message;
    private boolean isShowNegativeButton = true;
    private boolean isBackPressButton = true;

    private NoticeDialogCallbackListener noticeDialogCallbackListener;

    public NoticeDialog(Context context) {
        super(context);
        this.context = context;
    }

    public NoticeDialog setShowNegativeButton(boolean b) {
        this.isShowNegativeButton = b;
        return this;
    }

    public NoticeDialog setBackPressButton(boolean b) {
        this.isBackPressButton = b;
        return this;
    }

    public NoticeDialog setMsg(CharSequence message) {
        this.message = message;
        return this;
    }

    public NoticeDialog setNoticeDialogCallbackListener(NoticeDialogCallbackListener noticeDialogCallbackListener) {
        this.noticeDialogCallbackListener = noticeDialogCallbackListener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogNoticeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.dialog_notice, null, false);
        setContentView(binding.getRoot());

        // 다이얼로그 영역 밖 터치시 다이얼로그가 사라지지 않도록 설정
        setCanceledOnTouchOutside(false);

        binding.tvMessage.setText(message);

        binding.loPositive.setOnClickListener(v -> {
            if (noticeDialogCallbackListener != null) {
                noticeDialogCallbackListener.positive();
            }

            dismiss();
        });

        binding.loNegative.setOnClickListener(v -> {
            if (noticeDialogCallbackListener != null) {
                noticeDialogCallbackListener.negative();
            }

            dismiss();
        });

        binding.loNegative.setVisibility(isShowNegativeButton ? View.VISIBLE : View.GONE);
    }

    public interface NoticeDialogCallbackListener {
        void positive();

        void negative();
    }

    @Override
    public void onBackPressed() {
        if (isBackPressButton) {
            super.onBackPressed();
        }
    }
}