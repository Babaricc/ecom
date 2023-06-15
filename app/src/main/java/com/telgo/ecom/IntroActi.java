package com.telgo.ecom;

import static com.telgo.ecom.R.raw.e_commerce_22380;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class IntroActi extends AppIntro {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(SampleSlide.newInstance(R.layout.test));
        addSlide(SampleSlide.newInstance(R.layout.slide1));
        addSlide(SampleSlide.newInstance(R.layout.slide2));
        addSlide(SampleSlide.newInstance(R.layout.slide3));
        showSkipButton(true);

//        setBarColor(Color.parseColor("#B8B8B8"));
//        setSeparatorColor(Color.parseColor("#B8B8B8"));


        setColorDoneText(Color.parseColor("#FC9A09"));
        setNextArrowColor(Color.parseColor("#FC9A09"));
        setColorSkipButton(Color.parseColor("#FC9A09"));

        setIndicatorColor(Color.parseColor("#FC9A09"), Color.parseColor("#FC9A09"));


    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Intent i=new Intent(IntroActi.this,OTP_Screen.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        Intent i=new Intent(IntroActi.this,OTP_Screen.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when slide is changed
    }



}
