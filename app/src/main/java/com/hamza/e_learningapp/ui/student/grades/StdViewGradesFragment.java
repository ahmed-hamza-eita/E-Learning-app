package com.hamza.e_learningapp.ui.student.grades;

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
import com.hamza.e_learningapp.databinding.FragmentStdViewGradesBinding;
import com.hamza.e_learningapp.models.ModelGrades;
import com.hamza.e_learningapp.ui.student.control.ControlStdFragment;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class StdViewGradesFragment extends BaseFragment {
    private FragmentStdViewGradesBinding binding;
    private String courseId, courseName;
    private ViewGradesViewModel viewGradesViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStdViewGradesBinding.inflate(inflater, container, false);
        viewGradesViewModel = new ViewModelProvider(this).get(ViewGradesViewModel.class);
        courseId = StdViewGradesFragmentArgs.fromBundle(getArguments()).getCourseId();
        courseName = StdViewGradesFragmentArgs.fromBundle(getArguments()).getCourseName();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewGradesViewModel.getGrades(courseId);
        observer();
        binding.txtF.setText(ControlStdFragment.quizGrade);
        binding.txtF1.setText(ControlStdFragment.attendanceGrade);

    }

    private void observer() {
        loading(true);
        viewGradesViewModel.gradesLiveData.observe(getViewLifecycleOwner(), modelGrades -> {
            loading(false);
            binding.txtGrade.setText(modelGrades.getAssignmentGrade() + "");
            binding.txtAttend.setText(modelGrades.getAttendanceGrade() + "");
        });
        viewGradesViewModel.e.observe(getViewLifecycleOwner(), s -> {
            showToast(s);
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