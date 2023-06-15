package com.telgo.ecom.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.telgo.ecom.adapters.CategoryAdapter;
import com.telgo.ecom.adapters.CartAdapter;
import com.telgo.ecom.adapters.ProductAdapter;
import com.telgo.ecom.custom.Constants;
import com.telgo.ecom.databinding.FragmentHomeBinding;
import com.telgo.ecom.models.Category;
import com.telgo.ecom.models.Product;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "ecom!!";
    private FragmentHomeBinding binding;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    ProductAdapter productAdapter;
    List<Product> productList;

    List<CarouselItem> sliderList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        categoryList = new ArrayList<>();
        sliderList = new ArrayList<>();
//        Category category1 = new Category(1, 1, "Men Fashion", "https://cdn-icons-png.flaticon.com/512/2681/2681760.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");
//        Category category2 = new Category(1, 1, "Women Fashion", "https://cdn-icons-png.flaticon.com/512/8863/8863863.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");
//        Category category3 = new Category(1, 1, "Beauty", "https://cdn-icons-png.flaticon.com/512/1940/1940922.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");
//        Category category4 = new Category(1, 1, "Health Care", "https://cdn-icons-png.flaticon.com/512/2382/2382461.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");
//        Category category5 = new Category(1, 1, "Baby Care", "https://cdn-icons-png.flaticon.com/512/6205/6205025.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");
//        Category category6 = new Category(1, 1, "Home Appliances", "https://cdn-icons-png.flaticon.com/512/3659/3659933.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");
//        Category category7 = new Category(1, 1, "Sports", "https://cdn-icons-png.flaticon.com/512/4163/4163684.png", "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available.", "#ebc921");

//        categoryList.add(category1);
//        categoryList.add(category2);
//        categoryList.add(category3);
//        categoryList.add(category4);
//        categoryList.add(category5);
//        categoryList.add(category6);
//        categoryList.add(category7);


        List<String> colors = new ArrayList<>();
        colors.add("#456674");
        colors.add("#45he55");
        colors.add("#d345e");

        productList = new ArrayList<>();

//        Product product1 = new Product(11, 44, 270, 177.0, 3.9, 9.0, "POLO T-SHIRT MAROON & BLACK", "", "https://cdn.shopify.com/s/files/1/0580/7970/7297/products/DSC03600.jpg?v=1663174831", colors);
//        Product product2 = new Product(12, 45, 370, 277.0, 6.0, 8.0, "BOYS SANDAL 40-104 - BEIGE", "", "https://cdn.shopify.com/s/files/1/0102/7755/2164/files/DSC09769_f8b5f8d4-6342-4743-9030-9fc94d4444bc_540x.jpg?v=1684499543", colors);
//        Product product3 = new Product(14, 46, 470, 1599, 7.4, 8.8, "Girls Pumps PP-5 - BEIGE", "", "https://cdn.shopify.com/s/files/1/0102/7755/2164/products/z645651901_3_540x.jpg?v=1675337852", colors);
//        Product product4 = new Product(15, 47, 570, 230.00, 7.5, 6.7, "Mothercare Baby Shampoo - Tear Free", "", "https://cdn.shopify.com/s/files/1/0560/8053/1620/files/Baby-Shampoo---Tear-Free-60ml_481144f0-ba9a-4833-bf52-b969832f1d7c_grande.jpg?v=1683874346", colors);
//        Product product5 = new Product(16, 48, 670, 79.13, 8.0, 8.0, "Entamizole 250Mg/320Mg Suspension 90Ml", "", "https://www.dvago.pk/_next/image?url=https%3A%2F%2Fdvago-assets.s3.ap-southeast-1.amazonaws.com%2FProductsImages%2F04214_2.JPG&w=1920&q=75", colors);
//        Product product6 = new Product(17, 49, 770, 977.0, 7.7, 5.9, "Disprin 300Mg Dispersible Tablets 100S (Pack Size 10 X 10S)", "", "https://www.dvago.pk/_next/image?url=https%3A%2F%2Fdvago-assets.s3.ap-southeast-1.amazonaws.com%2FProductsImages%2F07332.jpg&w=1920&q=75", colors);
        productAdapter = new ProductAdapter(getActivity(), productList);
        GridLayoutManager productsGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        binding.productRecyclerview.setLayoutManager(productsGridLayoutManager);
        binding.productRecyclerview.setAdapter(productAdapter);

        fetchSliders();
        fetchCategories();
        fetchProducts();


        return root;
    }


    private void fetchProducts() {

        Gson gson = new Gson();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.PRODUCTS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d(TAG, "onErrorResponse: " + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Product product = gson.fromJson(String.valueOf(object), Product.class);
                        productList.add(product);
                    }

                    productAdapter = new ProductAdapter(getActivity(), productList);
                    GridLayoutManager productsGridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    binding.productRecyclerview.setLayoutManager(productsGridLayoutManager);
                    binding.productRecyclerview.setAdapter(productAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);

            }
        });
        queue.add(jsonObjectRequest);
    }

    private void fetchCategories() {

        Gson gson = new Gson();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.CATEGORIES_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d(TAG, "onErrorResponse: " + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Category category = gson.fromJson(String.valueOf(object), Category.class);
                        categoryList.add(category);
                    }

                    categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
                    GridLayoutManager categoriesGridLayoutManager = new GridLayoutManager(getActivity(), 3);
                    binding.categoriesRecyclerview.setLayoutManager(categoriesGridLayoutManager);
                    binding.categoriesRecyclerview.setAdapter(categoryAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);

            }
        });
        queue.add(jsonObjectRequest);
    }

    private void fetchSliders() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.SLIDERS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.d(TAG, "onErrorResponse: " + jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        sliderList.add(new CarouselItem(object.getString("image")));
                    }

                    binding.carousel.setData(sliderList);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);

            }
        });
        queue.add(jsonObjectRequest);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}