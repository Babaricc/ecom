package com.telgo.ecom;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.telgo.ecom.custom.Constants;
import com.telgo.ecom.session.UserSession;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserSession userSession = new UserSession(this);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    Intent intent;

                    userSession.clearSession();
                    intent = new Intent(MainActivity.this, HomeScreen.class);

//                    Log.d("session!!", "getContact: " + userSession.getContact());
//                    Log.d("session", "getEmail: " + userSession.getEmail());
//                    if (userSession.getUserStatus())
//                        intent = new Intent(MainActivity.this, ProfileScreen.class);
//                    else
////                        intent = new Intent(MainActivity.this, HomeScreen.class);
//                        intent = new Intent(MainActivity.this, IntroActi.class);
////
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.d("err!", "run: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        // start thread
        background.start();

    }
}
