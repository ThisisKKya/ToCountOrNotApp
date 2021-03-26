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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toaccountornot.CreateFirstCategoryActivity;
import com.example.toaccountornot.NavigationActivity;
import com.example.toaccountornot.R;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardHelper;
import com.example.toaccountornot.ui.account.account_tab_ui.MyKeyboardView;
import com.example.toaccountornot.utils.Accounts;
import com.example.toaccountornot.utils.Cards;
import com.example.toaccountornot.utils.First;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
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
    public List<First> categoryList = new ArrayList<>();
    public List<String> cardString = new ArrayList<>();
    public List<String> memberString = new ArrayList<>();
    public List<String> secondString = new ArrayList<>();
    public String minorout;
    Double mtvinput = 0.0;
    String mfirstCategory;
    Date mtime = Calendar.getInstance().getTime();
    String msecondCategory = "无";
    String mcard = "微信";
    String mmember = "我";
    TextView tvSecond,tvCard,tvmember;
    EditText etInput;
    LinearLayout llKeborad;
    MyKeyboardView keyboard_temp;
    MyKeyboardHelper helper;
    RecyclerView recyclerView;
    CategoryAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCategory();
        initStringList();
    }

    private static final String TAG = "kk";
    @Override
    public void onResume() {
        super.onResume();
        initCategory();
        initRecycler();
        Log.d(TAG, "onResume: running");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_basecategory,container,false);
        etInput = view.findViewById(R.id.etInput);
        tvSecond = view.findViewById(R.id.secondCategory);
        tvCard = view.findViewById(R.id.card);
        tvmember = view.findViewById(R.id.member);
        tvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .asBottomList("账户", cardString.toArray(new String[cardString.size()]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        tvCard.setText("账户:"+text);
                                        mcard = text;
                                        //Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        tvmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .asBottomList("成员", memberString.toArray(new String[memberString.size()]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        tvmember.setText("成员:"+text);
                                        mmember = text;
                                       // Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
            }
        });
        tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initsecondstring();
                new XPopup.Builder(getContext())
                        .asBottomList("请选择一项", //new String[]{"子类1"},//
                                 secondString.toArray(new String[secondString.size()]),
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        if (text.equals("添加自定义")) {
                                            Intent intent = new Intent();
                                            intent.putExtra("FirstOrSecond","second");
                                            intent.putExtra("FirstName",mfirstCategory);
                                            intent.setClass(getContext(), CreateFirstCategoryActivity.class);
                                            startActivity(intent);
                                        }else {
                                            tvSecond.setText(mfirstCategory+"("+text+")");
                                            msecondCategory = text;
//                                            Toast.makeText(getContext(),"click " + text,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .show();
            }
        });
        keyboard_temp = view.findViewById(R.id.keyboard_temp);
        llKeborad = view.findViewById(R.id.llKeborad);
        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        initKey();
        initRecycler();

        return view;
    }
    public  void initRecycler() {
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        adapter.setMyViewClickListener(new CategoryAdapter.MyViewClickListener() {
            @Override
            public void callKeyboard(String firstCategory) {
                mfirstCategory = firstCategory;
                initsecondstring();
                if (mfirstCategory.equals("自定义") ) {
//                    Toast.makeText(getContext(),mfirstCategory,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("FirstOrSecond","first");
                    intent.putExtra("inorout",minorout);
                    intent.setClass(getContext(), CreateFirstCategoryActivity.class);
                    startActivity(intent);
                } else {
                    if (llKeborad.getVisibility() == View.GONE){
                        if(minorout == "trans")
                            tvSecond.setText("转入账户:");
                        else
                            tvSecond.setText(mfirstCategory+"(无)");
                        llKeborad.setVisibility(View.VISIBLE);
                    }
                    else {
                        if (etInput.length() != 0) {
                            mtvinput = Double.valueOf(etInput.getText().toString().trim());
                        }
                        llKeborad.setVisibility(View.GONE);
                        etInput.setText(mtvinput.toString());
                    }
                }
            }
        });
    }
    public void initCategory() {
    }
    public void initsecondstring(){
        Log.d("hello",mfirstCategory);
        List<First> firsts = LitePal.where("name = ?",mfirstCategory).find(First.class);
        secondString.clear();
        for(First first:firsts){
            secondString.clear();
            for(int i=0; i<first.getSecond().size();i++){
                Log.d("hello",first.getSecond().get(i).toString());
                secondString.add(first.getSecond().get(i).toString());
            }
        }
    }
    public void initStringList() {

        List<Cards> list = LitePal.findAll(Cards.class);    //从数据库读账户
        for (Cards card:list) {
            cardString.add(card.getCard());
        }
        memberString.add("爸爸");
        memberString.add("妈妈");
        memberString.add("我");
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
//                        Log.i("=--coed ",code+"   "+ key.label );
                    }}
            }

            @Override
            public void doneCallback() {
                if (etInput.length() != 0) {
                    mtvinput = Double.valueOf(etInput.getText().toString().trim());
                }
                Accounts accounts = new Accounts();
                First oldFirst = LitePal.where("name = ?",mfirstCategory).findFirst(First.class);
                First updatefirst = new First();
                updatefirst.setThisMonthCost(oldFirst.getCost()+mtvinput);
                updatefirst.updateAll("name = ?",mfirstCategory);
                accounts.setFirst(mfirstCategory);
//                accounts.setTime(mtime);
                if(minorout == "trans"){
                    accounts.setCard(mcard+","+msecondCategory);    //  mcard转出账户 msecond转入账户
                    accounts.setSecond(mcard+"->"+msecondCategory);
                }
                else{
                    accounts.setCard(mcard);
                    accounts.setSecond(msecondCategory);
                }
                accounts.setInorout(minorout);
                accounts.setMember(mmember);
                accounts.setPrice(mtvinput);
                Calendar calendar = Calendar.getInstance();
                if (mtime == null) {
                    mtime = calendar.getTime();
                }
                calendar.setTime(mtime);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                accounts.setDate(simpleDateFormat.format(mtime));
                accounts.setDate_year(String.valueOf(calendar.get(Calendar.YEAR)));
                accounts.setDate_month(String.valueOf(calendar.get(Calendar.MONTH) + 1));
                accounts.setDate_week(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
                accounts.save();
//                Toast.makeText(getContext(),"已完成",Toast.LENGTH_SHORT).show();
                Keyboard.Key key = helper.getKey(-100000);
                Intent intent = new Intent(getContext(), NavigationActivity.class);
                getActivity().finish();
                startActivity(intent);
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
                                Keyboard.Key key = helper.getKey(-100000);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(mtime);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                key.label = simpleDateFormat.format(mtime);
                                keyboard_temp.invalidate();
//                                Toast.makeText(getContext(), "选择的时间："+date.toLocaleString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                new XPopup.Builder(getContext())
                        .asCustom(popup)
                        .show();
            }
        });
    }
}

