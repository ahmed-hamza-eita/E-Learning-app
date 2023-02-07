package com.hamza.e_learningapp.ui.instructor.create_course;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.databinding.FragmentCreateCourseBinding;
import com.hamza.e_learningapp.models.ModelCourse;
import com.hamza.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateCourseFragment extends BaseFragment {


    private FragmentCreateCourseBinding binding;
    private CreateCourseViewModel createCourseViewModel;
    private ModelCourse model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateCourseBinding.inflate(inflater, container, false);
        createCourseViewModel = new ViewModelProvider(this).get(CreateCourseViewModel.class);
        model = new ModelCourse();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        observer();
    }

    private void observer() {
        loading(false);
        createCourseViewModel.createCourseLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (Const.kEY_SUCCESS.equals(s)) {
                    showToast(s);
                    loading(false);
                    binding.editAttendGrade.setText("");
                    binding.editProjectGrade.setText("");
                    binding.editQuizGrade.setText("");
                    binding.edtCourseName.setText("");
                } else {
                    showToast(s);
                    loading(false);
                }
            }
        });
    }

    private void actions() {
        binding.ConfirmAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validations();
            }
        });
    }

    private void validations() {
        String courseName = binding.edtCourseName.getText().toString().trim();
        String quizGrade = binding.editQuizGrade.getText().toString().trim();
        String projectsGrade = binding.editProjectGrade.getText().toString().trim();
        String attendanceGrade = binding.editAttendGrade.getText().toString().trim();
        if (courseName.isEmpty()) {
            binding.edtCourseName.setError(getString(R.string.requried));
        } else if (quizGrade.isEmpty()) {
            binding.editQuizGrade.setError(getString(R.string.requried));
        } else if (projectsGrade.isEmpty()) {
            binding.editProjectGrade.setError(getString(R.string.requried));
        } else if (attendanceGrade.isEmpty()) {
            binding.editAttendGrade.setError(getString(R.string.requried));
        } else {
            loading(true);
            model.setCourseName(courseName);
            model.setAttendanceGrade(Double.parseDouble(attendanceGrade));
            model.setProjectsGrade(Double.parseDouble(projectsGrade));
            model.setAssignmentGrade(Double.parseDouble(quizGrade));
            model.setInstructorId(MySharedPrefrance.getUserId());
            createCourseViewModel.uploadCourse(model);

        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.ConfirmAddCourse.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.ConfirmAddCourse.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}