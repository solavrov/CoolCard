package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Navigator {

    public static boolean FINISH = true;
    public static boolean KEEP = false;
    public static boolean START = true;
    public static boolean REORDER = false;

    public static void run(Context context, Class target, boolean finishOrKeep, boolean startOrReorder) {
        Activity activity = (Activity) context;
        if (finishOrKeep && startOrReorder) {
            Intent intent = new Intent(context, target);
            activity.startActivity(intent);
            activity.finish();
        }
        if (!finishOrKeep && startOrReorder) {
            Intent intent = new Intent(context, target);
            activity.startActivity(intent);
        }
        if (finishOrKeep && !startOrReorder) {
            Intent intent = new Intent(context, target);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
            activity.finish();
        }
        if (!finishOrKeep && !startOrReorder) {
            Intent intent = new Intent(context, target);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
        }
    }
}
