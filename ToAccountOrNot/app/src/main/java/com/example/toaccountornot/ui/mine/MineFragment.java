package com.example.toaccountornot.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.toaccountornot.AboutActivity;
import com.example.toaccountornot.CardsActivity;
import com.example.toaccountornot.ChangePwActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.login.LoginActivity;
import com.example.toaccountornot.utils.Accounts;

import org.litepal.LitePal;

import java.util.List;

public class MineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        //ImageView imageView = view.findViewById(R.id.icon);
        LinearLayout change = view.findViewById(R.id.buttonChange);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePwActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout card = view.findViewById(R.id.card);
        TextView name = view.findViewById(R.id.name);
        SharedPreferences sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        name.setText(sp.getString("userId","爱记不记"));
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CardsActivity.class);
                startActivity(intent);
            }

        });
        LinearLayout about = view.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout quit = view.findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                SharedPreferences sp = getActivity().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
            }
        });
        return view;
    }
}