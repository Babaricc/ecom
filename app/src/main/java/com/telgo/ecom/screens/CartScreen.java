package com.telgo.ecom.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.telgo.ecom.adapters.CartAdapter;
import com.telgo.ecom.databinding.CartScreenBBinding;
import com.telgo.ecom.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CartScreen extends AppCompatActivity {

    List<Product> productList;
    CartAdapter cartAdapter;

    Cart cart;
    static CartScreenBBinding binding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CartScreenBBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("Your Cart");
        cart = TinyCartHelper.getCart();

        productList = new ArrayList<>();

        String colors = "";
//        Product product1 = new Product(11, 44, 270,5, 177.0, 3.9, 9.0, "POLO T-SHIRT MAROON & BLACK", "", "https://cdn.shopify.com/s/files/1/0580/7970/7297/products/DSC03600.jpg?v=1663174831", colors);
//        Product product2 = new Product(12, 45, 370,5, 277.0, 6.0, 8.0, "BOYS SANDAL 40-104 - BEIGE", "", "https://cdn.shopify.com/s/files/1/0102/7755/2164/files/DSC09769_f8b5f8d4-6342-4743-9030-9fc94d4444bc_540x.jpg?v=1684499543", colors);
//        Product product3 = new Product(14, 46, 470,5, 1599, 7.4, 8.8, "Girls Pumps PP-5 - BEIGE", "", "https://cdn.shopify.com/s/files/1/0102/7755/2164/products/z645651901_3_540x.jpg?v=1675337852", colors);
//        Product product4 = new Product(15, 47, 570,5, 230.00, 7.5, 6.7, "Mothercare Baby Shampoo - Tear Free", "", "https://cdn.shopify.com/s/files/1/0560/8053/1620/files/Baby-Shampoo---Tear-Free-60ml_481144f0-ba9a-4833-bf52-b969832f1d7c_grande.jpg?v=1683874346", colors);
//        Product product5 = new Product(16, 48, 670,5, 79.13, 8.0, 8.0, "Entamizole 250Mg/320Mg Suspension 90Ml", "", "https://www.dvago.pk/_next/image?url=https%3A%2F%2Fdvago-assets.s3.ap-southeast-1.amazonaws.com%2FProductsImages%2F04214_2.JPG&w=1920&q=75", colors);

//        productList.add(product1);
//        productList.add(product2);
//        productList.add(product3);
//        productList.add(product4);
//        productList.add(product5);




        for (Map.Entry<Item, Integer> item :  cart.getAllItemsWithQty().entrySet()){
            Product product = (Product) item.getKey();
            int quantity  = item.getValue();

            product.setQuantity(quantity);
            productList.add(product);
        }


        cartAdapter = new CartAdapter(this, productList, false);
        binding.cartRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerview.setAdapter(cartAdapter);


        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartScreen.this, Checkout.class));
            }
        });

    }


    public static void updateTotal(double total){
        binding.totalAmount.setText("PKR "  + total);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

}