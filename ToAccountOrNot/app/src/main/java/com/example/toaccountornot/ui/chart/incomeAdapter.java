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

    /*public incomeAdapter(List<income> incomes) {
        this.incomes = incomes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        income income = incomes.get(position);
        //holder.textViewNumber.setText(String.valueOf(position));
        holder.textViewName.setText(income.getName());
        holder.textViewAge.setText(String.valueOf(income.getPrice()));
    }


    public int getItemCount() {
        return incomes.size();
    }

    /**
     * 创建ViewHolder类，用来缓存item中的子控件，避免不必要的findViewById
     */
    /*public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewName, textViewAge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //textViewNumber = itemView.findViewById(R.id.texeView_number);
            textViewName = itemView.findViewById(R.id.income_name);
            textViewAge = itemView.findViewById(R.id.income_image);
        }
    }*/

    }
