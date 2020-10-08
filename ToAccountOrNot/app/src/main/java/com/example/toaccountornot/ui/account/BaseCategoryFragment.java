package com.example.toaccountornot.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toaccountornot.R;

import java.util.ArrayList;
import java.util.List;

public class BaseCategoryFragment extends Fragment {
    private List<Category> categoryList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basecategory,container,false);
        initCategory();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initCategory() {
        for (int i = 0; i < 10; i++) {
            Category dinner = new Category("Dinner",R.drawable.dinner);
            categoryList.add(dinner);
            Category lunch = new Category("lunch",R.drawable.lunch);
            categoryList.add(lunch);
        }
    }
}
