package com.telgo.ecom.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.razorpay.PaymentResultListener;
import com.telgo.ecom.CustomDialog;
import com.telgo.ecom.MainActivity;
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

                try {

                    String amount = binding.totalAmount.getText().toString();

                    double payAmount = Math.round(Double.parseDouble(amount) * 100);

                    com.razorpay.Checkout checkout = new com.razorpay.Checkout();
                    checkout.setKeyID(Constants.PAYMENT_GATEWAY_KEY);
                    checkout.setImage(R.mipmap.ic_launcher);

                    JSONObject object = new JSONObject();


                    object.put("name", "Karachi Mart");
                    object.put("description", "Shopping Payment");
                    object.put("theme.color", "");
                    object.put("currency", "PKR");
                    object.put("amount", payAmount);
                    object.put("prefill.contact", "923403961930");
                    object.put("prefill.email", "hussainbabarkh@gmail.com");
                    checkout.open(Checkout.this, object);


                } catch (Exception ex) {
                    Log.d("exception!!", "onClick: " + ex);
                }
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
        RequestQueue queue = Volley.newRequestQueue(Checkout.this);

        try {
            JSONObject orderData = new JSONObject();
            orderData.put("user_id", "434343"); // WILL BE CHANGED
            orderData.put("order_date", LocalDateTime.now());
            orderData.put("delivery_date", LocalDateTime.now().plusDays(5));
            orderData.put("amount", binding.totalAmount.getText().toString());
            orderData.put("sales_tax", 7.5); //// WILL BE CHANGED binding.salesTaxAmount.getText()
            orderData.put("shipping_address", binding.address2.getText());
            orderData.put("status", true);
            orderData.put("transaction_id", transaction_id);

            JSONArray products = new JSONArray();

            for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
                Product product = (Product) item.getKey();
                JSONObject prodObj = new JSONObject();

                prodObj.put("id", product.getId());
                prodObj.put("quantity", product.getQuantity());
                prodObj.put("price", product.getPrice());
                prodObj.put("discount", product.getDiscount());

                products.put(prodObj);
            }

            orderData.put("products", products);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.ORDRES_URL_CREATE, orderData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if (response.getBoolean("status")) {
                            CustomDialog customDialog = customDialog("Payment Transaction", "Payment Transaction Completed + " + transaction_id, R.raw.success);
                            customDialog.show();
                            customDialog.yeBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    customDialog.dismiss();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    CustomDialog customDialog = customDialog("Payment Transaction", "Payment Transaction Failed " + error, R.raw.failure);
                    customDialog.show();
                    customDialog.yeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                        }
                    });
                }
            });

            queue.add(jsonObjectRequest);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onPaymentError(int i, String error) {
        CustomDialog customDialog = customDialog("Payment Transaction", "Payment Transaction Failed " + error, R.raw.failure);
        customDialog.show();
        customDialog.yeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

    }

    public CustomDialog customDialog(String title, String message, int image) {
        CustomDialog customDialog = new CustomDialog(Checkout.this, title, message, image, false);
        return customDialog;
    }

}