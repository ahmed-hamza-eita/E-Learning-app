package com.hamza.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.databinding.ItemContainerRecieveMessageBinding;
import com.hamza.e_learningapp.databinding.ItemContainerSendMessageBinding;
import com.hamza.e_learningapp.models.ModelChat;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelChat> messageList;

    public void setMessageList(List<ModelChat> messageList) {
        this.messageList = messageList;
    }

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return  new SendMessageViewHolder(ItemContainerSendMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false));
        }
        else
            return new ReceieveMessageViewHolder(ItemContainerRecieveMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelChat m = messageList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_SENT:
                ((SendMessageViewHolder) holder).setData(m);
                break;
            case VIEW_TYPE_RECEIVED:
                ((ReceieveMessageViewHolder) holder).setData(m);
        }
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ModelChat m = messageList.get(position);
        if (m.getSenderId().equals(MySharedPrefrance.getUserId())) {
            return VIEW_TYPE_SENT;
        } else
            return VIEW_TYPE_RECEIVED;
    }


    static class SendMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerSendMessageBinding binding;

        SendMessageViewHolder(ItemContainerSendMessageBinding i) {
            super(i.getRoot());
            binding = i;
        }

        void setData(ModelChat modelChat) {
            binding.txtMessage.setText(modelChat.getMessage());
        }

    }

    static class ReceieveMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerRecieveMessageBinding binding;

        ReceieveMessageViewHolder(ItemContainerRecieveMessageBinding i) {
            super(i.getRoot());
            binding = i;
        }

        void setData(ModelChat modelChat) {
            binding.txtMessage.setText(modelChat.getMessage());
            binding.txtUserName.setText(modelChat.getUserName());
        }
    }
}
