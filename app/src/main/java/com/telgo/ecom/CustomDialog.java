package com.telgo.ecom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;

import com.airbnb.lottie.LottieAnimationView;
import com.telgo.ecom.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    Activity activity;
    public Button yeBtn;
    String title, message;
    boolean btnFlag;
    int rawRes;

    public CustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    public CustomDialog(Activity activity, String title, String message, int rawRes, boolean btnFlag) {
        this(activity);
        this.title = title;
        this.message = message;
        this.rawRes = rawRes;
        this.btnFlag = btnFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ((TextView) findViewById(R.id.dialog_title)).setText(title);
        ((TextView) findViewById(R.id.dialog_description)).setText(message);
        ((LottieAnimationView) findViewById(R.id.dialog_image)).setAnimation(rawRes);

        yeBtn = findViewById(R.id.dialog_btn);
        yeBtn.setOnClickListener(this);

        if (btnFlag){
            yeBtn.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
