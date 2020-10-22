package com.example.toaccountornot.ui.chart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;

import java.util.List;

public class incomeAdapter extends RecyclerView.Adapter<incomeAdapter.ViewHolder> {
    private List<income> incomes;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView incomeImage;
        TextView incomeName;

        public ViewHolder(View view) {
            super(view);
            incomeImage = (ImageView) view.findViewById(R.id.income_image);
            incomeName = (TextView) view.findViewById(R.id.income_name);
        }
    }

    public incomeAdapter(List<income> incomeList) {
        incomes = incomeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        income income = incomes.get(position);
        holder.incomeImage.setImageResource(income.getImageId());
        holder.incomeName.setText(income.getName());
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }


    }
