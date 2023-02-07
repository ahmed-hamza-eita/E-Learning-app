package com.hamza.e_learningapp.ui.showfiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.adapters.AdapterShowFiles;
import com.hamza.e_learningapp.databinding.FragmentShowFilesBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowFilesFragment extends BaseFragment {

    private FragmentShowFilesBinding binding;
    private ShowFilesViewModel showFilesViewModel;
    private AdapterShowFiles adapterShowFiles;
    private String id, name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowFilesBinding.inflate(inflater, container, false);
        showFilesViewModel = new ViewModelProvider(this).get(ShowFilesViewModel.class);
        adapterShowFiles = new AdapterShowFiles();
        id = ShowFilesFragmentArgs.fromBundle(getArguments()).getCourseId();
        name = ShowFilesFragmentArgs.fromBundle(getArguments()).getCourseName();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(true);
        actions();
        observers();
    }

    private void observers() {


        showFilesViewModel.getFiles(id);
        showFilesViewModel.filesLiveData.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapterShowFiles.setFileList(strings);
                binding.recyclerviewShowFiles.setAdapter(adapterShowFiles);
                loading(false);

            }
        });
        showFilesViewModel.e.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loading(false);
                showToast(s);
            }
        });
    }

    private void actions() {
        adapterShowFiles.setOnClick(new AdapterShowFiles.OnClick() {
            @Override
            public void onItemClick(String name) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
                startActivity(browserIntent);
            }
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}