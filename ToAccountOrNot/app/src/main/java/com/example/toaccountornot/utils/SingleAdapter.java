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

    public SingleAdapter(List<Single> singleList, Context context) {
        mContext = context;
        this.singleList = singleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView first;
        TextView second;
        TextView money;
        ImageView image_property;
        LinearLayout clickItem;
        Button button_lookmore;

        public ViewHolder(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.text_first);
            second = itemView.findViewById(R.id.text_second);
            money = itemView.findViewById(R.id.money);
            image_property = itemView.findViewById(R.id.property_image);
            clickItem = itemView.findViewById(R.id.click_item);
            button_lookmore = itemView.findViewById(R.id.button_lookmore);
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
        holder.first.setText(single.getFirst());
        holder.second.setText(single.getSecond());
        switch (single.getInorout())
        {
            case "in":
                holder.money.setText(df.format(single.getPrice()));
                break;
            case "out":
                holder.money.setText("-"+df.format(single.getPrice()));
                break;
        }
        imageSwitch(single.getFirst(), holder.image_property);
//        if(singleList.size()>3)
        initClickListener(holder,single);
    }

    @Override
    public int getItemCount() {
        return singleList.size();
    }

    void imageSwitch(String first, ImageView imageProperty) {
        switch (first) // 没写完 待补充
        {
            case"餐饮":
                imageProperty.setImageResource(R.drawable.food);
                break;
            case "购物":
                imageProperty.setImageResource(R.drawable.shopping);
                break;
            case "日用":
                imageProperty.setImageResource(R.drawable.daily);
                break;
            default:
                break;
        }
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
