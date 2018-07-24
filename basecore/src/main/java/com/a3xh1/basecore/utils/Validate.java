package com.a3xh1.basecore.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by louiszgm on 2016/10/22.
 */

public class Validate {

    /**
     * 验证是否是合法手机号
     * @param phone_num
     * @return
     */
    public static boolean validatePhoneNum(String phone_num) {
        if (TextUtils.isEmpty(phone_num)) return false;
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[0135678]|18\\d)\\d{8}|170[059]\\d{7}"); // 验证手机号
//        p = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[5678]|18\\d)\\d{8}|170[059]\\d{7}"); // 验证手机号
        m = p.matcher(phone_num);
        return m.matches();
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (TextUtils.isEmpty(cardId)) {
            return false;
        }
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
