package com.hamza.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.databinding.ItemChatbotBinding;
import com.hamza.e_learningapp.models.ChatBotModel;

import java.util.List;

public class AdapterChatBot extends RecyclerView.Adapter<AdapterChatBot.Holder> {
    private List<ChatBotModel> list;

    public void setList(List<ChatBotModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemChatbotBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ChatBotModel model = list.get(position);
        if (model.getSentBy().equals(ChatBotModel.SEND_BY_ME)){
            holder.binding.leftChat.setVisibility(View.GONE);
            holder.binding.rightChat.setVisibility(View.VISIBLE);
            holder.binding.txtChatPerson.setText(model.getMessage());
        }else {
            holder.binding.leftChat.setVisibility(View.VISIBLE);
            holder.binding.rightChat.setVisibility(View.GONE);
            holder.binding.txtChatGpt.setText(model.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ItemChatbotBinding binding;

        public Holder(@NonNull ItemChatbotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
