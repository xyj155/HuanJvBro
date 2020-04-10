package cn.huanjv.base_module.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {
    private static final String MESSAGE = "message";


    public static void setMessage(Context context, String message) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(MESSAGE, message).apply();
    }

    public static String getMessage(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(MESSAGE, "");
    }
}