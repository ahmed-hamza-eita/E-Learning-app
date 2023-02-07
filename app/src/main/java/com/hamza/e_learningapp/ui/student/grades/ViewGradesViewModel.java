package com.hamza.e_learningapp.ui.student.grades;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.models.ModelGrades;
import com.hamza.e_learningapp.utils.Const;
import com.hamza.e_learningapp.utils.Helper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ViewGradesViewModel extends ViewModel {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    MutableLiveData<ModelGrades> gradesLiveData = new MutableLiveData<>();
    MutableLiveData<String> e = new MutableLiveData<>();

    @Inject
    public ViewGradesViewModel(DatabaseReference reference, FirebaseAuth auth) {
        this.ref = reference;
        this.auth = auth;
    }

    public void getGrades(String courseId) {
        ref.child(Const.REF_COURSES).child(courseId).child(Const.REF_COURSE_MEMBERS)
                .child(Helper.removeDot(MySharedPrefrance.getUserEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelGrades m = snapshot.getValue(ModelGrades.class);
                        gradesLiveData.setValue(m);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        e.setValue(error.getMessage());
                    }
                });
    }
}
