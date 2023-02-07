package com.hamza.e_learningapp.ui.instructor.attendance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.databinding.FragmentAttendBinding;
import com.hamza.e_learningapp.utils.Helper;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AttendFragment extends BaseFragment {
    private FragmentAttendBinding binding;
    private String courseId, courseName;
    private int attendanceCode;
    private AttendViewModel attendViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = AttendFragmentArgs.fromBundle(requireArguments()).getCourseId();
        courseName = AttendFragmentArgs.fromBundle(requireArguments()).getCourseName();
        attendViewModel = new ViewModelProvider(this).get(AttendViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAttendBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actions();
        observers();
    }

    private void observers() {
        attendViewModel.errorLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
                loading(false);
            }
        });
    }

    private void actions() {
        binding.btnGenerateCode.setOnClickListener(view -> {
            attendanceCode = Helper.generateCode();
            binding.txtCode.setText(attendanceCode + "");
            binding.BtnConfirm.setEnabled(true);
        });
        binding.BtnConfirm.setOnClickListener(view -> {
            attendViewModel.attend(courseId, attendanceCode);
            loading(true);
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.BtnConfirm.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.BtnConfirm.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}