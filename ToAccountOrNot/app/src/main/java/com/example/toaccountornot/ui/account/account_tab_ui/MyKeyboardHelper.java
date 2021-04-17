package com.example.toaccountornot.ui.account.account_tab_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.toaccountornot.R;

public class MyKeyboardHelper {
    private Context context;
    private MyKeyboardView keyboardView;
    private EditText editText; //显示该键盘的EditText
    private Keyboard k1;// 自定义键盘
    private KeyboardCallBack callBack;//按键回调监听

    private String amount;
    private boolean flag = false;

    public MyKeyboardHelper(Context context, MyKeyboardView keyboardView) {
        this(context, keyboardView, null);
    }

    public MyKeyboardHelper(Context context, MyKeyboardView keyboardView, KeyboardCallBack callBack) {
        this.context = context;
        k1 = new Keyboard(context, R.layout.keyboard);//据Keyboard的xml布局绑定
        this.keyboardView = keyboardView;
        this.keyboardView.setOnKeyboardActionListener(listener);//设置键盘监听
        this.keyboardView.setKeyboard(k1);//设置默认键盘
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.callBack = callBack;
    }


    public MyKeyboardView getKeyBoradView() {
        return keyboardView;
    }

    /**
     *根据code，返回一个具体的key对象
     */
    public Keyboard.Key getKey(int code) {
        Keyboard.Key key = null;
        if (k1 != null) {
            for (int i = 0; i < k1.getKeys().size(); i++) {
                int[] codes = k1.getKeys().get(i).codes;
                if (codes[0] == code) {
                    key = k1.getKeys().get(i);
                    break;
                }
            }
        }
        return key;
    }

