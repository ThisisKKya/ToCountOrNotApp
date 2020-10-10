package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

/**
 * 图形密码
 */
public class PatternLockActivity extends AppCompatActivity {

    private TextView mTextTitle;
    private Button mButtonPin;
    private PatternLockView mPatternLockView;

    private static final String PREFERENCES = "com.example.toaccountornot";
    private static final String KEY_PATTERN = "pattern";

    private boolean mSetPattern;
    private String mFirstPattern = "";

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            if (mSetPattern) {
                setPattern(pattern);
            } else {
                checkPattern(pattern);
            }
        }

        @Override
        public void onCleared() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pattern_lock);

        mTextTitle = (TextView) findViewById(R.id.title);
        mButtonPin = (Button) findViewById(R.id.change_mode_button);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        if (mSetPattern) {
            mTextTitle.setText(getString(R.string.lock_set));
        } else {
            String pattern = getPatternFromSharedPreferences();
            if (pattern.equals("")) {
                mTextTitle.setText(getString(R.string.lock_set));
                mSetPattern = true;
            }
        }

        mButtonPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatternLockActivity.this, PinLockActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void writePatternToSharedPreferences(List<PatternLockView.Dot> pattern) {
        SharedPreferences prefs = this.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_PATTERN, PatternLockUtils.patternToSha1(mPatternLockView, pattern)).apply();
    }

    private String getPatternFromSharedPreferences() {
        SharedPreferences prefs = this.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PATTERN, "");
    }

    private void shake() {
        ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(mPatternLockView, "translationX",
                0, 25, -25, 25, -25, 15, -15, 6, -6, 0).setDuration(1000);
        objectAnimator.start();
    }

    private void setPattern(List<PatternLockView.Dot> pattern) {
        String patternToString = PatternLockUtils.patternToString(mPatternLockView, pattern);
        if (mFirstPattern.equals("")) {
            mFirstPattern = patternToString;
            mTextTitle.setText(getString(R.string.lock_second));
        } else {
            if (patternToString.equals(mFirstPattern)) {
                writePatternToSharedPreferences(pattern);
                Intent intent = new Intent(PatternLockActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            } else {
                shake();
                Toast.makeText(PatternLockActivity.this, getString(R.string.toast_not_same), Toast.LENGTH_SHORT).show();
                mTextTitle.setText(getString(R.string.lock_retry));
                mFirstPattern = "";
            }
        }
        //1.5s后清除图案
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPatternLockView.clearPattern();
            }
        },1500);
    }

    private void checkPattern(List<PatternLockView.Dot> pattern) {
        if (PatternLockUtils.patternToSha1(mPatternLockView, pattern).equals(getPatternFromSharedPreferences())) {
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
            Intent intent = new Intent(PatternLockActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        } else {
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            shake();
            mTextTitle.setText(getString(R.string.lock_wrong));
        }
        //1.5s后清除图案
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPatternLockView.clearPattern();
            }
        },1500);
    }
}
