package com.hamza.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.databinding.ItemAvailableQuizBinding;
import com.hamza.e_learningapp.models.ModelQuiz;

import java.util.ArrayList;
import java.util.List;

public class AdapterQuizes extends RecyclerView.Adapter<AdapterQuizes.Holder> {
    private List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAvailableQuizBinding binding = ItemAvailableQuizBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
       holder.binding.txtQuizName.setText("Quiz " + list.get(position ));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemAvailableQuizBinding binding;

        public Holder(@NonNull ItemAvailableQuizBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (onItemClickListener != null) {
                binding.getRoot().setOnClickListener(view -> {
                    onItemClickListener.onClick(list.get(getAdapterPosition()));
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(String name);
    }
}
