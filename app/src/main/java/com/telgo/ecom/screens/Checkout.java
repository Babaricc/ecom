package com.telgo.ecom.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.razorpay.PaymentResultListener;
import com.telgo.ecom.CustomDialog;
import com.telgo.ecom.HomeScreen;
import com.telgo.ecom.MainActivity;
import com.telgo.ecom.OTP_Screen;
import com.telgo.ecom.R;
import com.telgo.ecom.adapters.CartAdapter;
import com.telgo.ecom.custom.Constants;
import com.telgo.ecom.databinding.ActivityCheckoutBinding;
import com.telgo.ecom.models.Product;
import com.telgo.ecom.session.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Checkout extends AppCompatActivity implements PaymentResultListener {

    static ActivityCheckoutBinding binding;

    List<Product> productList;
    CartAdapter cartAdapter;

    static double total;
    UserSession userSession;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Checkout Order");

        userSession = new UserSession();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        cart = TinyCartHelper.getCart();

        productList = new ArrayList<>();

        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();

            product.setQuantity(quantity);
            productList.add(product);
        }


        cartAdapter = new CartAdapter(this, productList, true);
        binding.productsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.productsRecyclerview.setAdapter(cartAdapter);


        binding.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();

                JSONObject dataObj = new JSONObject();
                JSONArray products = new JSONArray();
                for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
                    Product product = (Product) item.getKey();
                    products.put(gson.toJson(product));
                }

                try {
                    dataObj.put("cust_id", "434325");
                    dataObj.put("order_date", LocalDateTime.now());
                    dataObj.put("delivery_date", LocalDateTime.now().plusDays(5));
                    dataObj.put("amount", binding.totalAmount.getText().toString());
                    dataObj.put("sales_tax", Constants.SALES_TAX);
                    dataObj.put("shipping_address", binding.address2.getText().toString());
                    dataObj.put("status", true);
                    dataObj.put("transaction_id", 2523523);
                    dataObj.put("products", products);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.d("obj!", "2: " + dataObj);
                Log.d("obj!", "3: " + dataObj.toString());

                return;


//                double amount = Math.round(Double.parseDouble(binding.totalAmount.getText().toString()) * 100);
//                com.razorpay.Checkout checkout = new com.razorpay.Checkout();
//                checkout.setKeyID("rzp_test_adbF9UpkM034uy");
//                checkout.setImage(R.mipmap.ic_launcher);
//
//
//                JSONObject object = new JSONObject();
//                try {
//                    object.put("name", userSession.getName().isEmpty() ? "User Name" : userSession.getName());
//                    object.put("description", "Shopping Payment");
//                    object.put("theme.color", "");
//                    object.put("currency", "PKR");
//                    object.put("amount", amount);
//                    object.put("prefill.contact", "03403961930");
//                    object.put("prefill.email", "husainbabarkh@gmail.com");
//                    checkout.open(Checkout.this, object);
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }

            }
        });

        if (total > 0) {
            binding.subTotalAmount.setText("PKR " + total);
            double tax = (total * 7.5) / 100;
            double totalAmount = total + tax;
            binding.totalAmount.setText("" + totalAmount);
        }

    }

    public static void updateTotal(double d) {
        total = d;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onPaymentSuccess(String transaction_id) {
        Log.d("obj!", "1: " + 3434);


//        try {
//            RequestQueue queue = Volley.newRequestQueue(Checkout.this);
//
//            JSONObject dataObj = new JSONObject();
//            JSONObject products;
//            products = (JSONObject) cart.getItemNames();
//
//
//            dataObj.put("cust_id","434325");
//            dataObj.put("order_date",LocalDateTime.now());
//            dataObj.put("delivery_date", LocalDateTime.now().plusDays(5));
//            dataObj.put("amount",binding.totalAmount.getText().toString());
//            dataObj.put("sales_tax",Constants.SALES_TAX);
//            dataObj.put("shipping_address",binding.address2);
//            dataObj.put("status",true);
//            dataObj.put("transaction_id",transaction_id);
//            dataObj.put("products",products);
//
//            Log.d("obj!", "2: " + dataObj);
//            Log.d("obj!", "3: " + dataObj.toString());
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.ORDRES_URL +"/" +transaction_id, dataObj, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    CustomDialog dialog = new CustomDialog(Checkout.this, "Payment Transaction", "Transaction successfully completed", R.raw.success, false);
//                    dialog.show();
//                    dialog.yeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("obj!!", "5: "+ error);
//                    System.out.println("------------------------");
//                    error.printStackTrace();
//                }
//            });
//
//            queue.add(jsonObjectRequest);
//
//        } catch (JSONException e) {
//            Log.d("obj!", "4: " + e);
//            CustomDialog dialog = new CustomDialog(Checkout.this, "Payment Transaction", "Transaction successfully completed", R.raw.success, false);
//            dialog.show();
//            dialog.yeBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//        }


    }

    @Override
    public void onPaymentError(int i, String s) {
        CustomDialog dialog = new CustomDialog(Checkout.this, "Payment Transaction", s, R.raw.success, false);
        dialog.show();
        dialog.yeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}