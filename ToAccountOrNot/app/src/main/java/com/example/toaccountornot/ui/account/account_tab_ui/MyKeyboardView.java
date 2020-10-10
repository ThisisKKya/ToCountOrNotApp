package com.example.toaccountornot.ui.account.account_tab_ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;

import com.example.toaccountornot.R;

import java.lang.reflect.Field;
import java.util.List;

public class MyKeyboardView extends KeyboardView {
    private Context context;
    private Paint paint;
    private Rect bounds;

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        bounds = new Rect();
        this.context = context;
    }

    public MyKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        bounds = new Rect();
        this.context = context;

    }

    Canvas canvas;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == -4) { // = 和 完成
                drawSpecialKey(canvas, key);
            } else if (key.codes[0] == -5) {//删除

            } else {
                //绘制普通信息文本的背景
                drawBackground(R.drawable.key_background, canvas, key);
            }
            //绘制普通信息的文本信息
            drawText(canvas, key);

        }
    }

    private void drawSpecialKey(Canvas canvas, Keyboard.Key key) {
        if (key.codes[0] == -4) {
            drawBackground(R.drawable.key_done_background, canvas, key);
        }
        // 可根据code值，进行区分绘制
    }

    private void drawBackground(@DrawableRes int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        int[] state = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            drawable.setState(state);
        }
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        drawable.draw(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void drawText(Canvas canvas, Keyboard.Key key) {
        if (key.label != null) {
            String label = key.label.toString();
            Field field;
            int keyTextSize;
            try {
                //获取KeyboardView设置的默认文本字体大小
                field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                field.setAccessible(true);
                keyTextSize = (int) field.get(this);
                paint.setTextSize(keyTextSize);
                if (key.codes[0] == -4) {
                    paint.setColor(Color.parseColor("#FFFFFF"));
                } else {
                    paint.setColor(Color.parseColor("#222222"));
                }
                paint.setTypeface(Typeface.DEFAULT);
                paint.getTextBounds(label, 0, label.length(), bounds);
                canvas.drawText(label, key.x + (key.width / 2), (key.y + key.height / 2) + bounds.height() / 2, paint);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
