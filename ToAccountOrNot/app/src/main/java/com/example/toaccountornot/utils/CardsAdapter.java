package com.example.toaccountornot.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.CardDetailActivity;
import com.example.toaccountornot.DetailActivity;
import com.example.toaccountornot.CardsActivity;
import com.example.toaccountornot.R;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private Context mContext;
    private List<Cards> mcardlist;

    public CardsAdapter(Context context, List<Cards> cardlist) {
        Log.d("card","cardsadapter");
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
        LinearLayout clickitem;

        public ViewHolder(View view) {
            super(view);
            clickitem = view.findViewById(R.id.click_item);
            name = view.findViewById(R.id.account_card);
            remark = view.findViewById(R.id.account_remark);
            headImage = view.findViewById(R.id.account_image);
            card_return = view.findViewById(R.id.card_return);
            in = view.findViewById(R.id.account_in);
            out = view.findViewById(R.id.account_out);
            sur = view.findViewById(R.id.account_sur);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("card","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("card","onBindViewHolder");
        Cards card = mcardlist.get(position);
        holder.name.setText(card.getCard());
        holder.remark.setText(card.getRemark());
        Log.d("card",""+card.getImage());
        holder.headImage.setImageResource(card.getImage());
        holder.card_return.setImageResource(R.drawable.returnsignal);
        String income = "+" + card.getIncome();
        holder.in.setText(income);
        String outcome = "-" + card.getOutcome();
        holder.out.setText(outcome);
        String surplus = card.getSurplus() > 0 ? "+" + card.getSurplus() : "" + card.getSurplus();
        holder.sur.setText(surplus);
        initClickListener(holder, card);
    }

    @Override
    public int getItemCount() {
        return mcardlist.size();
    }

    void initClickListener(final CardsAdapter.ViewHolder holder, final Cards card) {
        holder.clickitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, card.getCard(), Toast.LENGTH_SHORT).show();
                Intent cardintent = new Intent();
                cardintent.setClass(mContext, CardDetailActivity.class);
                Bundle cardbundle = new Bundle();
                cardbundle.putString("name", card.getCard());
                cardintent.putExtras(cardbundle);
                mContext.startActivity(cardintent);
            }
        });
    }
}