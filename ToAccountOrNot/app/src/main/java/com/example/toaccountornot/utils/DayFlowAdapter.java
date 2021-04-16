package com.example.toaccountornot.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;

import java.text.DecimalFormat;
import java.util.List;

public class DayFlowAdapter extends RecyclerView.Adapter<DayFlowAdapter.ViewHolder> {

    private Context mContext;
    private List<DayFlow> mDayFlowList;

    public DayFlowAdapter(Context mContext, List<DayFlow> mDayFlowList) {
        this.mContext = mContext;
        this.mDayFlowList = mDayFlowList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvItem;
        TextView tvDayExpense;
        TextView tvDayIncome;
        TextView tvDate;

        public ViewHolder(@NonNull View view) {
            super(view);
            rvItem = view.findViewById(R.id.list_everyday);
            tvDayExpense = view.findViewById(R.id.day_outcome);
            tvDayIncome = view.findViewById(R.id.day_income);
            tvDate = view.findViewById(R.id.title_date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayFlow dayFlow = mDayFlowList.get(position);
        holder.tvDate.setText(dayFlow.getDate());
        DecimalFormat df = new DecimalFormat("#.##");
        holder.tvDayExpense.setText(df.format(dayFlow.getExpense()));
        holder.tvDayIncome.setText(df.format(dayFlow.getIncome()));
        List<Single> singleList = dayFlow.getList();
        holder.rvItem.setLayoutManager(new LinearLayoutManager(mContext));
        SingleAdapter singleAdapter = new SingleAdapter(singleList, mContext);
        holder.rvItem.setAdapter(singleAdapter);
    }

    @Override
    public int getItemCount() {
        return mDayFlowList.size();
    }
}
