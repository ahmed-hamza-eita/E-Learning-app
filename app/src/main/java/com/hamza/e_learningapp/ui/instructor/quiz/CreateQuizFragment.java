package com.hamza.e_learningapp.ui.instructor.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.databinding.FragmentCreateCourseBinding;
import com.hamza.e_learningapp.databinding.FragmentCreateQuizBinding;
import com.hamza.e_learningapp.models.ModelQuiz;
import com.hamza.e_learningapp.ui.instructor.control.InstructorControlFragmentArgs;
import com.hamza.e_learningapp.ui.instructor.control.InstructorControlFragmentDirections;
import com.hamza.e_learningapp.ui.instructor.home.InstructorHomeFragment;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateQuizFragment extends BaseFragment {

    private FragmentCreateQuizBinding binding;
    private int rightAns = 0;
    private ArrayList<ModelQuiz> quizList;
    private String courseId,courseName;
    private CreateQuizViewModel createQuizViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizList = new ArrayList<>();
        courseId = CreateQuizFragmentArgs.fromBundle(getArguments()).getCourseId();
        courseName = CreateQuizFragmentArgs.fromBundle(getArguments()).getCourseName();
        createQuizViewModel = new ViewModelProvider(this).get(CreateQuizViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateQuizBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClicks();
        observers();
    }

    private void observers() {
        createQuizViewModel.msg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loading(false);
                showToast(s);
                NavHostFragment.findNavController(CreateQuizFragment.this).popBackStack();
            }
        });
    }

    private void onClicks() {
        binding.nextQuestion.setOnClickListener(view -> validation());
        checkingBox();
        binding.btnUploadQuiz.setOnClickListener(view -> {
            if (quizList.size() == 0) {
                showToast("You must add at least one question");
            } else {
                loading(true);
                createQuizViewModel.uploadQuiz(quizList, courseName);


            }
        });
    }


    private void validation() {
        String question = binding.enterQuestion.getText().toString().trim();
        String answer1 = binding.answer1.getText().toString().trim();
        String answer2 = binding.answer2.getText().toString().trim();
        String answer3 = binding.answer3.getText().toString().trim();
        String answer4 = binding.answer4.getText().toString().trim();
        if (question.equals("")) {
            binding.enterQuestion.setError("Question is required");
        } else if (answer1.equals("")) {
            binding.answer1.setError("Answer is required");
        } else if (answer2.equals("")) {
            binding.answer2.setError("Answer is required");
        } else if (answer3.equals("")) {
            binding.answer3.setError("Answer is required");
        } else if (answer4.equals("")) {
            binding.answer4.setError("Answer is required");
        } else if (rightAns == 0) {
            showToast("Please select correct answer");
        } else {
            showToast("Success");
            quizList.add(new ModelQuiz(question, answer1, answer2, answer3, answer4, rightAns));
            binding.enterQuestion.setText("");
            binding.answer1.setText("");
            binding.answer2.setText("");
            binding.answer3.setText("");
            binding.answer4.setText("");
        }

    }

    private void checkingBox() {
        binding.checkBoxOne.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                rightAns = 1;
                binding.checkBoxTwo.setChecked(false);
                binding.checkBoxThree.setChecked(false);
                binding.checkBoxFour.setChecked(false);
                binding.checkBoxOne.setClickable(false);
            }
        });

        binding.checkBoxTwo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                rightAns = 2;
                binding.checkBoxOne.setChecked(false);
                binding.checkBoxThree.setChecked(false);
                binding.checkBoxFour.setChecked(false);
                binding.checkBoxTwo.setClickable(false);
            }

        });
        binding.checkBoxThree.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                rightAns = 3;
                binding.checkBoxOne.setChecked(false);
                binding.checkBoxTwo.setChecked(false);
                binding.checkBoxFour.setChecked(false);
                binding.checkBoxThree.setClickable(false);

            }
        });
        binding.checkBoxFour.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                rightAns = 4;
                binding.checkBoxOne.setChecked(false);
                binding.checkBoxTwo.setChecked(false);
                binding.checkBoxThree.setChecked(false);
                binding.checkBoxFour.setClickable(false);
            }
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.btnUploadQuiz.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.btnUploadQuiz.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}