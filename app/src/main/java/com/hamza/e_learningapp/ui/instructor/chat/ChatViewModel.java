package com.hamza.e_learningapp.ui.instructor.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.models.ModelChat;
import com.hamza.e_learningapp.utils.Const;

import java.io.Closeable;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;

    ArrayList<ModelChat> list = new ArrayList<ModelChat>();
    MutableLiveData<ArrayList<ModelChat>> chatLiveData = new MutableLiveData();
    MutableLiveData<String> errorLiveData = new MutableLiveData();

    @Inject
    public ChatViewModel(FirebaseAuth auth, DatabaseReference reference) {
        this.auth = auth;
        ref = reference;
    }

    public void sendMessage(String message, String courseId, String userName) {
        ref.child(Const.REF_CHAT).child(courseId).push().setValue(
                new ModelChat(message, MySharedPrefrance.getUserId(), userName)
        );
    }

    public void getMessage(String courseID) {
        ref.child(Const.REF_CHAT).child(courseID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    list.add(ds.getValue(ModelChat.class));
                }
                chatLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(String.valueOf(error));
            }
        });
    }


}
