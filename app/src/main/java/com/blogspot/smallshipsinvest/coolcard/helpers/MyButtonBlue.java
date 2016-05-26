package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.smallshipsinvest.coolcard.R;

public class MyButtonBlue extends Button {

    public int shadow;

    public MyButtonBlue(Context context) {
        super(context);
        set(context);
    }

    public MyButtonBlue(Context context, AttributeSet attrs) {
        super(context, attrs);
        set(context);
    }

    public MyButtonBlue(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        set(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundResource(R.drawable.my_button_blue_normal);
            setPadding(getPaddingLeft() - shadow, getPaddingTop() - shadow, getPaddingRight() + shadow,
                    getPaddingBottom() + shadow);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setBackgroundResource(R.drawable.my_button_blue_pressed);
            setPadding(getPaddingLeft() + shadow, getPaddingTop() + shadow, getPaddingRight() - shadow,
                    getPaddingBottom() - shadow);
        }

        return value;
    }

    private void set(Context context) {
        shadow = context.getResources().getDimensionPixelSize(R.dimen.my_button_shadow_shift);
        int leftPadding = context.getResources().getDimensionPixelSize(R.dimen.my_button_left_padding);
        int topPadding = context.getResources().getDimensionPixelSize(R.dimen.my_button_top_padding);
        setPadding(leftPadding, topPadding, leftPadding + shadow, topPadding + shadow);
        setBackgroundResource(R.drawable.my_button_blue_normal);
        setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
    }

}
