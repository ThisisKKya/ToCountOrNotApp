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

import java.util.ArrayList;
import java.util.List;

public class OutcomeFragment extends BaseCategoryFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        minorout = "支出";
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initCategory() {
        Category food = new Category("餐饮", R.drawable.food);
        categoryList.add(food);
        Category shopping = new Category("购物",R.drawable.shopping);
        categoryList.add(shopping);
        Category daily = new Category("日用",R.drawable.daily);
        categoryList.add(daily);
        Category study = new Category("学习",R.drawable.study);
        categoryList.add(study);
        Category transport = new Category("交通",R.drawable.transport);
        categoryList.add(transport);
        Category fruit = new Category("水果",R.drawable.fruit);
        categoryList.add(fruit);
        Category snacks = new Category("零食",R.drawable.snacks);
        categoryList.add(snacks);
        Category sport = new Category("运动",R.drawable.sport);
        categoryList.add(sport);
        Category entertainment = new Category("娱乐",R.drawable.entertainment);
        categoryList.add(entertainment);
        Category house = new Category("住房",R.drawable.house);
        categoryList.add(house);
        Category dating = new Category("聚会",R.drawable.dating);
        categoryList.add(dating);
        Category travel = new Category("旅行",R.drawable.travel);
        categoryList.add(travel);
        Category doctor = new Category("医疗",R.drawable.doctor);
        categoryList.add(doctor);
        Category pet = new Category("宠物",R.drawable.pet);
        categoryList.add(pet);
        Category pet2 = new Category("宠物",R.drawable.pet);
        categoryList.add(pet);
        Category pet3 = new Category("宠物",R.drawable.pet);
        categoryList.add(pet);
        Category pet4 = new Category("宠物",R.drawable.pet);
        categoryList.add(pet);
        Category setting = new Category("自定义",R.drawable.setting);
        categoryList.add(setting);
    }

}
