package com.hamza.e_learningapp.ui.instructor.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.adapters.AdapterChat;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.databinding.FragmentChatBinding;
import com.hamza.e_learningapp.models.ModelChat;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatFragment extends BaseFragment {

    private FragmentChatBinding binding;
    private ChatViewModel chatViewModel;
    private AdapterChat adapterChat;
    private String name, id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        adapterChat = new AdapterChat();
        name = ChatFragmentArgs.fromBundle(getArguments()).getCourseName();
        id = ChatFragmentArgs.fromBundle(getArguments()).getCourseId();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        observer();
    }

    private void actions() {

        binding.frameSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    private void send() {
        String message = binding.inputMessage.getText().toString();
        if (message.equals("")) {
            binding.inputMessage.setError(getString(R.string.requried));
        } else {
            chatViewModel.sendMessage(message, id, MySharedPrefrance.getUserName());
            binding.inputMessage.setText("");
        }
    }

    private void observer() {
        chatViewModel.getMessage(id);
        chatViewModel.chatLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelChat>>() {
            @Override
            public void onChanged(ArrayList<ModelChat> modelChats) {
                adapterChat.setMessageList(modelChats);
                binding.chatRecycler.setAdapter(adapterChat);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}