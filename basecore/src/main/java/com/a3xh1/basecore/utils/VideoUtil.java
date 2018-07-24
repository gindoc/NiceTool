package com.a3xh1.basecore.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.util.HashMap;

/**
 * Created by lidacheng on 2018/2/27.
 */

public class VideoUtil {
    public static File getLocalVideoCover(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        File file = null;
        try {
            //绑定资源
            mmr.setDataSource(path);
            //获取第一帧图像的bitmap对象
            Bitmap bitmap = mmr.getFrameAtTime();
            file = BitmapUtil.saveBitmap(bitmap);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        return file;
    }

    public static String getDuration(String uri) {
        String duration = null;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            if (uri != null) {
                HashMap<String, String> headers = null;
                headers = new HashMap<String, String>();
                headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                mmr.setDataSource(uri, headers);
            }
            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        return duration;
    }
}
