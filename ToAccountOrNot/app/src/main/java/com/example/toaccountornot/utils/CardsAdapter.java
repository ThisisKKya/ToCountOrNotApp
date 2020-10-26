package com.example.toaccountornot.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{

    private Context mContext;
    private List<Cards> mcardlist;

    public CardsAdapter(Context context, List<Cards> cardlist){
        mContext = context;
        mcardlist = cardlist;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView remark;
        ImageView headImage;
        ImageView card_return;
        TextView in;
        TextView out;
        TextView sur;

        public ViewHolder(View view) {
            super(view);
            TextView name = view.findViewById(R.id.account_card);
            TextView remark = view.findViewById(R.id.account_remark);
            ImageView headImage = view.findViewById(R.id.account_image);
            ImageView card_return = view.findViewById(R.id.card_return);
            TextView in = view.findViewById(R.id.account_in);
            TextView out = view.findViewById(R.id.account_out);
            TextView sur = view.findViewById(R.id.account_sur);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext().inflate(R.layout.cards_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cards card = mcardlist.get(position);
        holder.name.setText(card.getCard());
        holder.remark.setText(card.getRemark());
        holder.headImage.setImageResource(card.getCardid());
        holder.card_return.setImageResource(R.drawable.returnsignal);
        String income = "+"+ card.getIncome();
        holder.in.setText(income);
        String outcome  = "-"+card.getOutcome();
        holder.out.setText(outcome);
        String surplus = card.getSurplus() >0 ? "+"+card.getSurplus():""+card.getSurplus();
        holder.sur.setText(surplus);
    }
    @Override
    public int getItemCount() {
        return mcardlist.size();
    }
}