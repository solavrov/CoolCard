package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewHelper {

    public static void setSpinnerFromResource(Context context, int spinnerID, int arrayID, int
            headLayoutID, int dropdownLayoutID) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayID, headLayoutID);
        adapter.setDropDownViewResource(dropdownLayoutID);
        Activity activity = (Activity) context;
        ((Spinner) activity.findViewById(spinnerID)).setAdapter(adapter);
    }

    public static void sendFadingMessage(TextView tv, int messageID, long duration, float targetAlpha) {
        String message = tv.getContext().getResources().getString(messageID);
        tv.setText(message);
        ObjectAnimator fade = ObjectAnimator.ofFloat(tv, "alpha", 1f, targetAlpha);
        fade.setDuration(duration);
        fade.start();
    }

}
