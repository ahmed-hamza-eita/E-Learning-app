package com.hamza.e_learningapp.ui.student.solve_quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.databinding.FragmentSolveQuizBinding;
import com.hamza.e_learningapp.models.ModelQuiz;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SolveQuizFragment extends BaseFragment {

    private FragmentSolveQuizBinding binding;
    private SolveQuizViewModel viewModel;
    private String courseId, quizId;
    private int rightAnswer = 0, position = 0, grade = 0;
    private ArrayList<ModelQuiz> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSolveQuizBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SolveQuizViewModel.class);
        courseId = SolveQuizFragmentArgs.fromBundle(getArguments()).getCourseId();
        quizId = SolveQuizFragmentArgs.fromBundle(getArguments()).getQuizId();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getQuiz(courseId, quizId);
        actions();
        observer();
    }


    private void actions() {
        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rightAnswer != 0) {
                    checkAns();
                    init();
                } else {
                    showToast("Please answer the question");
                }
            }
        });

        binding.anwser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 1;
                binding.anwser1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
                binding.anwser2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
            }
        });
        binding.anwser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 2;
                binding.anwser1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
                binding.anwser3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
            }
        });
        binding.anwser3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 3;
                binding.anwser1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
                binding.anwser4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
            }
        });
        binding.anwser4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 4;
                binding.anwser1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
                binding.anwser4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
            }
        });

    }


    private void checkAns() {
        if (list.get(position).getRightAnswer() == rightAnswer) {
            grade++;
        }
        if (position == (list.size() - 1)) {
            NavHostFragment.findNavController(SolveQuizFragment.this).popBackStack();
        } else {
            position++;
        }
        resetAnst();

    }

    private void resetAnst() {
        rightAnswer = 0;
        binding.anwser1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
        binding.anwser2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
        binding.anwser3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
        binding.anwser4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitee));
    }


    private void init() {
        if (position == (list.size() - 1)) {
            binding.btnNextQuestion.setText("Finish");
        }
        binding.txtQuestion.setText(list.get(position).getQuestion());
        binding.ans1.setText(list.get(position).getAnswer1());
        binding.ans2.setText(list.get(position).getAnswer2());
        binding.ans3.setText(list.get(position).getAnswer3());
        binding.ans4.setText(list.get(position).getAnswer4());
    }

    private void observer() {
        viewModel.getQuizLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ModelQuiz>>() {
            @Override
            public void onChanged(ArrayList<ModelQuiz> modelQuizs) {
                list = modelQuizs;
                init();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.uploadResult(courseId, quizId, grade);
        showToast("Quiz solved");
        binding = null;
    }
}