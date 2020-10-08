package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.toaccountornot.utils.PreferenceUtils;

import java.util.List;

public class SetPatternLockActivity extends AppCompatActivity {


    private TextView mTitleTv;
    private PatternLockView mPatternLockView;

    private boolean isFirst = true;
    private String mpassword;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {

            String patternToString = PatternLockUtils.patternToString(mPatternLockView, pattern);
            if (!TextUtils.isEmpty(patternToString)) {
                if (isFirst) {
                    mpassword = patternToString;
                    mTitleTv.setText("请再次输入密码");
                    isFirst = false;
                } else {
                    if (patternToString.equals(mpassword)) {
                        PreferenceUtils.setGesturePassword(SetPatternLockActivity.this, mpassword);
                        startActivity(new Intent(SetPatternLockActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(SetPatternLockActivity.this,"两次密码不一致，请重新设置",Toast.LENGTH_SHORT).show();
                        mpassword = "";
                        mTitleTv.setText("请设置密码");
                        isFirst = true;
                    }
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
        setContentView(R.layout.activity_set_lock);

        mTitleTv = (TextView) findViewById(R.id.profile_name);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }
}
