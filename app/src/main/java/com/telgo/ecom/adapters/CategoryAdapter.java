package com.telgo.ecom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telgo.ecom.R;
import com.telgo.ecom.databinding.CategoryLayoutBinding;
import com.telgo.ecom.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryLayoutBinding.catTitle.setText(category.getTitle());
        Picasso.get().load(category.getImage()).into(holder.categoryLayoutBinding.catImage);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        CategoryLayoutBinding categoryLayoutBinding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryLayoutBinding = CategoryLayoutBinding.bind(itemView);
        }
    }
}
