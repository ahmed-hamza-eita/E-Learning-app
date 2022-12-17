package com.hamza.e_learningapp.ui.instructor.add_student;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.models.ModelMembers;
import com.hamza.e_learningapp.utils.Const;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddStudentViewModel extends ViewModel {
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private ModelMembers modelMembers;
    private ArrayList<ModelMembers> membersList;
    MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<ModelMembers>> membersLiveData = new MutableLiveData<>();

    @Inject
    public AddStudentViewModel(FirebaseAuth auth, DatabaseReference reference) {
        ref = reference;
        this.auth = auth;
        modelMembers = new ModelMembers();
        membersList = new ArrayList<>();
    }

    public void addStudent(String email, String courseId, String courseName) {
        modelMembers.setStudentEmail(email);
        modelMembers.setCourseId(courseId);
        modelMembers.setCourseName(courseName);
        modelMembers.setAttendanceGrade(0);
        modelMembers.setQuizGrade(0);

        ref.child(Const.REF_COURSE_MEMBERS).child(email).child(courseId).setValue(modelMembers);

        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS).child(email)
                .setValue(modelMembers).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        errorLiveData.setValue(Const.kEY_SUCCESS);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorLiveData.setValue(e.getLocalizedMessage());
                    }
                });
    }

    public void getStudents(String courseId) {
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        membersList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            membersList.add(ds.getValue(ModelMembers.class));
                        }
                        membersLiveData.setValue(membersList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void deleteStudent(String courseId, String email) {
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS).child(email).setValue(null);
        ref.child(Const.REF_COURSE_MEMBERS).child(email).child(courseId).setValue(null);
    }

}
