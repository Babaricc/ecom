package com.telgo.ecom.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.telgo.ecom.adapters.OrdersAdapter;
import com.telgo.ecom.adapters.ProductAdapter;
import com.telgo.ecom.databinding.FragmentOrdersBinding;
import com.telgo.ecom.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {
    private FragmentOrdersBinding binding;

    OrdersAdapter ordersAdapter;
    List<Order> orderList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        orderList = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(845675);
        order1.setOrderDate("23-06-2023");
        order1.setStatus("Delivered");

        Order order2 = new Order();
        order2.setId(7869536);
        order2.setOrderDate("27-06-2023");
        order2.setStatus("Processing");

        Order order3 = new Order();
        order3.setId(85435646);
        order3.setOrderDate("05-06-2023");
        order3.setStatus("Delivered");

        Order order4 = new Order();
        order4.setId(43545);
        order4.setOrderDate("13-06-2023");
        order4.setStatus("Canceled");

        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);


        ordersAdapter = new OrdersAdapter(getActivity(), orderList);
        binding.ordersRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.ordersRecyclerview.setAdapter(ordersAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}