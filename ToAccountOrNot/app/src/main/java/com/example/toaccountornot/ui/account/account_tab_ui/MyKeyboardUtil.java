package com.example.toaccountornot.ui.account.account_tab_ui;

import android.content.Context;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

public class MyKeyboardUtil {
    //隐藏系统键盘
    //安卓4.0以下隐藏软键盘只需setInputType(InputType.TYPE_NULL)即可
    //4.0及以上，4.2以下需要调用setSoftInputShownOnFocus方法
    //4.2以上需要调用setShowSoftInputOnFocus方法
    //由于sdk的不一致，此处使用反射进行处理
    public static void hideSoftInput(EditText ed) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {// 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {// 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
        }
    }

    //显示被隐藏的系统键盘
    public static void showSoftInput(EditText ed) {
        InputMethodManager inputManager = (InputMethodManager) ed.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ed, 0);
    }
}