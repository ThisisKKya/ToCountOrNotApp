package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.toaccountornot.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                String passwordStr = PreferenceUtils.getGesturePassword(SplashActivity.this);
                Intent intent;
//                if (passwordStr == "") {
//                    intent = new Intent(SplashActivity.this, SetPatternLockActivity.class);
//                } else {
//                    intent = new Intent(SplashActivity.this, PatternLockActivity.class);
//                }
                intent = new Intent(SplashActivity.this, PinLockActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
