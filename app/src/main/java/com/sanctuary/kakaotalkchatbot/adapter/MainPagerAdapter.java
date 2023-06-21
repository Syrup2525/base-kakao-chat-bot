package com.sanctuary.kakaotalkchatbot.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sanctuary.kakaotalkchatbot.fragment.main.RuleSettingFragment;
import com.sanctuary.kakaotalkchatbot.fragment.main.CommandFragment;
import com.sanctuary.kakaotalkchatbot.fragment.main.SendHistoryFragment;
import com.sanctuary.kakaotalkchatbot.fragment.main.SettingFragment;
import com.sanctuary.kakaotalkchatbot.fragment.main.SupportFragment;

import java.util.ArrayList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    public static final int TAB1 = 0;
    public static final int TAB2 = 1;
    public static final int TAB3 = 2;
    public static final int TAB4 = 3;
    public static final int TAB5 = 4;
    public static final int FRAGMENT_COUNT = 5;

    ArrayList<Fragment> items = new ArrayList<>();

    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        items.add(new RuleSettingFragment());
        items.add(new SendHistoryFragment());
        items.add(new CommandFragment());
        items.add(new SettingFragment());
        items.add(new SupportFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
