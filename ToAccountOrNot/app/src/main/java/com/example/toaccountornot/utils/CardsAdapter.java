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
        Log.d("card",""+card.getType());
        //   holder.headImage.setImageResource(card.getImage());
        chooseimage(holder,card.getType());
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

    void chooseimage(ViewHolder holder,String type){
        if(type!=null){
            switch (type){
                case "微信":
                    holder.headImage.setImageResource(R.drawable.wechat);
                    break;
                case "支付宝":
                    holder.headImage.setImageResource(R.drawable.alipay);
                    break;
                case "现金":
                    holder.headImage.setImageResource(R.drawable.cash);
                    break;
                case "储蓄卡":
                    holder.headImage.setImageResource(R.drawable.bankcard);
                    break;
                case "信用卡":
                    holder.headImage.setImageResource(R.drawable.creditcard);
                    break;
                case "自定义":
                    holder.headImage.setImageResource(R.drawable.customize);
                    break;
                default:
                    holder.headImage.setImageResource(R.drawable.customize);
                    break;
            }
        }
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