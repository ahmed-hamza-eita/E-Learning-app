package com.hamza.e_learningapp.ui.instructor.create_course;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.hamza.e_learningapp.models.ModelCourse;
import com.hamza.e_learningapp.utils.Const;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateCourseViewModel extends ViewModel {
    MutableLiveData<String> createCourseLiveData = new MutableLiveData<String>();
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private ModelCourse model;

    @Inject
    public CreateCourseViewModel(FirebaseAuth auth, DatabaseReference reference) {
        this.auth = auth;
        ref = reference;
    }

    public void uploadCourse(ModelCourse model) {
        this.model = model;
        String id = ref.push().getKey();
        model.setCourseId(id);
        ref.child(Const.kEY_COURSES).child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                createCourseLiveData.setValue(Const.kEY_SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createCourseLiveData.setValue(e.getLocalizedMessage());
            }
        });
    }
}
