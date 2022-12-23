package com.hamza.e_learningapp.ui.instructor.uplaod.showfiles;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.hamza.e_learningapp.models.ModelPDF;
import com.hamza.e_learningapp.utils.Const;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowFilesViewModel extends ViewModel {
    private ArrayList<ModelPDF> files = new ArrayList<ModelPDF>();
    private DatabaseReference ref;
    private StorageReference storage;
    MutableLiveData<String> e = new MutableLiveData<String>();
    MutableLiveData<ArrayList<ModelPDF>> filesLiveData = new MutableLiveData<ArrayList<ModelPDF>>();

    @Inject
    public ShowFilesViewModel(DatabaseReference reff, StorageReference storagee) {
        this.ref = reff;
        this.storage = storagee;
    }

    public void getFiles(String id) {
        ref.child(Const.REF_FILES).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        files.add(ds.getValue(ModelPDF.class));
                    }
                    filesLiveData.setValue(files);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                e.setValue(error.getMessage());
            }
        });
    }
}
