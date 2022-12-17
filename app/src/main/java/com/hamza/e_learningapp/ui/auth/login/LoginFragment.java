package com.hamza.e_learningapp.ui.auth.login;

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
import com.hamza.e_learningapp.databinding.FragmentLoginBinding;
import com.hamza.e_learningapp.utils.Const;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {


    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actions();
        observer();
    }

    private void observer() {
        loginViewModel.loginLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loading(false);
                if (Const.kEY_SUCCESS.equals(s)) {
                    if (Const.kEY_USER_TYPE.equals(Const.kEY_INSTRUCTOR)) {
                        navigate(LoginFragmentDirections.actionLoginFragmentToInstructorHomeFragment());
                    } else {
                        navigate(LoginFragmentDirections.actionLoginFragmentToStudentHomeFragment());
                    }
                } else {
                    showToast(s);
                }
            }
        });
    }

    private void actions() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        binding.txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment());
            }
        });
        binding.backToType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(LoginFragmentDirections.actionLoginFragmentToChooseTypeFragment());
            }
        });
    }

    private void validation() {
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString();
        if (email.isEmpty()) {
            binding.edtEmail.setError(getString(R.string.requried));
        } else if (password.isEmpty()) {
            binding.edtPassword.setError(getString(R.string.requried));
        } else {
            loading(true);
            loginViewModel.login(email, password);
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.btnLogin.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}