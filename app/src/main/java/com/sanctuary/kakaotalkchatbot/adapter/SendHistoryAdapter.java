package com.sanctuary.kakaotalkchatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sanctuary.kakaotalkchatbot.R;
import com.sanctuary.kakaotalkchatbot.databinding.ListSendHistoryBinding;
import com.sanctuary.kakaotalkchatbot.models.Log;

public class SendHistoryAdapter extends BaseRecyclerViewAdapter<Log, SendHistoryAdapter.ViewHolder>{
    public SendHistoryAdapter(Context context) {super(context);}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_send_history, parent, false));
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        Log item = items.get(position);

        if (holder.binding == null) {
            return;
        }

        if (item.getErrorMessage() != null) {
            holder.binding.tvMessage.setText(item.getErrorMessage());
            holder.binding.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.danger));

            holder.binding.ivSendResult.setImageResource(R.drawable.ic_info);
            holder.binding.ivSendResult.setColorFilter(ContextCompat.getColor(context, R.color.danger));

            holder.binding.tvSendResult.setText("전송 실패");
            holder.binding.tvSendResult.setTextColor(ContextCompat.getColor(context, R.color.danger));
        } else {
            holder.binding.tvMessage.setText(item.getMessage());
            holder.binding.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.gray));

            holder.binding.ivSendResult.setImageResource(R.drawable.ic_success);
            holder.binding.ivSendResult.setColorFilter(ContextCompat.getColor(context, R.color.primary));

            holder.binding.tvSendResult.setText("전송 성공");
            holder.binding.tvSendResult.setTextColor(ContextCompat.getColor(context, R.color.primary));
        }

        holder.binding.tvDate.setText(item.getDate());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListSendHistoryBinding binding;

        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
