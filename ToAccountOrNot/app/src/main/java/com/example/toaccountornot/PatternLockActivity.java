package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.toaccountornot.utils.PreferenceUtils;

import java.util.List;

public class PatternLockActivity extends AppCompatActivity {

    private PatternLockView mPatternLockView;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            //密码
            String password = PreferenceUtils.getGesturePassword(PatternLockActivity.this);
            String patternToMD5 = PatternLockUtils.patternToMD5(mPatternLockView, pattern);
            if (!TextUtils.isEmpty(patternToMD5)) {
                if (patternToMD5.equals(password)) {
                    //判断为正确
                    mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    Toast.makeText(PatternLockActivity.this, "您绘制的密码是：" + patternToMD5 + "\n" + "密码正确，开锁成功", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PatternLockActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(PatternLockActivity.this, "您绘制的密码是：" + patternToMD5 + "\n" + "密码错误，请重新绘制", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_pattern_lock_view);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }
}
