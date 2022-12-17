package com.hamza.e_learningapp.ui.auth.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.models.ModelAuth;
import com.hamza.e_learningapp.utils.Const;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private ModelAuth model;

    MutableLiveData<String> loginLiveData = new MutableLiveData<String>();

    @Inject

    public LoginViewModel(FirebaseAuth auth, DatabaseReference refrence) {
        this.auth = auth;
        ref = refrence;
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                getData(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginLiveData.setValue(e.getLocalizedMessage());
            }
        });

    }

    private void getData(String uid) {
        ref.child(Const.kEY_REF).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(ModelAuth.class);
                MySharedPrefrance.setUserName(model.getName());
                MySharedPrefrance.setUserType(model.getType());
                MySharedPrefrance.putBoolean(Const.kEY_IS_SIGNED_IN, true);
                MySharedPrefrance.setUserEmail(model.getEmail());
                MySharedPrefrance.setUserId(model.getUserID());
                loginLiveData.setValue(Const.kEY_SUCCESS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginLiveData.setValue(error.getMessage());
            }
        });
    }
}
