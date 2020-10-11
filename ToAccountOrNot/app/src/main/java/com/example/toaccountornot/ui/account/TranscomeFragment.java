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

public class TranscomeFragment extends BaseCategoryFragment {
    private List<Category> categoryList = new ArrayList<>();
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCategory();
       // onCreateView(null,null,null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basecategory,container,false);
        etNote = view.findViewById(R.id.etNote);
        etInput = view.findViewById(R.id.etInput);
        keyboard_temp = view.findViewById(R.id.keyboard_temp);
        llKeborad = view.findViewById(R.id.llKeborad);
        initKey();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        adapter.setMyViewClickListener(new CategoryAdapter.MyViewClickListener() {
            @Override
            public void callKeyboard(String fisrtCategory) {
                if (llKeborad.getVisibility() == View.GONE){
                    llKeborad.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private void initCategory() {
        Category transfer = new Category("转账",R.drawable.transfer);
        categoryList.add(transfer);
    }

}
