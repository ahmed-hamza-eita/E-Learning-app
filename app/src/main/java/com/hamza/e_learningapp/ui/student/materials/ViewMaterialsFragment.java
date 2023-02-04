package com.hamza.e_learningapp.ui.student.materials;

import static androidx.lifecycle.LiveDataKt.observe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.adapters.AdapterShowFiles;
import com.hamza.e_learningapp.databinding.FragmentViewMaterialsBinding;
import com.hamza.e_learningapp.models.ModelPDF;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewMaterialsFragment extends BaseFragment {
    private FragmentViewMaterialsBinding binding;
    private ViewMaterialsViewModel viewMaterialsViewModel;
    private String courseId, courseName;
    AdapterShowFiles adapterShowFiles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMaterialsViewModel = new ViewModelProvider(this).get(ViewMaterialsViewModel.class);
        courseId = ViewMaterialsFragmentArgs.fromBundle(getArguments()).getCourseId();
        courseName = ViewMaterialsFragmentArgs.fromBundle(getArguments()).getCourseName();
        adapterShowFiles = new AdapterShowFiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewMaterialsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(true);
        viewMaterialsViewModel.getFiles(courseId);
        actions();
        observer();
    }

    private void observer() {
        viewMaterialsViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            showToast(error);
            binding.imgNofiles.setVisibility(View.VISIBLE);
            binding.imgNofiles.setAlpha(0.5f);
            loading(false);
        });
        viewMaterialsViewModel.filesListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelPDF>>() {
            @Override
            public void onChanged(ArrayList<ModelPDF> modelPDFS) {
                adapterShowFiles.setFileList(modelPDFS);
                binding.viewMaterials.setAdapter(adapterShowFiles);
                loading(false);
            }
        });
    }

    private void actions() {
        adapterShowFiles.setOnClick(name -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(name)));
        });
    }


    private void loading(Boolean isLoading) {
        if (isLoading) {
            // binding.btnLogin.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            //binding.btnLogin.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}