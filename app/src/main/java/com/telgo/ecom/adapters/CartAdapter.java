package com.telgo.ecom.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.squareup.picasso.Picasso;
import com.telgo.ecom.R;
import com.telgo.ecom.databinding.ItemCartBinding;
import com.telgo.ecom.models.Product;
import com.telgo.ecom.screens.CartScreen;
import com.telgo.ecom.screens.Checkout;
import com.telgo.ecom.screens.ProductDetails;

import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    List<Product> productList;
    Cart cart;

    CartViewHolder viewHolder;
    boolean checkout = false;

    public CartAdapter(Context context, List<Product> productList, boolean checkout) {
        this.context = context;
        this.productList = productList;
        this.checkout = checkout;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {

        viewHolder = holder;

        Product product = productList.get(position);

        holder.itemCartBinding.itemCartTitle.setText(product.getTitle());
        Picasso.get().load(product.getImage()).into(holder.itemCartBinding.itemCartImage);
        holder.itemCartBinding.itemCount.setText("" + product.getQuantity());
        holder.itemCartBinding.itemCartPrice.setText("Price: " + product.getPrice() + " PKR");
        product.setTotal(product.getQuantity() * product.getPrice());
        holder.itemCartBinding.itemCartTotalAmount.setText("" + product.getTotal());

        double price = product.getPrice() * product.getQuantity();
        holder.itemCartBinding.itemCartTotalAmount.setText("" + price);


        if (checkout) {
            holder.itemCartBinding.counterLayout.setVisibility(View.GONE);
            holder.itemCartBinding.deleteItem.setVisibility(View.GONE);
            holder.itemCartBinding.itemCartTotalAmount.setText("Items:" + product.getQuantity());

            CardView cardView = holder.itemCartBinding.itemCartProd;
            ViewGroup.LayoutParams params = cardView.getLayoutParams();
            params.height = 150;
            cardView.setLayoutParams(params);
        }

        updateTotal();

        holder.itemCartBinding.itemCartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });

        holder.itemCartBinding.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDismiss(product);
                cart.removeItem(product);
                updateTotal();
                notifyDataSetChanged();
                ProductDetails.updateBadge();
            }
        });


        holder.itemCartBinding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity();
                quantity++;
                product.setQuantity(quantity);
                holder.itemCartBinding.itemCount.setText("" + quantity);
                cart.updateItem(product, quantity);
                notifyItemChanged(productList.indexOf(product));
                updateTotal();

            }
        });

        holder.itemCartBinding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    product.setQuantity(quantity);
                    holder.itemCartBinding.itemCount.setText("" + quantity);
                    cart.updateItem(product, quantity);
                    notifyItemChanged(productList.indexOf(product));
                    updateTotal();

                }
            }
        });
    }
    public void onItemDismiss(Product product) {
        int index = productList.indexOf(product);
        {
            productList.remove(product);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }
    public void updateTotal() {
        double totalAmount = 0;
        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            totalAmount += product.getTotal();
        }
        CartScreen.updateTotal(totalAmount);
        Checkout.updateTotal(totalAmount);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ItemCartBinding itemCartBinding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCartBinding = ItemCartBinding.bind(itemView);
        }
    }
}
