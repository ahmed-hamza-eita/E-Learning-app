package com.hamza.e_learningapp.ui.student.control;

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

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ControlStdViewModel extends ViewModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    MutableLiveData<ModelCourse> modelCourseLiveData = new MutableLiveData<>();
    MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    MutableLiveData<String> attendLiveData = new MutableLiveData<>();


    @Inject
    public ControlStdViewModel(FirebaseAuth auth, DatabaseReference reference) {
        this.ref = reference;
        this.auth = auth;
    }

    public void getCoursesData(String courseID) {
        ref.child(Const.REF_COURSES).child(courseID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ModelCourse modelCourse = snapshot.getValue(ModelCourse.class);
                modelCourseLiveData.setValue(modelCourse);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });

    }

    private void registerAttendance(String courseId, String attendanceCode) {
        ref.child(Const.REF_ATTENDANCE).child(courseId).child(attendanceCode).child(auth.getUid())
                .setValue(auth.getUid()).addOnSuccessListener(unused -> {

                    attendLiveData.setValue(Const.kEY_SUCCESS);
                    addDegreeForAttendance(courseId);

                }).addOnFailureListener(e -> {
                    attendLiveData.setValue(e.getLocalizedMessage());
                });

    }

    private void addDegreeForAttendance(String courseId) {
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS)
                .child(Helper.removeDot(MySharedPrefrance.getUserEmail()))
                .child(Const.ATTENDANCE_GRADES).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int degree = snapshot.getValue(Integer.class);


                        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS)
                                .child(Helper.removeDot(MySharedPrefrance.getUserEmail()))
                                .child(Const.ATTENDANCE_GRADES).setValue(degree + 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        errorLiveData.setValue(error.getMessage());
                    }
                });


    }

    public void checkingAttendance(String courseId, String attendanceCode) {
        ref.child(Const.REF_ATTENDANCE).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(attendanceCode)) {
                    if (snapshot.child(attendanceCode).hasChild(MySharedPrefrance.getUserId())) {
                        attendLiveData.setValue("Already Attended");
                    } else {
                        registerAttendance(courseId, attendanceCode);
                    }
                } else {
                    attendLiveData.setValue("Wrong Code");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }
}
