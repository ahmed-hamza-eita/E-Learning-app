package com.hamza.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.databinding.ItemGradesBinding;
import com.hamza.e_learningapp.models.ModelMembers;
import com.hamza.e_learningapp.utils.Helper;

import java.util.ArrayList;

public class AdapterGrades extends RecyclerView.Adapter<AdapterGrades.Holder> {
    ArrayList<ModelMembers> gradesList;

    public void setGradesList(ArrayList<ModelMembers> gradesList) {
        this.gradesList = gradesList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGradesBinding binding;
        binding = ItemGradesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.StdEmail.setText(Helper.putDot(gradesList.get(position).getStudentEmail()));
        holder.binding.StdGrade.setText(  gradesList.get(position).getQuizGrade()+" ");
        holder.binding.StdAttendance.setText(gradesList.get(position).getAttendanceGrade()+" ");
    }

    @Override
    public int getItemCount() {
        return gradesList == null ? 0 : gradesList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemGradesBinding binding;

        public Holder(@NonNull ItemGradesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
