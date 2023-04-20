package com.hamza.e_learningapp.ui.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.data.MySharedPrefrance;
import com.hamza.e_learningapp.utils.Const;


public class SplashFragment extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> {
            actions();

        }, 1000);
    }

    private void actions() {
        if (!MySharedPrefrance.getBoolean(Const.kEY_IS_SIGNED_IN)) {
            navigate(SplashFragmentDirections.actionSplashFragmentToChooseTypeFragment());
        } else if (MySharedPrefrance.getUserType().equals(Const.kEY_INSTRUCTOR)) {
            navigate(SplashFragmentDirections.actionSplashFragmentToInstructorHomeFragment());
        } else {
            navigate(SplashFragmentDirections.actionSplashFragmentToStudentHomeFragment());
        }
    }
}