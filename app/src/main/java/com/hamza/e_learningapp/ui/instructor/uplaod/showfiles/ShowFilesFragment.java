package com.hamza.e_learningapp.ui.instructor.uplaod.showfiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.adapters.AdapterShowFiles;
import com.hamza.e_learningapp.databinding.FragmentShowFilesBinding;
import com.hamza.e_learningapp.models.ModelPDF;
import com.hamza.e_learningapp.utils.Const;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        actions();
        observers();
    }

    private void observers() {
        loading(true);
        showFilesViewModel.getFiles(id);
        showFilesViewModel.filesLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelPDF>>() {
            @Override
            public void onChanged(ArrayList<ModelPDF> modelPDFS) {
                loading(false);
                if (modelPDFS != null) {
                    adapterShowFiles.setFileList(modelPDFS);
                    binding.recyclerviewShowFiles.setAdapter(adapterShowFiles);

                }
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
        adapterShowFiles.setOnClick(new AdapterShowFiles.onClick() {
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