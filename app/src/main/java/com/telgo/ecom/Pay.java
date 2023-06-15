package com.telgo.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Pay extends AppCompatActivity  implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Button b = findViewById(R.id.payBtn);

        Checkout.preload(getApplicationContext());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_test_adbF9UpkM034uy");
                    checkout.setImage(R.drawable.baseline_email_24);

                    try {
                        JSONObject options = new JSONObject();

                        options.put("name", "Babar Hussain");
                        options.put("description", "Reference No. #123456");
                        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//                        options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                        options.put("theme.color", "#3399cc");
                        options.put("currency", "PKR");
                        options.put("amount", "54456");//pass amount in currency subunits
                        options.put("prefill.email", "fsret@example.com");
                        options.put("prefill.contact","+923133971166");
                        JSONObject retryObj = new JSONObject();
                        retryObj.put("enabled", true);
                        retryObj.put("max_count", 4);
                        options.put("retry", retryObj);

                        checkout.open(Pay.this, options);

                    } catch(Exception e) {
                        Log.e("", "Error in starting Razorpay Checkout", e);
                    }

            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("code!!", "onPaymentSuccess: " + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("code!!", "onPaymentError: " + s);

    }
}