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

public class OutcomeFragment extends BaseCategoryFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        minorout = "out";
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initCategory() {
        categoryList = LitePal.where("inorout = ? or inorout = ?","out","all").find(First.class);
    }

}
