package com.example.toaccountornot.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private Context mContext;
    private List<Day> dayList;
    private List<Single> singles;
    private SingleAdapter singleAdapter;

    public DayAdapter(List<Day> objects, Context mcontext){
        mContext = mcontext;
        dayList = objects;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView itemList;
        TextView day_outcome;
        TextView day_income;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            itemList = itemView.findViewById(R.id.list_everyday);
            day_outcome = itemView.findViewById(R.id.day_outcome);
            day_income = itemView.findViewById(R.id.day_income);
            date = itemView.findViewById(R.id.title_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<Single> singleList;
        DecimalFormat df = new DecimalFormat("#.##");
        Day day = dayList.get(position);

        singleList = initSinglelist(day.getDate());
        singles = singleList;

        holder.day_outcome.setText(df.format(day.getOutcome_day()));
        holder.day_income.setText(df.format(day.getIncome_day()));
        holder.date.setText(String.valueOf(day.getDate()));
        holder.itemList.setLayoutManager(new LinearLayoutManager(mContext));
        singleAdapter = new SingleAdapter(singleList, mContext);
        holder.itemList.setAdapter(singleAdapter);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public List<Single> initSinglelist(String date) {
        List<Single>singleList = new ArrayList<>();
        String first = "";
        String inorout = "";
        String pay_method = "";
        String remark = "";
        long id = 0;
        double income = 0;
        double outcome = 0;

        List<Accounts> list = LitePal.where("date=?", date)
                .order("id desc")
                .find(Accounts.class);
        for (Accounts accounts : list) {
            inorout = accounts.getInorout();
            first = accounts.getFirst();
            pay_method = accounts.getPaymethod();
            remark = accounts.getSecond();
            id = accounts.getId();
            switch (inorout)
            {
                case "in":
                    income = accounts.getIncome();
                    outcome = 0;
                    break;
                case "out":
                    outcome = accounts.getOutcome();
                    income = 0;
                    break;
            }
            singleList.add(new Single(first, income, outcome, inorout, pay_method, remark, date, id));
        }

        return singleList;
    }
}
