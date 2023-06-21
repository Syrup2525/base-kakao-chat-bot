package com.sanctuary.kakaotalkchatbot.util;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.CustomToastBinding;

/**
 * Created by Administrator on 2016-06-28.
 */
public class KeyHandleUtil {
    private static final int BACK_KEY_DELAY = 1000;  // 대기시간
    private static long mBackKeyPressedTime = 0;    // 뒤로가기 누른 시간

    public static void doubleBackFinish(Activity activity) {
        if (System.currentTimeMillis() > (mBackKeyPressedTime + BACK_KEY_DELAY)) {
            mBackKeyPressedTime = System.currentTimeMillis();

            showToast(activity.getString(R.string.confirm_back_pressed), activity);
        } else {
            activity.finish();
        }
    }

    protected static void showToast(String message, Activity activity) {
        CustomToastBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(), R.layout.custom_toast, null, false);

        binding.tvMessage.setText(message);

        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(binding.getRoot());
        toast.show();
    }
}