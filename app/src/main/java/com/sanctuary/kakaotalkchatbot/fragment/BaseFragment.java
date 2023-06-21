package com.sanctuary.kakaotalkchatbot.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.app.MyPreferencesManager;
import com.sanctuary.kakaotalkchatbot.databinding.CustomToastBinding;

public class BaseFragment extends Fragment {
    protected MyPreferencesManager myPreferencesManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPreferencesManager = MyPreferencesManager.getInstance(getContext());
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
}
