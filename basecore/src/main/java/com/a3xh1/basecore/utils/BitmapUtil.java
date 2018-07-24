package com.a3xh1.basecore.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lidacheng on 2018/2/27.
 */

public class BitmapUtil {
    public static File saveBitmap(Bitmap bitmap) {
        File resultFile = FileUtil.createTempImage();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(resultFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultFile;
    }
}
