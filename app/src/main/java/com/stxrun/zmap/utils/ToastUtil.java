package com.stxrun.zmap.utils;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.Toast;

import com.stxrun.zmap.R;

/**
 * Created by stxr on 17-9-15.
 */

public class ToastUtil {
    public static void show(Context context, String string) {
        Toast.makeText(context, string,Toast.LENGTH_SHORT).show();
    }
}
