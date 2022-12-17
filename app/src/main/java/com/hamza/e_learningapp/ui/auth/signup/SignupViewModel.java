package com.hamza.e_learningapp.ui.auth.signup;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.models.ModelAuth;
import com.hamza.e_learningapp.utils.Const;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignupViewModel extends ViewModel {

    private FirebaseAuth auth;
    private DatabaseReference ref;
    private ModelAuth modelAuth;

    MutableLiveData<String> liveData = new MutableLiveData<>();

    @Inject
    public SignupViewModel(FirebaseAuth auth, DatabaseReference refrence) {
        this.auth = auth;
        ref = refrence;
    }

    public void signup(ModelAuth modelAuth, String password) {
        this.modelAuth = modelAuth;
        auth.createUserWithEmailAndPassword(modelAuth.getEmail(), password).addOnSuccessListener(
                authResult -> {
                    modelAuth.setUserID(authResult.getUser().getUid());
                    uploadData(modelAuth);
                }).addOnFailureListener(e -> liveData.setValue(e.getLocalizedMessage()));
    }

    private void uploadData(ModelAuth modelAuth) {
        ref.child(Const.kEY_REF).child(auth.getUid()).setValue(modelAuth).addOnSuccessListener(unused -> {
            MySharedPrefrance.setUserEmail(modelAuth.getEmail());
            MySharedPrefrance.setUserId(modelAuth.getUserID());
            MySharedPrefrance.setUserType(modelAuth.getType());
            MySharedPrefrance.setUserName(modelAuth.getName());
            liveData.setValue(Const.kEY_SUCCESS);
            MySharedPrefrance.putBoolean(Const.kEY_IS_SIGNED_IN, true);
        }).addOnFailureListener(e -> liveData.setValue(e.getLocalizedMessage()));
    }


}
