package com.telgo.ecom;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_Screen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editText;
    TextView editTextError;
    Button optBtn, send_otp_btn;
    ProgressBar progressBar;
    String code;
    private String verificationId;

    AlertDialog.Builder errorAlertDialog;

    EditText edit_otp1, edit_otp2, edit_otp3, edit_otp4, edit_otp5, edit_otp6;

    CustomDialog customDialog;


    View otpSection;

    String mobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        setTitle("Register Yourself");
        mAuth = FirebaseAuth.getInstance();

//        FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);

        editTextError = findViewById(R.id.textViewError);
        editText = findViewById(R.id.editText);
        editText.setText("+923133971166");
        send_otp_btn = findViewById(R.id.send_otp_btn);
        optBtn = findViewById(R.id.btnVerify);

        edit_otp1 = findViewById(R.id.otpB1);
        edit_otp2 = findViewById(R.id.otpB2);
        edit_otp3 = findViewById(R.id.otpB3);
        edit_otp4 = findViewById(R.id.otpB4);
        edit_otp5 = findViewById(R.id.otpB5);
        edit_otp6 = findViewById(R.id.otpB6);

        edit_otp6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace
                    edit_otp5.requestFocus();
                }
                return false;
            }
        });
        progressBar = findViewById(R.id.loading_otp_progress);
        progressBar.setVisibility(View.GONE);
        errorAlertDialog = new AlertDialog.Builder(this);

        otpSection = findViewById(R.id.verify_otp_section);

        send_otp_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(editText.getText().toString())) {
                    editTextError.setVisibility(View.VISIBLE);
                    return;
                }

                editTextError.setVisibility(View.INVISIBLE);
                mobileNumber = editText.getText().toString();

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(mobileNumber)            // Phone number to verify
                        .setTimeout(5L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(OTP_Screen.this)             // Activity (for callback binding)
                        .setCallbacks(mCallBack)     // OnVerificationStateChangedCallbacks
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }

            private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationId = s;
                    progressBar.setVisibility(View.INVISIBLE);
                    send_otp_btn.setEnabled(false);
                    editText.setEnabled(false);
                    otpSection.setVisibility(View.VISIBLE);
                    edit_otp1.requestFocus();
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    code = phoneAuthCredential.getSmsCode();

                    Log.d("codes!!", "code" + code);
                    Log.d("codes!!", "verificationId" + verificationId);


                    progressBar.setVisibility(View.INVISIBLE);
                    send_otp_btn.setEnabled(false);
                    editText.setEnabled(false);
                    Toast.makeText(OTP_Screen.this, "sadfdasfdsfs", Toast.LENGTH_LONG).show();
//
//                    if (code != null) {
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//                        signInWithCredential(credential);
//                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    errorAlertDialog.setTitle("Error: Unable to send OTP");
                    customDialog = new CustomDialog(OTP_Screen.this, "OTP Verification", e.getMessage(), R.raw.failure, false);
                    customDialog.show();
                    e.printStackTrace();
                }


            };

        });

//        optBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        edit_otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null || !String.valueOf(s).isEmpty()) {
                    edit_otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null || !String.valueOf(s).isEmpty()) {
                    edit_otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null || !String.valueOf(s).isEmpty()) {
                    edit_otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null || !String.valueOf(s).isEmpty()) {
                    edit_otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null || !String.valueOf(s).isEmpty()) {
                    edit_otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        optBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userCode = edit_otp1.getText().toString() + edit_otp2.getText().toString() + edit_otp3.getText().toString() + edit_otp4.getText().toString() + edit_otp5.getText().toString() + edit_otp6.getText().toString();
//        userCode = 456788
                customDialog = new CustomDialog(OTP_Screen.this, "OTP Verification", "Verify OTP.....", R.raw.upload, true);
                customDialog.show();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, userCode);
                signInWithCredential(credential);

                Log.e("verify!", "userCode: " + userCode);
                Log.e("verify!", "verificationId: " + code);
//                if (userCode.equals(code)) {
//                    customDialog = new CustomDialog(OTP_Screen.this, "OTP Verification", "OTP verification Successful!", R.raw.success);
//                } else {
//                    customDialog = new CustomDialog(OTP_Screen.this, "OTP Verification", "OTP verification Failed!, Check your code or retry again.", R.raw.success);
//                }
//
//                customDialog.show();
//
//                Log.d("verify!", "userCode: " + userCode);
//                Log.d("verify!", "verificationId: " + verificationId);
            }
        });


        ((TextView) findViewById(R.id.continue_as_guest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTP_Screen.this, HomeScreen.class));
                finish();
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("verify!!", "task: " + task.isSuccessful());
                if (task.isSuccessful()) {
                    customDialog.dismiss();
                    Intent i = new Intent(OTP_Screen.this, ProfileScreen.class);
                    i.putExtra("mobileNumber", mobileNumber);
                    startActivity(i);
                    finish();
                } else {
                    customDialog.dismiss();
                    Toast.makeText(OTP_Screen.this, "Invalid OTP, Please enter again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}