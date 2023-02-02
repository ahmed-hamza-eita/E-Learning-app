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
import com.hamza.e_learningapp.models.ModelMembers;
import com.hamza.e_learningapp.utils.Const;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class InstructorHomeViewModel extends ViewModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    //Course
    public ArrayList<ModelCourse> courseList = new ArrayList<ModelCourse>();
    public  MutableLiveData<ArrayList<ModelCourse>> courseListLiveData = new MutableLiveData<ArrayList<ModelCourse>>();
    public   MutableLiveData<String> errorCLiveData = new MutableLiveData<String>();

    //Grades
    public ArrayList<ModelMembers> gradesList = new ArrayList<ModelMembers>();
    public MutableLiveData<ArrayList<ModelMembers>> gradesListLiveData = new MutableLiveData<ArrayList<ModelMembers>>();
    public    MutableLiveData<String> errorGLiveData = new MutableLiveData<String>();

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
                        errorCLiveData.setValue(error.getMessage());
                    }
                });
    }

    public void getGrades(String courseId) {
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    gradesList.add(ds.getValue(ModelMembers.class));
                }
                gradesListLiveData.setValue(gradesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorGLiveData.setValue(error.getMessage());
            }
        });
    }

}
