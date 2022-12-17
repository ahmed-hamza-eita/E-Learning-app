package com.hamza.e_learningapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.e_learningapp.databinding.ItemMembersBinding;
import com.hamza.e_learningapp.models.ModelMembers;
import com.hamza.e_learningapp.utils.Helper;

import java.util.ArrayList;

public class AdapterAddMembers extends RecyclerView.Adapter<AdapterAddMembers.Holder> {

    private ArrayList<ModelMembers> list;

    public void setList(ArrayList<ModelMembers> list) {
        this.list = list;
    }

    private Delete delete;

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMembersBinding binding;
        binding = ItemMembersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.textEmail.setText(Helper.putDot(list.get(position).getStudentEmail()));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemMembersBinding binding;


        public Holder(@NonNull ItemMembersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btnDelete.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            if (delete != null) {
                                delete.onItemDelete(list.get(getLayoutPosition()).getCourseId(),
                                        list.get(getLayoutPosition()).getStudentEmail());
                            }
                        }
                    });
        }

    }

    public interface Delete {
        void onItemDelete(String id, String email);
    }


}
