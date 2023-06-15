package com.telgo.ecom.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.squareup.picasso.Picasso;
import com.telgo.ecom.R;
import com.telgo.ecom.databinding.ActivityProductDetailsBinding;
import com.telgo.ecom.models.Product;

import java.util.Objects;

public class ProductDetails extends AppCompatActivity {
    private static final String TAG = "prod!!";
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)

    ActivityProductDetailsBinding binding;
    static Cart cart;
    static TextView textCartItemCount;
    int mCartItemCount = 10;
    static View actionView;
    Product product;

    @SuppressLint({"NewApi", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        product = getIntent().getParcelableExtra("product", Product.class);
        cart = TinyCartHelper.getCart();
        getSupportActionBar().setTitle(product.getTitle());

        Picasso.get().load(product.getImage()).into(binding.prodDetailImage);
        binding.prodDetailTitle.setText(product.getTitle());
        binding.prodDetailRating.setText("Rating: " + product.getRating() + "/5");
        binding.prodDetailDescription.setText(product.getDescription());
        binding.prodDetailReviews.setText("Reviews: " + product.getReviews());
        binding.prodDetailSold.setText("" + product.getSold() + " Sold");



        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, CartScreen.class));
            }
        });
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.prodCartAdded.setVisibility(View.VISIBLE);
                binding.prodCartAdded.clearAnimation();


                Product checkObj = checkProduct(product.getId());
                if (checkObj != null) {
                    int quantity = checkObj.getQuantity();
                    quantity++;
                    checkObj.setQuantity(quantity);
                    cart.updateItem(checkObj, quantity);
                } else {
                    mCartItemCount++;
                    cart.addItem(product, 1);
                    updateBadge();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.prodCartAdded.setVisibility(View.GONE);
                        binding.prodCartAdded.clearAnimation();
                    }
                }, 2500);

            }


        });
    }

    boolean animFlag = true;

    public Product checkProduct(int id) {
        for (Item p : cart.getItemNames()) {
            Product product = (Product) p;
            if (product.getId() == id) return product;
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        updateBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart: {
                startActivity(new Intent(ProductDetails.this, CartScreen.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static void updateBadge() {
        int count = cart.getItemNames().size();
        if (count > 0) {
//            actionView.setVisibility(View.VISIBLE);
            actionView.findViewById(R.id.cart_badge).setVisibility(View.VISIBLE);
            textCartItemCount.setText("" + count);
        } else {
            actionView.findViewById(R.id.cart_badge).setVisibility(View.GONE);
//            actionView.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}

