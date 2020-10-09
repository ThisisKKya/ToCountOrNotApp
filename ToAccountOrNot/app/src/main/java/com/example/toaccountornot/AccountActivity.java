package com.example.toaccountornot;

import android.os.Bundle;

import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardHelper;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;

import com.example.toaccountornot.ui.account.account_tab_ui.SectionsPagerAdapter;

public class AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}