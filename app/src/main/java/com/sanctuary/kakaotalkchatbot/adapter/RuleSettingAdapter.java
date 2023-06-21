package com.sanctuary.kakaotalkchatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.ListRuleBinding;
import com.sanctuary.kakaotalkchatbot.models.Rule;

public class RuleSettingAdapter extends BaseRecyclerViewAdapter<Rule, RuleSettingAdapter.ViewHolder> {
    public RuleSettingAdapter(Context context) { super(context); }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rule, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        if (holder.binding == null) {
            return;
        }

        Rule item = getItem(position);

        holder.binding.tvWord.setText(item.getRoomName());
        holder.binding.tvMatchType.setText(item.getMatchType());
        holder.binding.tvRoomType.setText(item.getRoomType());

        holder.binding.loContent.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        });

        holder.binding.ivDelete.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListRuleBinding binding;

        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
