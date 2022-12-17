package com.hamza.e_learningapp.di;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@dagger.Module
@InstallIn(SingletonComponent.class)
public class Module {

    @Singleton
    @Provides
    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    public DatabaseReference getRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Singleton
    @Provides
    public StorageReference getStorage() {
        return FirebaseStorage.getInstance().getReference();
    }
}
