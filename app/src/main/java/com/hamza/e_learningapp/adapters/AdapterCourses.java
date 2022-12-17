package com.hamza.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.databinding.ItemCoursesBinding;
import com.hamza.e_learningapp.models.ModelCourse;

import java.util.ArrayList;

public class AdapterCourses extends RecyclerView.Adapter<AdapterCourses.Holder> {

    private ArrayList<ModelCourse> list;

    public void setList(ArrayList<ModelCourse> list) {
        this.list = list;
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCoursesBinding binding;
        binding = ItemCoursesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.courseName.setText(list.get(position).getCourseName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemCoursesBinding binding;

        public Holder(@NonNull ItemCoursesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (onItemClick != null) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick.onClick(list.get(getLayoutPosition()).getCourseName()
                                , list.get(getLayoutPosition()).getCourseId());
                    }
                });
            }
        }
    }

    public interface OnItemClick {
        void onClick(String name, String id);
    }
}
