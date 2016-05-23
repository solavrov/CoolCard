package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.blogspot.smallshipsinvest.coolcard.R;

public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            setBackgroundResource(R.drawable.my_button_normal);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setBackgroundResource(R.drawable.my_button_pressed);
            setPadding(getPaddingLeft() + 1, getPaddingTop() + 1, getPaddingRight() - 1,
                    getPaddingBottom() - 1);
        }

        return value;
    }

}
