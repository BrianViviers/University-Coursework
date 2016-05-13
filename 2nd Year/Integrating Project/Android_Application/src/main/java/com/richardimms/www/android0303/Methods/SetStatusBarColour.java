package com.richardimms.www.android0303.Methods;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.support.v7.app.ActionBar;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by philip on 07/04/2015.
 */
public class SetStatusBarColour {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTaskbar(Window window)
    {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#FF689DCB"));
    }

    public static void setActionbarColour(ActionBar bar)
    {
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff90caf9")));
    }
}
