package com.telgo.ecom.custom;
public class Constants {
//    public static String API_BASE_URL = "http://192.168.2.103:30004/ecomapi";
    public static String API_BASE_URL = "https://ecomapidb-production.up.railway.app/ecomapi";
    public static String CATEGORIES_URL = API_BASE_URL + "/categories";
    public static String SLIDERS_URL = API_BASE_URL + "/sliders";
    public static String PRODUCTS_URL = API_BASE_URL + "/products";
    public static String ORDRES_URL = API_BASE_URL + "/orders";
    public static Double SALES_TAX = 7.5;
    public static String[] PERMISSIONS = {android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_SMS, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.CAMERA};

}
