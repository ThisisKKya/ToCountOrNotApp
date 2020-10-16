package com.example.toaccountornot.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.toaccountornot.CardsActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.utils.Accounts;

import org.litepal.LitePal;

import java.util.List;

public class MineFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        Button button = view.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Accounts> list = LitePal.findAll(Accounts.class);
                for (Accounts account : list) {
                    Toast.makeText(getContext(), "" + account.getPrice(), Toast.LENGTH_SHORT).show();
                }
                }

        });
        Button analyze_account = view.findViewById(R.id.analyze_account);
        analyze_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CardsActivity.class);
                startActivity(intent);
            }

        });
        return view;
    }
}