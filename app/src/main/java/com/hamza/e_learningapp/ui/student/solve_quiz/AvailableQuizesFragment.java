package com.hamza.e_learningapp.ui.student.solve_quiz;

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
import com.hamza.e_learningapp.adapters.AdapterQuizes;
import com.hamza.e_learningapp.databinding.FragmentAvailableQuizesBinding;
import com.hamza.e_learningapp.models.ModelQuiz;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvailableQuizesFragment extends BaseFragment {
    private FragmentAvailableQuizesBinding binding;
    private SolveQuizViewModel viewModel;
    private String name, id, quizId;
    private AdapterQuizes adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvailableQuizesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SolveQuizViewModel.class);
        id = AvailableQuizesFragmentArgs.fromBundle(getArguments()).getCourseId();
        name = AvailableQuizesFragmentArgs.fromBundle(getArguments()).getCourseName();
        adapter = new AdapterQuizes();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading(true);
        viewModel.getAllQuizzes(id);
        actions();
        observer();
    }

    private void observer() {

        viewModel.errorLiveData.observe(getViewLifecycleOwner(), s -> {
            showToast(s);
            loading(false);

            binding.noQuizzes.setVisibility(View.VISIBLE);
            binding.noQuizzes.setAlpha(0.5f);


        });
       viewModel.allQuizLiveData.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
           @Override
           public void onChanged(List<String> strings) {
               adapter.setList(strings);
               binding.recyclerAvailableQuzes.setAdapter(adapter);
               loading(false);
           }
       });

        viewModel.checkIfUserAnsweredLiveData.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showToast("You have answered on this quiz");
            } else {
                navigate(AvailableQuizesFragmentDirections.actionAvailableQuizesFragmentToSolveQuizFragment(id, quizId));
            }
        });
    }

    private void actions() {
        adapter.setOnItemClickListener(name -> {
            loading(false);
            quizId = name;
            viewModel.checkIfUserAnswered(id, name);
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