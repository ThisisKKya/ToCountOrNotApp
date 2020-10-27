package com.example.toaccountornot.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.First;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends BaseCategoryFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        minorout = "in";
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initCategory() {
        categoryList = LitePal.where("inorout = ? or inorout = ?","in","all").find(First.class);
//        Category salary = new Category("工资", R.drawable.salary);
//        categoryList.add(salary);
//        Category parttime = new Category("兼职",R.drawable.parttime);
//        categoryList.add(parttime);
//        Category gift = new Category("礼金",R.drawable.gift);
//        categoryList.add(gift);
//        Category setting = new Category("自定义",R.drawable.setting);
//        categoryList.add(setting);
    }
}
