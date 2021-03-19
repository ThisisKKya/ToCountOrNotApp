package com.example.toaccountornot.ui.account;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toaccountornot.CreateFirstCategoryActivity;
import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.First;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TranscomeFragment extends BaseCategoryFragment {
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        minorout = "trans";
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_basecategory,container,false);
        etInput = view.findViewById(R.id.etInput);
        tvSecond = view.findViewById(R.id.secondCategory);
        tvCard = view.findViewById(R.id.card);
        tvmember = view.findViewById(R.id.member);
        tvSecond.setText("转入账户:");
        tvCard.setText("转出账户:");
        tvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .asBottomList("转出账户", cardString.toArray(new String[cardString.size()]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        tvCard.setText("转出账户:"+text);
                                        mcard = text;
                                        //Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        tvmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .asBottomList("成员", memberString.toArray(new String[memberString.size()]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        tvmember.setText("成员:"+text);
                                        mmember = text;
                                        // Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initsecondstring();
                new XPopup.Builder(getContext())
                        .asBottomList("转入账户", cardString.toArray(new String[cardString.size()]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                            tvSecond.setText("转入账户:"+text);
                                            msecondCategory = text;
//                                            Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        keyboard_temp = view.findViewById(R.id.keyboard_temp);
        llKeborad = view.findViewById(R.id.llKeborad);
        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        initKey();
        initRecycler();

        return view;
    }

    @Override
    public void initCategory() {
        categoryList = LitePal.where("inorout = ? ","trans").find(First.class);
    }

}
