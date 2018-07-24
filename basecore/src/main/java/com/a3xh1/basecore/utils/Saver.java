package com.a3xh1.basecore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Saver {
    private static SharedPreferences sharePref;

    public static SharedPreferences getInstance() {
        return sharePref;
    }

    public static void initSaver(Context context) {
        sharePref = context.getSharedPreferences("saveinfo", Context.MODE_PRIVATE);
    }

    public static boolean isFirstIn() {
        return sharePref.getBoolean(Const.SharePreferenceKey.IS_FIRST_IN, false);
    }

    public static void setFirstIn() {
        Editor editor = sharePref.edit();
        editor.putBoolean(Const.SharePreferenceKey.IS_FIRST_IN, true);
        editor.commit();
    }
}
