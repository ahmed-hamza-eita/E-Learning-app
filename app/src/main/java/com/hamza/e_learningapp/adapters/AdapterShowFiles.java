package com.hamza.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.databinding.ItemCoursesBinding;
import com.hamza.e_learningapp.databinding.ItemViewMaterialsBinding;
import com.hamza.e_learningapp.models.ModelPDF;

import java.util.ArrayList;
import java.util.List;

public class AdapterShowFiles extends RecyclerView.Adapter<AdapterShowFiles.Holder> {
    private List<String> fileList;

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewMaterialsBinding binding;
        binding = ItemViewMaterialsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.courseName.setText("Material " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return fileList == null ? 0 : fileList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemViewMaterialsBinding binding;

        public Holder(ItemViewMaterialsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (onClick != null) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onItemClick(fileList.get(getAdapterPosition()));
                    }
                });
            }
        }
    }

    public interface OnClick {
        void onItemClick(String name);
    }
}