    private void insertParse() {
        SharedPreferences imageParse = context.getSharedPreferences("imageparse",Context.MODE_PRIVATE);
        amount = imageParse.getString("amount",null);
        if(amount!=null)
            flag = true;
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
            //当key中有keyOutputText属性时，点击键盘会触发该方法，回调keyOutputText的值
            Editable editable = (Editable) editText.getText();
            int end = editText.getSelectionEnd();
            editable.delete(0, end);
            editable.insert(0, text);
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {

            //设置了codes属性后，点击键盘会触发该方法，回调codes的值
            //codes值与ASCLL码对应
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            int end = editText.getSelectionEnd();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (editable != null && editable.length() > 0) {
                        if (start == end) {
                            editable.delete(start - 1, start);
                        } else {
                            editable.delete(start, end);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_DONE:
                    if (callBack != null) {// =：计算。完成：回调
                        Keyboard.Key key = getKey(-4);
                        if (key.label.equals("=")) {
                            if (start != end) {
                                editable.delete(start, end);
                            }
                            if (defaultLogic((char) primaryCode, editable, start, end, true))
                                return;
                        } else {
                            callBack.doneCallback();
                        }

                    }
                    break;
                case -100000:
                    if (callBack != null) {
                        callBack.dateCallback(getKey(-100000));
//                        callBack.updateDateCallback();
                    }
                    break;
                default:
                    if (start != end) {
                        editable.delete(start, end);
                    }
                    if (defaultLogic((char) primaryCode, editable, start, end, false)) return;
                    break;
            }
            if (callBack != null) {
                callBack.keyCall(primaryCode, editText.getText().toString());
            }
        }
    };

    /**
     * @param isequals 用户是不是点击的=号
     *                 注意：isequals=true时，不能判断以 primaryCode 为判断标准。
     *                 因为 =号/完成：这个是取的系统的状态code（完成：code=-4）,转成char是个空值
     */
    private boolean defaultLogic(char primaryCode, Editable editable, int start, int end, boolean isequals) {
        Log.i("Joker", "KeyBoradHelper: primaryCode = " + primaryCode);
        /**
         * 只要输入+ 或 -  更改=状态
         * */
        if (Character.toString(primaryCode).equals("+") || Character.toString(primaryCode).equals("-")) {
            Keyboard.Key key = getKey(-4);
            key.label = "=";
        }
        /**
         *editText当前的值为0，后面不能继续输入0。即：开头为：00 情况  为0时,继续输入数字时直接把0替换掉
         * */
        if (editText.getText().toString().equals("0") && (Character.toString(primaryCode).equals("0")
                || Character.toString(primaryCode).equals("0")
                || Character.toString(primaryCode).equals("1")
                || Character.toString(primaryCode).equals("2")
                || Character.toString(primaryCode).equals("3")
                || Character.toString(primaryCode).equals("4")
                || Character.toString(primaryCode).equals("5")
                || Character.toString(primaryCode).equals("6")
                || Character.toString(primaryCode).equals("7")
                || Character.toString(primaryCode).equals("8")
                || Character.toString(primaryCode).equals("9")
        )) {
            editable.delete(0, end);
            editable.insert(0, Character.toString(primaryCode));
            start = editText.getSelectionStart();
            return true;
        }

        /** =
         * 不能以 . +  -
         * */
        if (editText.getText().toString().isEmpty()) {
            if (Character.toString(primaryCode).equals(".") || Character.toString(primaryCode).equals("+") || Character.toString(primaryCode).equals("-")) {
                return true;
            }
        }
        /**
         *  避免连续输入 . +  -
         * */
        if (editText.getText().toString().endsWith("+") || editText.getText().toString().endsWith("-") || editText.getText().toString().endsWith(".")) {
            // 避免重复连续的+ 或 -号
            if (Character.toString(primaryCode).toString().equals("+") || Character.toString(primaryCode).toString().equals("-") || Character.toString(primaryCode).toString().equals(".") || isequals) {
                // 以+、-、结尾时 用户点击 = 计算    以 . 结尾时不能去计算
                if (isequals && !editText.getText().toString().endsWith(".")) {
                    String input = editText.getText().toString();
                    String substring = input.substring(0, input.length() - 1);
                    editable.delete(0, end);// 删除已经插入
                    editable.insert(0, substring);//把插入计算结果
                    Keyboard.Key key = getKey(-4);
                    if (key != null) { //  以+ - 结尾时的计算后，需要把 = 号恢复为完成状态
                        key.label = "完成";
                    }
                    return true;
                }
                Log.i("JokerKeyBoradHelper", ". +  - 再次结尾输入不处理");
            } else {
                Log.i("JokerKeyBoradHelper", "插入");
                editable.insert(start, Character.toString(primaryCode));
            }
        } else {
            // editText中已经存在+ 或 -  再继续输入就进行计算
            if (editText.getText().toString().contains("+") || editText.getText().toString().contains("-")) {
                /**
                 * 1、输入的是+ 或 - 计算
                 * 2、如果是按的=  计算（不把=号 插入结果后面） isequals是判断是不是点击的=
                 */
                if (Character.toString(primaryCode).equals("+") || Character.toString(primaryCode).equals("-") || isequals) {
                    if (editText.getText().toString().contains("+")) {
                        /**
                         * 加法计算
                         * 1.editText存在 +  在输入 +或- 就会自动进行计算
                         * 2.负数场景：计算过一次，得到负数，继续输入进行计算
                         * */

                        String[] split = editText.getText().toString().replace("+", ",").split(",");
                        if (split == null) return true;
                        if (split.length > 1) {
                            String addResult = toCalculate(split, true);
                            editable.delete(0, end);// 删除已经插入的
                            editable.insert(0, addResult);//重新插入计算结果
                            if (!isequals) {
                                start = editText.getSelectionStart(); // 重新获取插入开始位置
                                editable.insert(start, Character.toString(primaryCode));// 把加号在插入到后面
                            }
                        } else {
                            Log.i("Joker", "KeyBoradHelper: editText = " + editText.getText().toString() + "\n Split length = " + split.length);
                        }
                    } else if (editText.getText().toString().contains("-")) { //&& Character.toString((char) primaryCode).equals("-")
                        /**
                         * 减法计算:3种场景
                         *   1.需要判断第一次计算产生负数（- 开头），继续进行 - 或 = 输入计算。
                         *   2.需要判断第一次计算产生负数（- 开头），继续输入- 计算，在点击 = ，在输入 - 计算的情况。 例如：上次计算结果为负数为-3。-3-3，继续输入-，Edittext得到:-6-，按= 得到-6，在按- 计算的情况
                         *   3.editText存在- （两个整数相减） 继续输入  + 或 - 就会自动进行计算
                         * */
                        String[] split = null;
                        if (editText.getText().toString().startsWith("-")) { // -号开头
                            int indexOf = editText.getText().toString().lastIndexOf("-");
                            if (indexOf != 0) {
                                StringBuilder builder = new StringBuilder(editText.getText().toString());
                                builder.replace(indexOf, indexOf + 1, ","); // 替换指定位置的字符
                                split = builder.toString().split(",");
                                Log.i("Joker", "-开头：indexOf!=0  ->indexof = " + indexOf + "\nbuilder = " + builder.toString() + "\n split = " + split.length + "  split[0]= " + split[0] + "  split[1]= " + split[1]);
                            } else {
                                editable.insert(start, Character.toString(primaryCode));
                                Log.i("Joker", "-开头：indexof = " + indexOf);
                            }
                        } else {
                            split = editText.getText().toString().replace("-", ",").split(",");
                        }
                        if (split == null) return true;
                        if (split.length > 1) {
                            String addResult = toCalculate(split, false);
                            editable.delete(0, end);// 删除已经插入
                            editable.insert(0, addResult);//把插入计算结果
                            if (!isequals) {// 按的 =  不插入结果后面
                                start = editText.getSelectionStart(); // 重新获取插入开始位置
                                editable.insert(start, Character.toString(primaryCode));// 把加号在插入到后面
                            }
                        } else {
                            Log.i("JokerKeyBoradHelper - ", editText.getText().toString() + " Split length = " + split.length);
                        }
                    }
                } else {
                    editable.insert(start, Character.toString(primaryCode));
                }
            } else {
                editable.insert(start, Character.toString(primaryCode));
            }
        }
        return false;
    }

    //计算
    public String toCalculate(String[] split, boolean isAddition) {

        String add_1 = split[0].isEmpty() ? "0" : split[0];
        String add_2 = split[1];
        String addResult = "";
        int i_1 = 0;
        int i_2 = 0;
        double d_1 = 0;
        double d_2 = 0;
        /**
         * 1、判断是否是小数
         * */
        Log.i("toCalculate ", add_1 + "__" + add_2);
        if (add_1.contains(".")) {
            d_1 = Double.parseDouble(add_1);
        } else {
            i_1 = Integer.parseInt(add_1);
        }
        if (add_2.contains(".")) {
            d_2 = Double.parseDouble(add_2);
        } else {
            i_2 = Integer.parseInt(add_2);
        }

        if (isAddition) {

            if ((i_1 == 0 && d_1 != 0) && (i_2 == 0 && d_2 != 0)) {//d_1 + d_2
                addResult = (d_1 + d_2) + "";
            }
            if ((i_1 != 0 && d_1 == 0) && (i_2 == 0 && d_2 != 0)) {//i_1 + d_2
                addResult = (i_1 + d_2) + "";
            }
            if ((i_1 != 0 && d_1 == 0) && (i_2 != 0 && d_2 == 0)) {//i_1 + i_2
                addResult = (i_1 + i_2) + "";
            }
            if ((i_1 == 0 && d_1 != 0) && (i_2 != 0 && d_2 == 0)) {//d_1 + i_2
                addResult = (d_1 + i_2) + "";
            }
            // 0开头情况
            if ((i_1 == 0 && d_1 == 0) && (i_2 != 0 && d_2 == 0)) {//
                addResult = (0 + i_2) + "";
            }
            if ((i_1 == 0 && d_1 == 0) && (i_2 == 0 && d_2 != 0)) {//
                addResult = (0 + d_2) + "";
            }
            if ((i_1 != 0 && d_1 == 0) && (i_2 == 0 && d_2 == 0)) {//
                addResult = (i_1 + 0) + "";
            }
            if ((i_1 == 0 && d_1 != 0) && (i_2 == 0 && d_2 == 0)) {//
                addResult = (d_1 + 0) + "";
            }
        } else {
            if ((i_1 == 0 && d_1 != 0) && (i_2 == 0 && d_2 != 0)) {//d_1 + d_2
                addResult = (d_1 - d_2) + "";
            }
            if ((i_1 != 0 && d_1 == 0) && (i_2 == 0 && d_2 != 0)) {//i_1 + d_2
                addResult = (i_1 - d_2) + "";
            }
            if ((i_1 != 0 && d_1 == 0) && (i_2 != 0 && d_2 == 0)) {//i_1 + i_2
                addResult = (i_1 - i_2) + "";
            }
            if ((i_1 == 0 && d_1 != 0) && (i_2 != 0 && d_2 == 0)) {//d_1 + i_2
                addResult = (d_1 - i_2) + "";
            }
            // 0 开头
            if ((i_1 == 0 && d_1 == 0) && ((i_2 == 0 && d_2 != 0))) {
                addResult = (0 - d_2) + "";
            }
            if ((i_1 == 0 && d_1 == 0) && ((i_2 != 0 && d_2 == 0))) {
                addResult = (0 - i_2) + "";
            }
            if ((i_1 != 0 && d_1 == 0) && ((i_2 == 0 && d_2 == 0))) {
                addResult = (i_1 - 0) + "";
            }
            if ((i_1 == 0 && d_1 != 0) && ((i_2 == 0 && d_2 == 0))) {
                addResult = (d_1 - 0) + "";
            }
        }

        return addResult;
    }


    //在显示键盘前应调用此方法，指定EditText与KeyboardView绑定
    public void setEditText(EditText editText) {
        this.editText = editText;
        //关闭进入该界面获取焦点后弹出的系统键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        //隐藏该EditText获取焦点而要弹出的系统键盘
        MyKeyboardUtil.hideSoftInput(editText);
    }

    //Activity中获取焦点时调用，显示出键盘
    public void show() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    //隐藏键盘
    public void hide() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }

    public boolean isVisibility() {
        if (keyboardView.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public interface KeyboardCallBack {
        void keyCall(int code, String content);

        void doneCallback();

        void dateCallback(Keyboard.Key key);


    }

    //设置回调，用于自定义特殊按键在不同界面或EditText的处理
    public void setCallBack(KeyboardCallBack callBack) {
        this.callBack = callBack;
    }
}
