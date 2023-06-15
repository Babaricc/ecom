package com.telgo.ecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentAddress extends AppCompatActivity {

    LocationManager mLocationManager;
    Geocoder geocoder;
    List<Address> addresses;

    TextView textView, lat, longt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_address);

        textView = findViewById(R.id.textView2);
        lat = findViewById(R.id.lat);
        longt = findViewById(R.id.longt);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CurrentAddress.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }
        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        Log.d("location!!", "Latitude 2!!:" + latitude);
        Log.d("location!!", " Longitude 2:" + longitude);


        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                Log.d("location!!", "Latitude!!:" + location.getLongitude());
                Log.d("location!!", " Longitude:" + location.getLongitude());


                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    Log.d("location!!", "onLocationChanged: " + addresses.get(0).getAddressLine(0));
                    textView.setText(addresses.get(0).getAddressLine(0).toString());
                    lat.setText(String.valueOf(latitude).toString());
                    longt.setText(String.valueOf(longitude).toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000,
                400, mLocationListener);


    }
}