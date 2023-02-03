package com.hamza.e_learningapp.ui.instructor.attendance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.hamza.e_learningapp.utils.Const;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AttendViewModel extends ViewModel {

    private DatabaseReference ref ;
    MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    @Inject
    public AttendViewModel(DatabaseReference reference) {
        this.ref = reference;
    }

    public void attend(String courseId, int code) {
        ref.child(Const.REF_ATTENDANCE).child(courseId).child(code + "").setValue("")
                .addOnSuccessListener(unused -> errorLiveData.setValue(Const.kEY_SUCCESS))
                .addOnFailureListener(e -> errorLiveData.setValue(e.getLocalizedMessage()));
    }
}
