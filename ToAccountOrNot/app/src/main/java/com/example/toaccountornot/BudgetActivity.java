package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaccountornot.utils.Budget;
import com.example.toaccountornot.utils.BudgetAdapter;
import com.example.toaccountornot.utils.First;
import com.example.toaccountornot.utils.TotalBudget;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class BudgetActivity extends AppCompatActivity {
    public List<First> budgetList = new ArrayList<>();
    ImageView editImage;
    ImageView return_bar;
    RecyclerView recyclerView;
    BudgetAdapter adapter;
    TextView tvTotalBudget;
    TextView tvCost;
    TextView tvRemain;
    TotalBudget totalBudget;
    double totalCost = 0;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initBudget();
        totalBudget = LitePal.findFirst(TotalBudget.class);
        tvTotalBudget.setText(""+totalBudget.getBudget());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        tvTotalBudget = findViewById(R.id.text_second);
        totalBudget = LitePal.findFirst(TotalBudget.class);
        tvTotalBudget.setText(""+totalBudget.getBudget());
        tvCost = findViewById(R.id.text_cost);
        tvRemain = findViewById(R.id.text_remain);
        recyclerView =  findViewById(R.id.subbudget_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        initBudget();
        editImage = findViewById(R.id.budget_edit);
        return_bar = findViewById(R.id.return_bar);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(BudgetActivity.this)
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"编辑总预算", "编辑分类预算"},
                                new int[]{},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        switch (position) {
                                            case 0:
                                                Intent intent1 = new Intent();
                                                intent1.putExtra("FirstOrSecond","budget");
                                                intent1.setClass(BudgetActivity.this,CreateFirstCategoryActivity.class);
                                                startActivity(intent1);
                                                break;
                                            case 1:
                                                Intent intent = new Intent();
                                                intent.setClass(BudgetActivity.this,CreateBudget.class);
                                                startActivity(intent);
                                                break;

                                        }
                                    }
                                })
                        .show();
            }
        });

        return_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initBudget() {
        budgetList.clear();
        totalCost = 0;
        budgetList = LitePal.where("budget > ? or thisMonthCost > ?","0","0").order("thisMonthCost desc").find(First.class);
        for (First budgettemp:budgetList) {
            totalCost += budgettemp.getCost();
        }
        tvCost.setText(""+totalCost);
        tvRemain.setText(""+(Double.valueOf(tvTotalBudget.getText().toString())-Double.valueOf(tvCost.getText().toString())));
        adapter = new BudgetAdapter(budgetList);
        recyclerView.setAdapter(adapter);
        adapter.setMyViewClickListener(new BudgetAdapter.MyViewClickListener() {
            @Override
            public void changeBudget(String budgetName) {
                Intent intent1 = new Intent();
                intent1.putExtra("BudgetName",budgetName);
                intent1.putExtra("FirstOrSecond","subbudget");
                intent1.setClass(BudgetActivity.this,CreateFirstCategoryActivity.class);
                startActivity(intent1);
            }
        });
    }

}
