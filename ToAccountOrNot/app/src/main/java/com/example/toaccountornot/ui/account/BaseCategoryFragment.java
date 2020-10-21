package com.example.toaccountornot.ui.account;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardHelper;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardView;
import com.example.toaccountornot.utils.Accounts;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.popup.TimePickerPopup;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BaseCategoryFragment extends Fragment   {
    public List<Category> categoryList = new ArrayList<>();
    String mfirstCategory;
    Date mtime;
    String mcard;
    String mmember;
    TextView tvNote;
    EditText etInput, etNote;
    LinearLayout llKeborad;
    MyKeyboardView keyboard_temp;
    MyKeyboardHelper helper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCategory();
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basecategory,container,false);
        etNote = view.findViewById(R.id.etNote);
        etInput = view.findViewById(R.id.etInput);
        tvNote = view.findViewById(R.id.tvNoteHint);
        tvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .asBottomList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4", "条目5"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        keyboard_temp = view.findViewById(R.id.keyboard_temp);
        llKeborad = view.findViewById(R.id.llKeborad);
        initKey();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final CategoryAdapter adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        adapter.setMyViewClickListener(new CategoryAdapter.MyViewClickListener() {
            @Override
            public void callKeyboard(String firstCategory) {
                mfirstCategory = firstCategory;
//                Toast.makeText(getContext(),mfirstCategory,Toast.LENGTH_SHORT).show();
                if (llKeborad.getVisibility() == View.GONE){
                    llKeborad.setVisibility(View.VISIBLE);
                }
                else {
                    llKeborad.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }
    public void initCategory() {
    }

    public void initKey() {
        // 设置禁止获取焦点，这个etInput用于键盘输入和计算结果的展示
        etInput.setFocusable(false);
        etInput.setFocusableInTouchMode(false);
        //初始化KeyboardView
        helper = new MyKeyboardHelper(getContext(), keyboard_temp);
        // 软键盘捆绑etInput
        helper.setEditText(etInput);
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
                        Log.i("=--coed ",code+"   "+ key.label );
                    }}
            }

            @Override
            public void doneCallback() {

                String etnote = etNote.getText().toString().trim();
                Double tvinput = Double.valueOf(etInput.getText().toString().trim());
                Accounts accounts = new Accounts();
                accounts.setFirst(mfirstCategory);
//                accounts.setTime(mtime);
                accounts.setSecond(etnote);
                accounts.setPrice(tvinput);
                // 流水测试用
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mtime);
                accounts.setInorout("out");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                accounts.setDate(simpleDateFormat.format(mtime));
                accounts.setDate_year(String.valueOf(calendar.get(Calendar.YEAR)));
                accounts.setDate_month(String.valueOf(calendar.get(Calendar.MONTH)+1));

                accounts.save();
                Toast.makeText(getContext(),"已完成",Toast.LENGTH_SHORT).show();
                Keyboard.Key key = helper.getKey(-100000);
                Intent intent = new Intent(getContext(), NavigationActivity.class);
                startActivity(intent);
            }

            @Override
            public void dateCallback(final Keyboard.Key key) {
                Calendar date = Calendar.getInstance();
                date.set(2000, 5,1);
                Calendar date2 = Calendar.getInstance();
                date2.set(2020, 5,1);
                TimePickerPopup popup = new TimePickerPopup(getContext())
//                        .setDefaultDate(date)  //设置默认选中日期
//                        .setYearRange(1990, 1999) //设置年份范围
//                        .setDateRang(date, date2) //设置日期范围
                        .setTimePickerListener(new TimePickerListener() {
                            @Override
                            public void onTimeChanged(Date date) {
                                //时间改变
                            }
                            @Override
                            public void onTimeConfirm(Date date, View view) {
                                //点击确认时间
                                mtime = date;
                                Toast.makeText(getContext(), "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                new XPopup.Builder(getContext())
                        .asCustom(popup)
                        .show();
            }
        });
    }
}

