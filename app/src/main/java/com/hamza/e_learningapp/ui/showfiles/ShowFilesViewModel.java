package com.hamza.e_learningapp.ui.showfiles;

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
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowFilesViewModel extends ViewModel {
    private DatabaseReference ref;
    private ArrayList<String> files = new ArrayList<String>();
    MutableLiveData<String> e = new MutableLiveData<String>();
    MutableLiveData<List<String>> filesLiveData = new MutableLiveData<List<String>>();

    @Inject
    public ShowFilesViewModel(DatabaseReference reff) {
        this.ref = reff;

    }

    public void getFiles(String id) {
        ref.child(Const.REF_FILES).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        files.add(snapshot1.getValue(String.class));
                    }
                    filesLiveData.setValue(files);
                }else {
                    e.setValue("No materials");
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                e.setValue(error.getMessage());
            }
        });
    }
}
