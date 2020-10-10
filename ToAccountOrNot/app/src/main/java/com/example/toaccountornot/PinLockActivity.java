package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.toaccountornot.utils.Utils;

public class PinLockActivity extends AppCompatActivity {

    private TextView mTextTitle;
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

    private static final String PREFERENCES = "com.example.toaccountornot";
    private static final String KEY_PIN = "pin";

    private boolean mSetPin = false;
    private String mFirstPin = "";

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (mSetPin) {
                setPin(pin);
            } else {
                checkPin(pin);
            }
        }

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin_lock);

        mTextTitle = (TextView) findViewById(R.id.profile_name);
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        if (mSetPin) {
            mTextTitle.setText("请设置密码");
        } else {
            String pin = getPinFromSharedPreferences();
            if (pin.equals("")) {
                mTextTitle.setText("请设置密码");
                mSetPin = true;
            }
        }
    }

    private void writePinToSharedPreferences(String pin) {
        SharedPreferences prefs = this.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_PIN, Utils.sha256(pin)).apply();
    }

    private String getPinFromSharedPreferences() {
        SharedPreferences prefs = this.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PIN, "");
    }

    private void shake() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(mPinLockView, "translationX",
                0, 25, -25, 25, -25, 15, -15, 6, -6, 0).setDuration(1000);
        objectAnimator.start();
    }

    private void setPin(String pin) {
        if (mFirstPin.equals("")) {
            mFirstPin = pin;
            mTextTitle.setText("请再次输入密码");
            mPinLockView.resetPinLockView();
        } else {
            if (pin.equals(mFirstPin)) {
                writePinToSharedPreferences(pin);
                Intent intent = new Intent(PinLockActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            } else {
                shake();
                Toast.makeText(PinLockActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                mTextTitle.setText("请重新设置密码");
                mPinLockView.resetPinLockView();
                mFirstPin = "";
            }
        }
    }

    private void checkPin(String pin) {
        if (Utils.sha256(pin).equals(getPinFromSharedPreferences())) {
            Intent intent = new Intent(PinLockActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        } else {
            shake();
            mTextTitle.setText("密码错误，请重新输入");
            mPinLockView.resetPinLockView();
        }
    }
}
