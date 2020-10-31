package com.example.toaccountornot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaccountornot.ui.account.CategoryAdapter;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardHelper;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardView;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.First;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.TimePickerPopup;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateBudget extends AppCompatActivity {
    public List<First> categoryList = new ArrayList<>();
    private EditText editText;
    TextView tvcate;
    private MyKeyboardView keyboard_temp;
    LinearLayout llKeborad;
    RecyclerView recyclerView;
    MyKeyboardHelper helper;
    String mfirstCategory;
    CategoryAdapter adapter;
    double mbudgetNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_budget);
        tvcate = findViewById(R.id.budget_category);
        editText = findViewById(R.id.budget_etInput);
        keyboard_temp =findViewById(R.id.budget_keyboard_temp);
        llKeborad = findViewById(R.id.budget_llKeborad);
        recyclerView = findViewById(R.id.budget_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateBudget.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        initCategory();
        initRecycler();
        initKey();


    }
    public void initKey() {
        // 设置禁止获取焦点，这个etInput用于键盘输入和计算结果的展示
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        //初始化KeyboardView
        helper = new MyKeyboardHelper(CreateBudget.this, keyboard_temp);
        // 软键盘捆绑etInput
        helper.setEditText(editText);
        helper.setCallBack(new MyKeyboardHelper.KeyboardCallBack() {
            @Override
            public void keyCall(int code, String content) {
//                if (!content.isEmpty() && !content.startsWith("+") && !content.startsWith("-")) {
//                    if (content.contains("+") || content.contains("-")) {
//                        //回调键盘监听，根据回调的code值进行处理
//                        if (code == 43 || code == 45) {
//                            Keyboard.Key key = helper.getKey(-4);
//                            key.label = "=";
//                        }
//                    } else {
//                        Keyboard.Key key = helper.getKey(-4);
//                        key.label = "完成";
//                    }
//                }
                if (!content.isEmpty()){
                    if (code == 43 || code == 45) {
                        Keyboard.Key key = helper.getKey(-4);
                        key.label = "=";
                        Log.i("=coed ",code+"  "+ key.label );
                    }
                    if (code ==-4) {
                        Keyboard.Key key = helper.getKey(-4);
                        key.label = "完成";
//                        Log.i("=--coed ",code+"   "+ key.label );
                    }}
            }

            @Override
            public void doneCallback() {
                if (editText.length() != 0) {
                    mbudgetNum = Double.valueOf(editText.getText().toString().trim());
                }
                First first = new First();
                first.setBudget(mbudgetNum);
                first.updateAll("name = ?",mfirstCategory);
//                Toast.makeText(CreateBudget.this,"已完成",Toast.LENGTH_SHORT).show();
                Keyboard.Key key = helper.getKey(-100000);
//                Intent intent = new Intent(CreateBudget.this, NavigationActivity.class);
//                startActivity(intent);
                finish();
            }

//            @Override
//            public void updateDateCallback() {
//                Keyboard.Key key = helper.getKey(-100000);
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(mtime);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                key.label = simpleDateFormat.format(mtime);
//            }

            @Override
            public void dateCallback(final Keyboard.Key key) {
//                Calendar date = Calendar.getInstance();
//                date.set(2000, 5,1);
//                Calendar date2 = Calendar.getInstance();
//                date2.set(2020, 5,1);
//                TimePickerPopup popup = new TimePickerPopup(getContext())
////                        .setDefaultDate(date)  //设置默认选中日期
////                        .setYearRange(1990, 1999) //设置年份范围
////                        .setDateRang(date, date2) //设置日期范围
//                        .setTimePickerListener(new TimePickerListener() {
//                            @Override
//                            public void onTimeChanged(Date date) {
//                                //时间改变
//                            }
//                            @Override
//                            public void onTimeConfirm(Date date, View view) {
//                                //点击确认时间
//                                mtime = date;
//                                Keyboard.Key key = helper.getKey(-100000);
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.setTime(mtime);
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                key.label = simpleDateFormat.format(mtime);
//                                keyboard_temp.invalidate();
////                                Toast.makeText(getContext(), "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                new XPopup.Builder(getContext())
//                        .asCustom(popup)
//                        .show();
            }
        });
    }
    public  void initRecycler() {
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        adapter.setMyViewClickListener(new CategoryAdapter.MyViewClickListener() {
            @Override
            public void callKeyboard(String firstCategory) {
                mfirstCategory = firstCategory;
                if (mfirstCategory.equals("自定义") ) {
//                    Toast.makeText(getContext(),mfirstCategory,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("FirstOrSecond","first");
                    intent.putExtra("inorout","out");
                    intent.setClass(CreateBudget.this, CreateFirstCategoryActivity.class);
                    startActivity(intent);
                } else {
                    if (llKeborad.getVisibility() == View.GONE){
                        tvcate.setText("预算类别：" +mfirstCategory);
                        llKeborad.setVisibility(View.VISIBLE);
                    }
                    else {
                        llKeborad.setVisibility(View.GONE);
                        editText.setText("0");
                    }
                }
            }
        });
    }
    public void initCategory() {
        categoryList = LitePal.where("inorout = ? or inorout = ?","out","all").find(First.class);
    }
}
