package com.example.toaccountornot.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toaccountornot.R;

import java.util.List;

public class CardsAdapter extends ArrayAdapter {

    private int CardId;
    public CardsAdapter(Context context, int headImage, List<Cards> obj){
        super(context,headImage,obj);
        CardId = headImage;//这个是传入我们自己定义的界面
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Cards card = (Cards) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(CardId,parent,false);//这个是实例化一个我们自己写的界面Item
        TextView name = view.findViewById(R.id.account_card);
        TextView remark = view.findViewById(R.id.account_remark);
        ImageView headImage = view.findViewById(R.id.account_image);
        TextView in = view.findViewById(R.id.account_in);
        TextView out = view.findViewById(R.id.account_out);
        TextView sur = view.findViewById(R.id.account_sur);
        name.setText(card.getCard());
        remark.setText(card.getRemark());
        headImage.setImageResource(card.getCardid());
        in.setText(String.valueOf(card.getIncome()));
        out.setText(String.valueOf(card.getOutcome()));
        sur.setText(String.valueOf(card.getSurplus()));
        return view;
    }
}