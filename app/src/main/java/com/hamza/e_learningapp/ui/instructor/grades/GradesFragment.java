package com.hamza.e_learningapp.ui.instructor.grades;

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
import com.hamza.e_learningapp.adapters.AdapterGrades;
import com.hamza.e_learningapp.databinding.FragmentGradesBinding;
import com.hamza.e_learningapp.models.ModelMembers;
import com.hamza.e_learningapp.ui.instructor.home.InstructorHomeViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GradesFragment extends BaseFragment {

    private FragmentGradesBinding binding;
    private String name, id;
    private InstructorHomeViewModel instructorGradesViewModel;
    private AdapterGrades adapterGrades;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = GradesFragmentArgs.fromBundle(getArguments()).getCourseName();
        id = GradesFragmentArgs.fromBundle(getArguments()).getCourseId();
        instructorGradesViewModel = new ViewModelProvider(this).get(InstructorHomeViewModel.class);
        adapterGrades = new AdapterGrades();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(true);
        instructorGradesViewModel.getGrades(id);
        observers();
    }

    private void observers() {
        instructorGradesViewModel.gradesListLiveData.observe(getViewLifecycleOwner(), modelMembers -> {
            if (modelMembers.size() == 0) {
                showToast("No Grades Found");
                loading(false);
            } else {
                loading(false);
                adapterGrades.setGradesList(modelMembers);
                binding.recyclerViewGrades.setAdapter(adapterGrades);
            }
        });
        instructorGradesViewModel.errorGLiveData.observe(getViewLifecycleOwner(), error -> {
            showToast(error);
            loading(false);
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