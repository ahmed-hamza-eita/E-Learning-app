package com.hamza.e_learningapp.ui.instructor.control;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.databinding.FragmentInstructorControlBinding;
import com.hamza.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InstructorControlFragment extends BaseFragment {


    private FragmentInstructorControlBinding binding;
    private String name;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstructorControlBinding.inflate(inflater, container, false);
        name = InstructorControlFragmentArgs.fromBundle(getArguments()).getCourseName();
        id = InstructorControlFragmentArgs.fromBundle(getArguments()).getCourseId();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        actions();
    }

    private void actions() {
        binding.copyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                showToast("Copied");
            }
        });
        binding.imgAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(InstructorControlFragmentDirections.actionInstructorControlFragmentToAddStudentFragment(name, id));
            }
        });
        binding.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(InstructorControlFragmentDirections.actionInstructorControlFragmentToChatFragment(name, id));
            }
        });
    }

    private void init() {
        String courseName = InstructorControlFragmentArgs.fromBundle(getArguments()).getCourseName();
        String courseId = InstructorControlFragmentArgs.fromBundle(getArguments()).getCourseId();
        binding.courseName.setText(courseName);
        binding.courseId.setText(courseId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}