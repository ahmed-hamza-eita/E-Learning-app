package com.hamza.e_learningapp.ui.type;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.hamza.e_learningapp.BaseFragment;
import com.hamza.e_learningapp.R;
import com.hamza.e_learningapp.databinding.FragmentChooseTypeBinding;
import com.hamza.e_learningapp.utils.Const;


public class ChooseTypeFragment extends BaseFragment {

    private FragmentChooseTypeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChooseTypeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Glide.with(getContext()).load(R.drawable.splash2).into(binding.imagesplash);

        binding.checkStudent.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.checkDoctor.setChecked(false);
                Const.kEY_USER_TYPE = Const.kEY_STUDENT;
            } else {
                Const.kEY_USER_TYPE = "";
            }
        });
        binding.checkDoctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.checkStudent.setEnabled(false);
                    Const.kEY_USER_TYPE = Const.kEY_INSTRUCTOR;
                } else {
                    Const.kEY_USER_TYPE = "";
                }
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Const.kEY_USER_TYPE.equals("")) {
                    showToast("Please select a type...");
                } else {
                    navigate(ChooseTypeFragmentDirections.actionChooseTypeFragmentToLoginFragment());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}