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

public class CreateCardAdapter extends ArrayAdapter {

    private int ImageId;
    public CreateCardAdapter(Context context, int Image, List<CreateCard> obj){
        super(context,Image,obj);
        ImageId = Image;//这个是传入我们自己定义的界面
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CreateCard c_card = (CreateCard) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(ImageId,parent,false);//这个是实例化一个我们自己写的界面Item
        ImageView image = view.findViewById(R.id.createcard_image);
        TextView name = view.findViewById(R.id.createcard_name);
        ImageView signal = view.findViewById(R.id.createcard_signal);
        image.setImageResource(c_card.getImageId());
        name.setText(String.valueOf(c_card.getName()));
        signal.setImageResource(c_card.getSignalId());
        return view;
    }
}