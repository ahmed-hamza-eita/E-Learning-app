package com.hamza.e_learningapp.ui.auth.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.databinding.FragmentSignupBinding;
import com.hamza.e_learningapp.models.ModelAuth;
import com.hamza.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignupFragment extends BaseFragment {


    private FragmentSignupBinding binding;
    private SignupViewModel signupViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        observers();
    }

    private void observers() {

        signupViewModel.liveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loading(false);
                if (Const.kEY_SUCCESS.equals(s)) {
                    if (Const.kEY_USER_TYPE == Const.kEY_INSTRUCTOR) {
                        navigate(SignupFragmentDirections.actionSignupFragmentToInstructorHomeFragment());
                    } else {
                        navigate(SignupFragmentDirections.actionSignupFragmentToStudentHomeFragment());
                    }
                } else {
                    showToast(s);
                }
            }
        });
    }

    private void actions() {
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        binding.backToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment());
            }
        });
    }

    private void validation() {
        String name = binding.edtName.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString();

        if (name.isEmpty()) {
            binding.edtName.setError(getString(R.string.requried));
        } else if (email.isEmpty()) {
            binding.edtEmail.setError(getString(R.string.requried));
        } else if (password.isEmpty()) {
            binding.edtPassword.setError(getString(R.string.requried));
        } else if (password.length() < 6) {
            binding.edtPassword.setError("Password must be at least 6 characters");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("invalid Email Address");
        } else {
            loading(true);
            signupViewModel.signup(new ModelAuth(name, email, Const.kEY_USER_TYPE), password);
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.btnSignUp.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.btnSignUp.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}