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
import com.telgo.ecom.databinding.ItemOrderBinding;
import com.telgo.ecom.models.Order;
import com.telgo.ecom.models.Product;
import com.telgo.ecom.screens.CartScreen;
import com.telgo.ecom.screens.Checkout;
import com.telgo.ecom.screens.OrderDetails;
import com.telgo.ecom.screens.ProductDetails;

import java.util.List;
import java.util.Map;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    Context context;
    List<Order> orderList;
    Cart cart;
    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Order order = orderList.get(position);

        holder.orderBinding.orderItemId.setText("Order Id:" + order.getId());
        holder.orderBinding.orderItemDate.setText("Order Date:" + order.getOrderDate());
        holder.orderBinding.orderItemStatus.setText("" + order.getStatus());

        holder.orderBinding.orderItemDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetails.class);
                intent.putExtra("order", order);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        ItemOrderBinding orderBinding;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderBinding = ItemOrderBinding.bind(itemView);
        }
    }
}
