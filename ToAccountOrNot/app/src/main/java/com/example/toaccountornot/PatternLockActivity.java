package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class PatternLockActivity extends AppCompatActivity {

    private TextView mTextTitle;
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

        mTextTitle = (TextView) findViewById(R.id.profile_name);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

        if (mSetPattern) {
            mTextTitle.setText("请设置密码");
        } else {
            String pattern = getPatternFromSharedPreferences();
            if (pattern.equals("")) {
                mTextTitle.setText("请设置密码");
                mSetPattern = true;
            }
        }
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
            mTextTitle.setText("请再次输入密码");
        } else {
            if (patternToString.equals(mFirstPattern)) {
                writePatternToSharedPreferences(pattern);
                Intent intent = new Intent(PatternLockActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            } else {
                shake();
                Toast.makeText(PatternLockActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                mTextTitle.setText("请重新设置密码");
                mFirstPattern = "";
            }
        }
        //2s后清除图案
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPatternLockView.clearPattern();
            }
        },2000);
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
            mTextTitle.setText("密码错误，请重新输入");
        }
        //2s后清除图案
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPatternLockView.clearPattern();
            }
        },2000);
    }
}
