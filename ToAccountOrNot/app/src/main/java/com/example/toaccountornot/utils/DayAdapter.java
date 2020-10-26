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

import org.litepal.LitePal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {

    private Context mContext;
    private List<Day> mDayList;

    public DayAdapter(List<Day> dayList, Context context){
        mContext = context;
        mDayList = dayList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView itemList;
        TextView day_outcome;
        TextView day_income;
        TextView date;

        private ViewHolder(View itemView) {
            super(itemView);
            itemList = itemView.findViewById(R.id.list_everyday);
            day_outcome = itemView.findViewById(R.id.day_outcome);
            day_income = itemView.findViewById(R.id.day_income);
            date = itemView.findViewById(R.id.title_date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<Single> singleList;
        SingleAdapter singleAdapter;
        DecimalFormat df = new DecimalFormat("#.##");
        Day day = mDayList.get(position);

        singleList = initSingleList(day.getDate());

        holder.day_outcome.setText(df.format(day.getOutcome_day()));
        holder.day_income.setText(df.format(day.getIncome_day()));
        holder.date.setText(String.valueOf(day.getDate()));
        holder.itemList.setLayoutManager(new LinearLayoutManager(mContext));
        singleAdapter = new SingleAdapter(singleList, mContext);
        holder.itemList.setAdapter(singleAdapter);
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    private List<Single> initSingleList(String date) {
        List<Single>singleList = new ArrayList<>();

        long id;
        String inorout;
        String first;
        String second;
        double price;
        String card;
        String member;

        List<Accounts> list = LitePal.where("date=?", date)
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

            singleList.add(new Single(id, inorout, first, second, price, date, card, member));
        }

        return singleList;
    }
}
