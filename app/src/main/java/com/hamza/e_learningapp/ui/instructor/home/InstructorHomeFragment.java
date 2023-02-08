package com.hamza.e_learningapp.ui.instructor.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.MainActivity;
import com.hamza.e_learningapp.adapters.AdapterCourses;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.databinding.FragmentInstructorHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InstructorHomeFragment extends BaseFragment {

    private FragmentInstructorHomeBinding binding;
    private AdapterCourses adapterCourses;
    private InstructorHomeViewModel instructorHomeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstructorHomeBinding.inflate(inflater, container, false);
        instructorHomeViewModel = new ViewModelProvider(this).get(InstructorHomeViewModel.class);
        adapterCourses = new AdapterCourses();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        instructorHomeViewModel.getCourses();
        observers();
    }

    private void observers() {
        loading(true);

        instructorHomeViewModel.courseListLiveData.observe(getViewLifecycleOwner(), modelCourses -> {

            if (modelCourses.size() != 0) {
                loading(false);
                adapterCourses.setList(modelCourses);
                binding.recyclerInstructorCourse.setAdapter(adapterCourses);

            } else if(modelCourses.size()==0){
                loading(false);
                showToast("No Courses");
            }
        });
        instructorHomeViewModel.errorCLiveData.observe(getViewLifecycleOwner(), s -> showToast(s));
    }

    private void actions() {
        binding.addCourse.setOnClickListener(view ->
                navigate(InstructorHomeFragmentDirections.actionInstructorHomeFragmentToCreateCourseFragment()));
        adapterCourses.setOnItemClick((name, id) ->
                navigate(InstructorHomeFragmentDirections.actionInstructorHomeFragmentToInstructorControlFragment(name,id)));

        binding.imgLogOut.setOnClickListener(view -> {

            MySharedPrefrance.clear();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(mFragmentActivity, MainActivity.class);
            startActivity(intent);
            mFragmentActivity.finishAffinity();
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