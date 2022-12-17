package com.hamza.e_learningapp.ui.instructor.uplaod;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.databinding.FragmentUplaodBinding;
import com.hamza.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UplaodFragment extends BaseFragment {

    private FragmentUplaodBinding binding;
    private UploadViewModel uploadViewModel;
    private String id, name;
    Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUplaodBinding.inflate(inflater, container, false);
        uploadViewModel = new ViewModelProvider(this).get(UploadViewModel.class);
        id = UplaodFragmentArgs.fromBundle(getArguments()).getCourseId();
        name = UplaodFragmentArgs.fromBundle(getArguments()).getCourseName();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actions();
        observer();
        loading(false);
    }

    private void observer() {
        uploadViewModel.uploadLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loading(false);
                showToast(s);
                NavHostFragment.findNavController(UplaodFragment.this).popBackStack();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null && requestCode == 0) {
            uri = data.getData();
            binding.pdfView.fromUri(uri)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .enableDoubletap(true)
                    .load();
            binding.imgUplaod.setVisibility(View.GONE);
            binding.txtAdd.setVisibility(View.GONE);
        }
    }

    private void actions() {
        binding.imgUplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType(Const.FILE_TYPE);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, 0);

            }
        });
        binding.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType(Const.FILE_TYPE);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, 0);
            }
        });
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              loading(false);
                if (uri == null) {
                   loading(false);
                    showToast("Please select pdf");
                } else {

                    uploadViewModel.uploadFile(uri, id);
                    loading(true);
                }

            }
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.upload.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.upload.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}