package com.a3xh1.basecore.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;

import com.a3xh1.basecore.base.BaseCoreFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/5/11.
 */

public class ImageUtil {

    private String filePath;

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, File file, Bitmap.CompressFormat format) {
        if (isEmptyBitmap(src) || file == null) return false;
        System.out.println(src.getWidth() + "," + src.getHeight());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            return src.compress(format, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            FileUtil.closeIO(fos);
        }
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }


    /**
     * 根据文件路径获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    public static Bitmap getBitmapByFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) return null;
        File file = new File(filePath);
        try {
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 压缩图片
     *
     * @param filePath  文件路径
     * @param reqWidth  期望的宽
     * @param reqHeight 期望的高
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressImage(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        opts.inSampleSize = 1;
        while (width > reqWidth || height > reqHeight) {
            opts.inSampleSize *= 2;                //inSampleSize为偶数时才有效
            width /= 2;
            height /= 2;
        }
        opts.inJustDecodeBounds = false;
        Bitmap image = BitmapFactory.decodeFile(filePath, opts);
        return image;
    }

    /**
     * 从uri中获取压缩后的bitmap
     *
     * @param context
     * @param uri
     * @param reqWidth
     * @param reqHeight
     * @return obj[0] 图片文件路径， obj[1] bitmap
     */
    public static Object[] getBitmapFromUri(Context context, Uri uri, int reqWidth, int reqHeight) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        Bitmap bitmap = compressImage(imagePath, reqWidth, reqHeight);
        File tmpFile = FileUtil.createTempImage(/*context*/);
        Object[] objects = new Object[2];
        objects[0] = tmpFile;
        objects[1] = bitmap;
        if (save(bitmap, tmpFile, Bitmap.CompressFormat.PNG)) {
            return objects;
        } else {
            return null;
        }
    }

    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 压缩图片文件
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return obj[0] 图片文件， obj[1] bitmap
     */
    public static Object[] compressImageFile(String filePath, int reqWidth, int reqHeight) {
        Object[] objects = new Object[2];
        Bitmap bitmap = compressImage(filePath, reqWidth, reqHeight);
        File tmpFile = FileUtil.createTempImage();
        objects[0] = tmpFile;
        objects[1] = bitmap;
        if (save(bitmap, tmpFile, Bitmap.CompressFormat.PNG)) {
            return objects;
        } else {
            return null;
        }
    }

    public static void takeImageFromAlbum(Activity activity) {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        activity.startActivityForResult(intent, Const.REQUEST.REQUEST_FOR_TAKE_FROM_ALBUM);
    }

    public static void takeImageFromAlbum(BaseCoreFragment fragment) {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        fragment.startActivityForResult(intent, Const.REQUEST.REQUEST_FOR_TAKE_FROM_ALBUM);
    }

    public static String takeImageByCamera(Activity activity) {
        String filePath;
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = FileUtil.createTempImage();
        filePath = file.toString();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName()+".FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        activity.startActivityForResult(intent2, Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA);
        return filePath;
    }

    public static String takeImageByCamera(BaseCoreFragment fragment) {
        String filePath;
        Context context = fragment.getContext();
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = FileUtil.createTempImage();
        filePath = file.toString();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()+".FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        fragment.startActivityForResult(intent2, Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA);
        return filePath;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth() <= 0 ? 400 : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? 400 : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public static String takeImageByCamera(Fragment fragment) {
        String filePath;
        Activity activity = fragment.getActivity();
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = FileUtil.createTempImage();
        filePath = file.toString();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName()+".FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        fragment.startActivityForResult(intent2, Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA);
        return filePath;
    }

    public static void takeImageFromAlbum(Fragment fragment) {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        fragment.startActivityForResult(intent, Const.REQUEST.REQUEST_FOR_TAKE_FROM_ALBUM);
    }

    /**
     * 将Bitmap转化为字节流
     *
     * @param bmp 待转化Bitmap
     * @return Bitmap转化后对应的字节流
     */
    public static byte[] bitmapToByte(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();        // 初始化一个流对象
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);                    // 把bitmap100%高质量压缩 到output对象里
        bmp.recycle();                                                    // 自由选择是否进行回收
        byte[] result = output.toByteArray();                            // 转换成功了
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将view转换成bitmap
     * @param view
     * @param widthAndHeight
     * @return
     */
    public static Bitmap view2Bitmap(View view, int[] widthAndHeight) {
        view.setDrawingCacheEnabled(true);      //启用绘图缓存
        //调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
        view.measure(View.MeasureSpec.makeMeasureSpec(widthAndHeight[0], View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(widthAndHeight[1], View.MeasureSpec.EXACTLY));
        //这个方法也非常重要，设置布局的尺寸和位置
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();               //创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

//    public static Bitmap getBlurBitmap(Bitmap bitmap) {
//        return StackBlur.blurNatively(bitmap, 20, false);
//    }
}
