package com.example.toaccountornot.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.DetailActivity;
import com.example.toaccountornot.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder>{

    private Context mContext;
    private List<Single> singleList;
    private List<LinearLayout>hide = new ArrayList<>();
    private Button button;
    private boolean isHide = true;

    public SingleAdapter(List<Single> singleList, Context context) {
        mContext = context;
        this.singleList = singleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView first;
        TextView second;
        TextView money;
        TextView daytext;
        ImageView image_property;
        LinearLayout clickItem;
        LinearLayout single_all;
        Button button_lookmore;

        private ViewHolder(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.text_first);
            second = itemView.findViewById(R.id.text_second);
            money = itemView.findViewById(R.id.money);
            image_property = itemView.findViewById(R.id.property_image);
            clickItem = itemView.findViewById(R.id.click_item);
            single_all = itemView.findViewById(R.id.single_all);
            button_lookmore = itemView.findViewById(R.id.button_lookmore);
            daytext = itemView.findViewById(R.id.single_day);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single,parent,false);
        return new SingleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("#.##");
        Single single = singleList.get(position);
        holder.first.setText(single.getFirst());
        holder.second.setText(single.getSecond());
        //Log.d("test",single.getDate());
        String date_day = single.getDate().substring(single.getDate().length() -2);
        holder.daytext.setText(date_day);
        if(single.getShowday()==1)
            holder.daytext.setVisibility(View.VISIBLE);
        else
            holder.daytext.setVisibility(View.GONE);
        switch (single.getInorout())
        {
            case "in":
                holder.money.setText(df.format(single.getPrice()));
                break;
            case "out":
                holder.money.setText(df.format(-single.getPrice()));
                break;
            case "trans":
                holder.money.setText(df.format(single.getPrice()));
                break;
        }
        Utils.imageSwitch(single.getFirst(), holder.image_property);
        if(singleList.size()>3)
        {
            if(position>1&&position!=(singleList.size()-1)) {
                setVisibility(false,holder.single_all);
                hide.add(holder.single_all);
            }
            if(position==(singleList.size()-1)) {
                holder.button_lookmore.setVisibility(View.VISIBLE);
                button = holder.button_lookmore;
            }
        }
        initClickListener(holder,single);
    }

    @Override
    public int getItemCount() {
        return singleList.size();
    }

    private void initClickListener(final ViewHolder holder, final Single single) {
        holder.clickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", single.getId());
                bundle.putString("inorout", single.getInorout());
                bundle.putString("first", single.getFirst());
                bundle.putString("second", single.getSecond());
                bundle.putDouble("price", single.getPrice());
                bundle.putString("date", single.getDate());
                bundle.putString("card", single.getCard());
                bundle.putString("member", single.getMember());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.button_lookmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHide) {
                    for (int j = 0; j < hide.size(); j++) {
                        setVisibility(true, hide.get(j));
                    }
                    button.setText(R.string.btn_hide);
                    isHide = false;
                } else {
                    for (int j = 0; j < hide.size(); j++) {
                        setVisibility(false, hide.get(j));
                    }
                    button.setText(R.string.btn_more);
                    isHide = true;
                }
            }
        });
    }

    private void setVisibility(boolean isVisible,LinearLayout itemView){
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
        if (isVisible){
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        }else{
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }
}
