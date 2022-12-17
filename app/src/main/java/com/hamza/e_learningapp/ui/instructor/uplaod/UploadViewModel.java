package com.hamza.e_learningapp.ui.instructor.uplaod;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hamza.e_learningapp.utils.Const;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UploadViewModel extends ViewModel {

    private DatabaseReference ref;
    private StorageReference storage, sRef;
    MutableLiveData<String> uploadLiveData = new MutableLiveData();

    @Inject
    public UploadViewModel(DatabaseReference reff, StorageReference storage) {
        this.storage = storage;
        ref = reff;
    }

    public void uploadFile(Uri path, String courseID) {
        if (path != null) {
            sRef = storage.child("file/" + courseID + "/"   + System.currentTimeMillis());
            sRef.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final StorageReference path = sRef;
                    path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String pathF = uri.toString();
                            ref.child(Const.REF_FILES).child(courseID).push().setValue(pathF);
                            uploadLiveData.setValue(Const.kEY_SUCCESS);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    uploadLiveData.setValue(e.getLocalizedMessage());
                }
            });
        }
    }
}
