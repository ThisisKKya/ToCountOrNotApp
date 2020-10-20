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

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class BaseCategoryFragment extends Fragment   {
    public List<Category> categoryList = new ArrayList<>();
    String mfirstCategory;
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
                accounts.setSecond(etnote);
                accounts.setPrice(tvinput);
                accounts.save();
                Toast.makeText(getContext(),"已完成",Toast.LENGTH_SHORT).show();
                Keyboard.Key key = helper.getKey(-100000);
                Intent intent = new Intent(getContext(), NavigationActivity.class);
                startActivity(intent);
            }

            @Override
            public void dateCallback(final Keyboard.Key key) {
//                DateSelectDialog.getCalendar(, new DateSelectDialog.DateTimeCallback() {
//                    @Override
//                    public Void timeCallback(String time) {
//                        key.label = time;
//                        // 这里调用了系统日历，需要调用view的postInvalidate进行重绘
//                        helper.getKeyBoradView().postInvalidate();
//                        return null;
//                    }
//                });
            }
        });
    }
}

