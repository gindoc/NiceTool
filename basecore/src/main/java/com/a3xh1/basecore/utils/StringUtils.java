package com.a3xh1.basecore.utils;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Author: GIndoc on 2017/11/21 上午10:43
 * email : 735506583@qq.com
 * FOR   :
 */
public class StringUtils {

    public static String decimalFormat(String format, double value) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }

    public static String decimalFormat(double value) {
        return decimalFormat("#,###.00", value);
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    public static String format(Context context, int stringRes, Object... args) {
        return String.format(context.getString(stringRes), args);
    }
}
