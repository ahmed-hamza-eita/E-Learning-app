package com.hamza.e_learningapp.ui.instructor.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.models.ModelCourse;
import com.hamza.e_learningapp.utils.Const;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class InstructorHomeViewModel extends ViewModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;
      ArrayList<ModelCourse> courseList = new ArrayList<ModelCourse>();
    MutableLiveData<ArrayList<ModelCourse>> courseListLiveData = new MutableLiveData<ArrayList<ModelCourse>>();
    MutableLiveData<String> errorLiveData = new MutableLiveData<String>();

    @Inject
    public InstructorHomeViewModel(FirebaseAuth auth, DatabaseReference reference) {
        this.auth = auth;
        ref = reference;
    }

    public void getCourses() {
        ref.child(Const.kEY_COURSES).orderByChild(Const.REF_INSTRUCTOR_ID).equalTo(MySharedPrefrance.getUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courseList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            courseList.add(ds.getValue(ModelCourse.class));
                        }
                        courseListLiveData.setValue(courseList);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        errorLiveData.setValue(error.getMessage());
                    }
                });
    }

}
