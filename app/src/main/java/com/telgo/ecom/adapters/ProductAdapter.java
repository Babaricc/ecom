package com.telgo.ecom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.telgo.ecom.R;
import com.telgo.ecom.databinding.ProductViewBinding;
import com.telgo.ecom.models.Product;
import com.telgo.ecom.screens.ProductDetails;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productViewBinding.prodDetailTitle.setText(product.getTitle());
        holder.productViewBinding.productPrice.setText("PKR " + product.getPrice());
        Picasso.get().load(product.getImage()).into(holder.productViewBinding.prodDetailImage);
        holder.productViewBinding.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ProductViewBinding productViewBinding;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productViewBinding = ProductViewBinding.bind(itemView);
        }
    }
}
