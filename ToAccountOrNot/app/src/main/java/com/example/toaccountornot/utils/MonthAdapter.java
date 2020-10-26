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

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private Context mContext;
    private List<Month> mMonthList;

    public MonthAdapter(List<Month> MonthList, Context context){
        mContext = context;
        mMonthList = MonthList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView itemList;
        TextView month_outcome;
        TextView month_income;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            itemList = itemView.findViewById(R.id.list_everyday);
            month_outcome = itemView.findViewById(R.id.day_outcome);
            month_income = itemView.findViewById(R.id.day_income);
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
        SingleAdapter singleAdapter;
        DecimalFormat df = new DecimalFormat("#.##");
        Month month = mMonthList.get(position);

        singleList = initSinglelist(month.getYear(),month.getMonth(),month.getCard());

        String date = month.getYear()+"-"+month.getMonth();
        holder.month_outcome.setText(df.format(month.getOutcome_month()));
        holder.month_income.setText(df.format(month.getIncome_month()));
        holder.date.setText(date);
        holder.itemList.setLayoutManager(new LinearLayoutManager(mContext));
        singleAdapter = new SingleAdapter(singleList, mContext);
        holder.itemList.setAdapter(singleAdapter);
    }

    @Override
    public int getItemCount() {
        return mMonthList.size();
    }

    public List<Single> initSinglelist(String year, String month, String card) {
        List<Single>singleList = new ArrayList<>();

        long id;
        String inorout;
        String first;
        String second;
        double price = 0;
        String member;
        String day;

        List<Accounts> list = LitePal.where("date_year=? and date_month=? and card=?", year, month, card)
                .order("id desc")
                .find(Accounts.class);
        for (Accounts accounts : list) {
            id = accounts.getId();
            inorout = accounts.getInorout();
            first = accounts.getFirst();
            second = accounts.getSecond();
            price = accounts.getPrice();
            card = accounts.getCard();
            member = accounts.getMember();
            day = accounts.getDate();

            singleList.add(new Single(id, inorout, first, second, price, day, card, member));
        }

        return singleList;
    }
}
