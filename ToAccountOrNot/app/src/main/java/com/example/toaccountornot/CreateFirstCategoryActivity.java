package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.toaccountornot.utils.Budget;
import com.example.toaccountornot.utils.First;
import com.example.toaccountornot.utils.TotalBudget;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 该活动创建自定义一级菜单，二级菜单
 */
public class CreateFirstCategoryActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_first_category);
        intent = getIntent();
        if (intent.getStringExtra("FirstOrSecond").equals("budget")) {
            new XPopup.Builder(this)
                    .dismissOnTouchOutside(false)
                    .asInputConfirm("设置总预算", "请设置金额。",
                    new OnInputConfirmListener() {
                        @Override
                        public void onConfirm(String text) {
                            TotalBudget updateBudget = new TotalBudget();
                            updateBudget.setBudget(Double.valueOf(text));
                            updateBudget.update(1);
                            finish();
                        }
                    }).show();
        }else if (intent.getStringExtra("FirstOrSecond").equals("subbudget")){
            final String name = intent.getStringExtra("BudgetName");
            new XPopup.Builder(this)
                    .dismissOnTouchOutside(false)
                    .asInputConfirm("设置"+name+"预算", "请设置金额。",
                            new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {
                                    TotalBudget updateBudget = new TotalBudget();
                                    updateBudget.setBudget(Double.valueOf(text));
                                    updateBudget.updateAll("name = ?",name);
                                    finish();
                                }
                            }).show();
        }else if (intent.getStringExtra("FirstOrSecond").equals("second")) {
            new XPopup.Builder(this).dismissOnTouchOutside(false).asInputConfirm("添加二级类别", "请输入内容。",
                    new OnInputConfirmListener() {
                        @Override
                        public void onConfirm(String text) {
                            First updateFirst = new First();
                            First oldFirst = LitePal.where("name = ?",intent.getStringExtra("FirstName")).findFirst(First.class);
                            ArrayList<String> tempSecondList = oldFirst.getSecond();
                            tempSecondList.add(text);
                            updateFirst.setSecond(tempSecondList);
                            updateFirst.updateAll("name = ?",intent.getStringExtra("FirstName"));
                            Intent intentFirst = new Intent(CreateFirstCategoryActivity.this, AccountActivity.class);
                            startActivity(intentFirst);
                        }
                    })
                    .show();//添加二级菜单
        }else {//添加一级
            new XPopup.Builder(this).dismissOnTouchOutside(false).asInputConfirm("添加一级类别", "请输入内容。",
                    new OnInputConfirmListener() {
                        @Override
                        public void onConfirm(String text) {
                            First first = new First();
                            first.setName(text);
                            first.setImage(R.drawable.setting);
                            first.setInorout(getIntent().getStringExtra("inorout"));
                            ArrayList<String> secondList = new ArrayList<String>();
                            secondList.add("添加自定义");
                            first.setSecond(secondList);
                            first.save();
//                        finish();
                            Intent intentFirst = new Intent(CreateFirstCategoryActivity.this, AccountActivity.class);
                            startActivity(intentFirst);
                        }
                    })
                    .show();
        }
    }
}
