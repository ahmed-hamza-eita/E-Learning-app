package com.hamza.e_learningapp.ui.student.materials;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.models.ModelPDF;
import com.hamza.e_learningapp.utils.Const;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ViewMaterialsViewModel extends ViewModel {
    private DatabaseReference ref;
    MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    ArrayList<ModelPDF> fileList = new ArrayList<>();
    MutableLiveData<ArrayList<ModelPDF>> filesListLiveData = new MutableLiveData<>();

    @Inject
    public ViewMaterialsViewModel(DatabaseReference reference) {
        this.ref = reference;

    }

    public void getFiles(String courseId) {
        ref.child(Const.REF_FILES).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        fileList.add(ds.getValue(ModelPDF.class));
                    }
                    filesListLiveData.setValue(fileList);
                } else {
                    errorLiveData.setValue("No files available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorLiveData.setValue(error.getMessage());
            }
        });
    }


}
