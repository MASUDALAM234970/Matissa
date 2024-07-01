package com.error.mantissa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.error.mantissa.R;
import com.error.mantissa.databinding.SampleCategoryItemBinding;
import com.error.mantissa.model.CategoryModel;


import java.util.ArrayList;

public class CategoryDepAdapter extends RecyclerView.Adapter<CategoryDepAdapter.CategoryViewHolder>
{  Context context;
    ArrayList<CategoryModel> categories;
    public interface CategoryClickListener{
        void onCategoryClicked(CategoryModel category);
    }

    CategoryClickListener categoryClickListener;

    public CategoryDepAdapter(Context context, ArrayList<CategoryModel> categories, CategoryClickListener categoryClickListener) {
        this.context = context;
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }

  /* public interface CategoryClickListener{
        void onCategoryClicked(CategoryModel category);
    }

    CategoryClickListener categoryClickListener;

    public CategoryDepAdapter(Context context, ArrayList<CategoryModel> categories) {
        this.context = context;
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }*/

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position)
    {
        CategoryModel category = categories.get(position);
        holder.binding.categoryText.setText(category.getCategoryName());
        holder.binding.categoryIcon.setImageResource(category.getCategoryImage());

        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));

        holder.itemView.setOnClickListener(c-> {
            categoryClickListener.onCategoryClicked(category);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        SampleCategoryItemBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SampleCategoryItemBinding.bind(itemView);

        }
    }
}
