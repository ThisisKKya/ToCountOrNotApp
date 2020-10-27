package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.toaccountornot.utils.First;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CreateFirstCategoryActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_first_category);
        intent = getIntent();
        if (intent.getStringExtra("FirstOrSecond").equals("second")) {
            new XPopup.Builder(this).asInputConfirm("添加二级类别", "请输入内容。",
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
            new XPopup.Builder(this).asInputConfirm("添加一级类别", "请输入内容。",
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
