package com.hamza.e_learningapp.ui.instructor.quiz;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.hamza.e_learningapp.models.ModelQuiz;
import com.hamza.e_learningapp.utils.Const;


import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlin.jvm.internal.Ref;

@HiltViewModel
public class CreateQuizViewModel extends ViewModel {
    private DatabaseReference ref;
    MutableLiveData<String> msg = new MutableLiveData<>();

    @Inject
    public CreateQuizViewModel(DatabaseReference reference) {
        ref = reference;
    }

    public void uploadQuiz(ArrayList<ModelQuiz> list, String courseId) {
        ref.child(Const.REF_QUIZ).child(courseId).push().setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                msg.setValue(Const.kEY_SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                msg.setValue(e.getLocalizedMessage());
            }
        });
    }
}
