package com.a3xh1.basecore.utils;

import android.content.Context;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/5/17.
 */

public class FileUtil {
    private final static String PATTERN = "yyyyMMddHHmmss";
    private static final String SEPARATOR = File.separator;
    private static final String EXTERNAL_ROOT = "factoryOnline";

    private static final String EXTERNAL_STORAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + SEPARATOR + EXTERNAL_ROOT;

    private static final String IMAGE = "image";
    private static final String PARENT_PATH = SEPARATOR + "jyk" + SEPARATOR;

    public static ArrayList<String> getAllFilePath(String filePath) {
        File file = new File(filePath);
        File[] subFile = file.listFiles();
        ArrayList<String> fileList = new ArrayList<>();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getAbsolutePath();
                fileList.add(filename);
            }
        }
        return fileList;
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createTempImage(/*Context context*/) {
        String timeStamp = new SimpleDateFormat(PATTERN, Locale.CHINA).format(new Date());
        File file = new File(getImagePath(), timeStamp + ".jpg");
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public static String getImagePath() {
        return getStoreExternalPath(IMAGE) + PARENT_PATH;
    }

    private static String getStoreExternalPath(String folder) {
        File file = new File(EXTERNAL_STORAGE + SEPARATOR + folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
