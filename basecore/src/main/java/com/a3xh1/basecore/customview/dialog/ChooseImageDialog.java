package com.a3xh1.basecore.customview.dialog;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3xh1.basecore.R;
import com.a3xh1.basecore.databinding.DialogChooseImgBinding;
import com.a3xh1.basecore.utils.Const;
import com.a3xh1.basecore.utils.ImageUtil;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * Author: GIndoc on 2017/11/13 下午5:30
 * email : 735506583@qq.com
 * FOR   :
 */
public class ChooseImageDialog extends BaseDialogFragment implements FcPermissionsCallbacks {
    private DialogChooseImgBinding mBinding;
    private String filePath;

    @Inject
    public ChooseImageDialog() {
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogChooseImgBinding.inflate(inflater, container, false);
        mBinding.setDialog(this);
        return mBinding.getRoot();
    }

    public void takeImageFromAlbum() {
        FcPermissions.requestPermissions(this, "该功能需要读取存储卡权限", Const.REQUEST.REQUEST_FOR_TAKE_FROM_ALBUM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void takeImageFromCamera() {
        FcPermissions.requestPermissions(this, "该功能需要开启摄像头", Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA, Manifest.permission.CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Const.REQUEST.REQUEST_FOR_TAKE_FROM_ALBUM && listener != null) {
            Object[] objects = ImageUtil.getBitmapFromUri(getContext(), data.getData(), 600, 600);
            listener.onImageTakenFromAlbum(objects);
        } else if (requestCode == Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA && listener != null) {
            Object[] objects = ImageUtil.compressImageFile(filePath, 600, 600);
            listener.onImageTakenFromCamera(objects);
        }
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {
        if (i == Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA) {
            filePath = ImageUtil.takeImageByCamera(this);
        } else {
            ImageUtil.takeImageFromAlbum(this);
        }
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        if (i == Const.REQUEST.REQUEST_FOR_TAKE_FROM_CAMERA) {
            FcPermissions.checkDeniedPermissionsNeverAskAgain(this,
                    "本功能需要使用摄像头和写入存储卡权限，否则该功能无法使用",
                    R.string.setting, R.string.cancel, null, list);
        } else {
            FcPermissions.checkDeniedPermissionsNeverAskAgain(this, "该功能需要读写存储卡权限，否则该功能无法使用",
                    R.string.setting, R.string.cancel, null, list);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public OnDialogClickListener listener;

    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    public interface OnDialogClickListener {
        void onImageTakenFromCamera(Object[] data);

        void onImageTakenFromAlbum(Object[] data);
    }
//    public static ChooseImageDialog build(ChooseImageDialog chooseImageDialog) {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean(IS_FULL_WIDTH, true);
//        bundle.putInt(POP_DIRECTION, Gravity.BOTTOM);
//        chooseImageDialog.setArguments(bundle);
//        return chooseImageDialog;
//    }
}
