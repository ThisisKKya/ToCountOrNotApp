package com.example.toaccountornot.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.DetailActivity;
import com.example.toaccountornot.R;

import java.text.DecimalFormat;
import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder>{

    private Context mContext;
    private List<Single> singleList;

    public SingleAdapter(List<Single>singleList, Context mcontext) {
        mContext = mcontext;
        this.singleList = singleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView first;
        TextView paymethod;
        TextView money;
        Button button_lookmore;
        ImageView image_property;
        LinearLayout clickItem;
        LinearLayout single_all;
        public ViewHolder(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.text_first);
            paymethod = itemView.findViewById(R.id.text_paymethod);
            money = itemView.findViewById(R.id.money);
            image_property = itemView.findViewById(R.id.propertyimage);
            clickItem = itemView.findViewById(R.id.click_item);
            button_lookmore = itemView.findViewById(R.id.button_lookmore);
            single_all = itemView.findViewById(R.id.itemsingle_all);

        }
    }

    @Override
    public SingleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single,parent,false);
        final SingleAdapter.ViewHolder holder = new SingleAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#.##");
        Single single = singleList.get(position);
        switch (single.getInorout())
        {
            case "in":
                holder.money.setText(df.format(single.getIncome()));
                break;
            case "out":
                holder.money.setText("-"+df.format(single.getOutcome()));
                break;
        }
        imageSwitch(single.getFirst(), holder.image_property);
        holder.first.setText(single.getFirst());
        holder.paymethod.setText(String.valueOf(single.getPay_method()));
//        if(singleList.size()>3)
        initClickListener(holder,single);
    }

    @Override
    public int getItemCount() {
        return singleList.size();
    }

    void imageSwitch(String property, ImageView imageProperty) {
        // 待补充
    }

    void initClickListener(final ViewHolder holder, final Single single) {
        holder.clickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                // 待补充
            }
        });
        holder.button_lookmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 待补充
            }
        });
    }
}
