package com.example.toaccountornot.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder>{

    private Context mContext;
    private List<Single> singleList;
    List<LinearLayout>hide = new ArrayList<>();
    Button button;
    boolean isHide = true;

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
        LinearLayout single_all;
        Button button_lookmore;

        public ViewHolder(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.text_first);
            second = itemView.findViewById(R.id.text_second);
            money = itemView.findViewById(R.id.money);
            image_property = itemView.findViewById(R.id.property_image);
            clickItem = itemView.findViewById(R.id.click_item);
            single_all = itemView.findViewById(R.id.single_all);
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

    void imageSwitch(String first, ImageView imageProperty) {
        switch (first)
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
            case "学习":
                imageProperty.setImageResource(R.drawable.study);
                break;
            case "交通":
                imageProperty.setImageResource(R.drawable.transport);
                break;
            case "水果":
                imageProperty.setImageResource(R.drawable.fruit);
                break;
            case "零食":
                imageProperty.setImageResource(R.drawable.snacks);
                break;
            case "运动":
                imageProperty.setImageResource(R.drawable.sport);
                break;
            case "娱乐":
                imageProperty.setImageResource(R.drawable.entertainment);
                break;
            case "住房":
                imageProperty.setImageResource(R.drawable.house);
                break;
            case "聚会":
                imageProperty.setImageResource(R.drawable.dating);
                break;
            case "旅行":
                imageProperty.setImageResource(R.drawable.travel);
                break;
            case "医疗":
                imageProperty.setImageResource(R.drawable.doctor);
                break;
            case "宠物":
                imageProperty.setImageResource(R.drawable.pet);
                break;
            case "工资":
                imageProperty.setImageResource(R.drawable.salary);
                break;
            case "兼职":
                imageProperty.setImageResource(R.drawable.parttime);
                break;
            case "礼金":
                imageProperty.setImageResource(R.drawable.gift);
                break;
            case "转账":
                imageProperty.setImageResource(R.drawable.transfer);
                break;
            default:
                imageProperty.setImageResource(R.drawable.setting);
                break;
        }
    }

    void initClickListener(final ViewHolder holder, final Single single) {
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
                    button.setText("收起");
                    isHide = false;
                } else {
                    for (int j = 0; j < hide.size(); j++) {
                        setVisibility(false, hide.get(j));
                    }
                    button.setText("查看更多");
                    isHide = true;
                }
            }
        });
    }

    public void setVisibility(boolean isVisible,LinearLayout itemView){
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
