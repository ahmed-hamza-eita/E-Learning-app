package com.hamza.e_learningapp.ui.student.home;

import android.view.View;

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
import com.hamza.e_learningapp.utils.Helper;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class StudentHomeViewModel extends ViewModel {

    private FirebaseAuth auth;
    private DatabaseReference ref;
    MutableLiveData<String> enrollLiveData = new MutableLiveData<String>();
    MutableLiveData<String> errorLiveData = new MutableLiveData<String>();
    private ArrayList<ModelCourse> list ;
    MutableLiveData<ArrayList<ModelCourse>> coursesLiveData = new MutableLiveData<ArrayList<ModelCourse>>();

    @Inject
    public StudentHomeViewModel(FirebaseAuth auth, DatabaseReference reference) {
        this.auth = auth;
        ref = reference;
        list = new ArrayList<>();

    }

    public void enrollToCourse(String courseId) {
        ref.child(Const.kEY_COURSES).child(courseId).child(Const.KEY_COURSE_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            enrollLiveData.setValue(snapshot.getValue().toString());

                        } else {
                            errorLiveData.setValue(Const.kEY_FAIL);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        errorLiveData.setValue(error.getMessage());
                    }
                });
    }

    public void getCourses() {
        ref.child(Const.REF_COURSE_MEMBERS).child(Helper.removeDot(MySharedPrefrance.getUserEmail())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    list.add(ds.getValue(ModelCourse.class));
                }
                coursesLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }
}
