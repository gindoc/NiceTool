package com.a3xh1.basecore.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Author: GIndoc on 2018/2/6 上午10:55
 * email : 735506583@qq.com
 * FOR   :
 */
public class LanguageUtils {
    private static final LanguageUtils ourInstance = new LanguageUtils();

    public static LanguageUtils getInstance() {
        return ourInstance;
    }

    private Map<String, String> languages = new HashMap<>();

    private LanguageUtils() {
        languages.put("zh_CN", "CN");
        languages.put("zh_TW", "TRAD");
        languages.put("en", "EN");
    }

    private String getLanguage(String key) {
        return languages.get(key);
    }

    public String getLocaleLanguage() {
        String locale = Locale.getDefault().toString();
        String lang = locale.contains("en")?locale.split("_")[0]:locale;
        return getLanguage(lang);
    }

    public Map<String, Object> getCommonHeader() {
        Map<String, Object> headers = new HashMap<>();
        long time = System.currentTimeMillis();
        String key = time + "1#j0ZAqg";
        headers.put("times", time);
        headers.put("key", EnCodeUtil.MD5(key));
        return headers;
    }

    public Map<String, Object> getCommonHeaderWithLang() {
        Map<String, Object> header = getCommonHeader();
        header.put("language", getLocaleLanguage());
        return header;
    }
}
