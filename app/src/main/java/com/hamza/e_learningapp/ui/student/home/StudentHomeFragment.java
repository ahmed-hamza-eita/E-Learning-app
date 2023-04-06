package com.hamza.e_learningapp.ui.student.home;

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
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.adapters.AdapterCourses;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.databinding.FragmentStudentHomeBinding;
import com.hamza.e_learningapp.ui.instructor.add_student.AddStudentViewModel;
import com.hamza.e_learningapp.utils.Helper;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StudentHomeFragment extends BaseFragment {

    private FragmentStudentHomeBinding binding;
    private StudentHomeViewModel studentHomeViewModel;
    private AddStudentViewModel addStudentViewModel;
    private AdapterCourses adapterCourses;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentHomeBinding.inflate(inflater, container, false);
        studentHomeViewModel = new ViewModelProvider(this).get(StudentHomeViewModel.class);
        addStudentViewModel = new ViewModelProvider(this).get(AddStudentViewModel.class);
        adapterCourses = new AdapterCourses();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        studentHomeViewModel.getCourses();
        observers();

    }

    private void observers() {
        loading(true);
        studentHomeViewModel.enrollLiveData.observe(getViewLifecycleOwner(), s -> {
            loading(false);
            if ("".equals(s)) {
                return;
            } else {
                addStudentViewModel.addStudent(Helper.removeDot(MySharedPrefrance.getUserEmail()), binding.enterCourseId.getText().toString(), s);
                binding.enterCourseId.setText("");
                loading(false);
                binding.enterCourseId.clearFocus();
                studentHomeViewModel.enrollLiveData.setValue("");
            }
        });
        studentHomeViewModel.coursesLiveData.observe(getViewLifecycleOwner(), modelCourses -> {
            loading(false);
            if (modelCourses.size() == 0) {
                showToast("No courses");
                return;

            } else {
                adapterCourses.setList(modelCourses);
                binding.recyclerViewStudentAddCourses.setAdapter(adapterCourses);
            }
        });
        studentHomeViewModel.errorLiveData.observe(getViewLifecycleOwner(), s -> {
            showToast(s);
            loading(false);
        });
    }

    private void actions() {
        binding.helper.setOnClickListener(view -> {
            navigate(StudentHomeFragmentDirections.actionStudentHomeFragmentToChatBotFragment());
        });
        binding.btnAddCourse.setOnClickListener(view -> {
            String id = binding.enterCourseId.getText().toString().trim();
            if (id.isEmpty()) {
                binding.enterCourseId.setError(getString(R.string.requried));
            } else {
                loading(true);
                studentHomeViewModel.enrollToCourse(id);

            }
        });
        binding.imgLogOut.setOnClickListener(view -> {
            MySharedPrefrance.clear();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(mFragmentActivity, MainActivity.class);
            startActivity(intent);
            mFragmentActivity.finishAffinity();
        });

        adapterCourses.setOnItemClick((name, id) -> {
            navigate(StudentHomeFragmentDirections.actionStudentHomeFragmentToControlStdFragment(name, id));
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