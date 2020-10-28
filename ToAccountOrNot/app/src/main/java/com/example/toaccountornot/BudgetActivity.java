package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaccountornot.utils.First;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import static java.security.AccessController.getContext;

public class BudgetActivity extends AppCompatActivity {

    ImageView editImage;
    ImageView return_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

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
                                                new XPopup.Builder(BudgetActivity.this).asInputConfirm("添加自定义类别", "请输入内容。",
                                                        new OnInputConfirmListener() {
                                                            @Override
                                                            public void onConfirm(String text) {

                                                            }
                                                        })
                                                        .show();
                                                break;
                                            case 1:

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

}
