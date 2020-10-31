package com.example.toaccountornot.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;
import com.example.toaccountornot.ui.account.CategoryAdapter;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder>{
    private List<First> mBudgetList;
    private BudgetAdapter.MyViewClickListener myViewClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView BudgetImage;
        TextView BudgetName;
        TextView BudgetNum;
        View budgetView;
        LinearLayout clickLayout;
        public ViewHolder(View view) {
            super(view);
            budgetView = view;
            BudgetImage = view.findViewById(R.id.budget_image);
            BudgetName = view.findViewById(R.id.budget_name1);
            BudgetNum = view.findViewById(R.id.budget_num);
            clickLayout = view.findViewById(R.id.click_item2);
        }
    }

    public BudgetAdapter(List<First> budgetList) {
        mBudgetList = budgetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewClickListener != null) {
                    int postion = holder.getAdapterPosition();
                    First budget = mBudgetList.get(postion);
                    myViewClickListener.changeBudget(budget.getName());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        First budget = mBudgetList.get(position);
        holder.BudgetImage.setImageResource(budget.getImage());
        holder.BudgetName.setText(budget.getName());
        holder.BudgetNum.setText(""+budget.getCost()+"/"+budget.getBudget());
    }

    @Override
    public int getItemCount() {
        return mBudgetList.size();
    }
    public void setMyViewClickListener(BudgetAdapter.MyViewClickListener myViewClickListener) {
        this.myViewClickListener = myViewClickListener;
    }
    public interface MyViewClickListener{
        void changeBudget(String budgetName);
    }
}
