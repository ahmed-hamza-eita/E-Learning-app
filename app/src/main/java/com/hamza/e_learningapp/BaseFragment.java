package com.hamza.e_learningapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import io.github.muddz.styleabletoast.StyleableToast;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mView;
    protected FragmentActivity mFragmentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        mFragmentActivity = requireActivity();
    }

    protected void navigate(NavDirections navDirections) {
        Navigation.findNavController(mView).navigate(navDirections);
    }

    protected void showToast(String message) {
        StyleableToast.makeText(mFragmentActivity, message, R.style.toastStyle).show();
    }

}
