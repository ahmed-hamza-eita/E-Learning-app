package com.hamza.e_learningapp.ui.instructor.add_student;

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
import com.hamza.e_learningapp.adapters.AdapterAddMembers;
import com.hamza.e_learningapp.databinding.FragmentAddStudentBinding;
import com.hamza.e_learningapp.models.ModelMembers;
import com.hamza.e_learningapp.utils.Const;
import com.hamza.e_learningapp.utils.Helper;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddStudentFragment extends BaseFragment {
    private FragmentAddStudentBinding binding;
    private AddStudentViewModel addStudentViewModel;
    private String name;
    private String id;
    private AdapterAddMembers adapterAddMembers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapterAddMembers = new AdapterAddMembers();
        binding = FragmentAddStudentBinding.inflate(inflater, container, false);
        addStudentViewModel = new ViewModelProvider(this).get(AddStudentViewModel.class);
        name = AddStudentFragmentArgs.fromBundle(getArguments()).getCourseName();
        id = AddStudentFragmentArgs.fromBundle(getArguments()).getCourseId();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        observers();
        addStudentViewModel.getStudents(id);
    }

    private void observers() {
        addStudentViewModel.errorLiveData.observe(getViewLifecycleOwner(), s -> {
            loading(false);
            if (Const.kEY_SUCCESS.equals(s)) {
                showToast("Success");
                binding.enterStdEmail.setText(null);
            } else {
                showToast(s);
            }
        });
        addStudentViewModel.membersLiveData.observe(getViewLifecycleOwner(), modelMembers -> {
            adapterAddMembers.setList(modelMembers);

            binding.recyclerStudents.setAdapter(adapterAddMembers);

        });
    }

    private void actions() {
        binding.addStudent.setOnClickListener(view -> validation());
        adapterAddMembers.setDelete((String email, String id) -> {
            addStudentViewModel.deleteStudent(id, email);
            showToast("Deleted");
        });
    }

    private void validation() {
        String email = binding.enterStdEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.enterStdEmail.setError(getString(R.string.requried));
        } else {
            loading(true);
            addStudentViewModel.addStudent(Helper.removeDot(email), id, name);
        }
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