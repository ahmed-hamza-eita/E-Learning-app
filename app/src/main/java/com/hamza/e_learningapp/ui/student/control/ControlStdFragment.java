package com.hamza.e_learningapp.ui.student.control;

import android.annotation.SuppressLint;
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
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.databinding.FragmentControlStdBinding;
import com.hamza.e_learningapp.models.ModelCourse;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ControlStdFragment extends BaseFragment {

    private FragmentControlStdBinding binding;
    private String courseId, courseName, attendanceCode;
    public static String attendanceGrade, quizGrade;
    private ControlStdViewModel controlStdViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = ControlStdFragmentArgs.fromBundle(getArguments()).getCourseId();
        courseName = ControlStdFragmentArgs.fromBundle(getArguments()).getCourseName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentControlStdBinding.inflate(inflater, container, false);
        controlStdViewModel = new ViewModelProvider(this).get(ControlStdViewModel.class);
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controlStdViewModel.getCoursesData(courseId);
        binding.stdName.setText("Name: " + MySharedPrefrance.getUserName());
        onClicks();
        observers();
    }

    private void observers() {
        controlStdViewModel.errorLiveData.observe(getViewLifecycleOwner(), s -> {
            showToast(s);
        });

        controlStdViewModel.modelCourseLiveData.observe(getViewLifecycleOwner(), modelCourse -> {
            attendanceGrade = modelCourse.getAttendanceGrade() + "";
            quizGrade = modelCourse.getAssignmentGrade() + "";

        });

        controlStdViewModel.attendLiveData.observe(getViewLifecycleOwner(), s -> {
            if ("".equals(s)) {
                return;
            } else {
                loading(false);
                binding.attendStd.setText("");
                binding.attendStd.clearFocus();
                showToast(s);
                controlStdViewModel.attendLiveData.setValue("");

            }
        });
    }

    private void onClicks() {
        binding.Btnactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceCode = binding.attendStd.getText().toString().trim();
                if (attendanceCode.isEmpty()) {
                    binding.attendStd.setError("Attendance code is required");
                } else {
                    loading(true);
                    controlStdViewModel.checkingAttendance(courseId, attendanceCode);
                }
            }
        });
        binding.cardChat.setOnClickListener(view -> navigate(ControlStdFragmentDirections.actionControlStdFragmentToChatFragment(courseName, courseId)));
        binding.cardMaterials.setOnClickListener(view -> navigate(ControlStdFragmentDirections.actionControlStdFragmentToViewMaterialsFragment(courseName, courseId)));
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.Btnactive.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.Btnactive.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}