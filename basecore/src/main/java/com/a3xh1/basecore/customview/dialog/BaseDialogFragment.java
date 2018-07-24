package com.a3xh1.basecore.customview.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.a3xh1.basecore.R;

/**
 * Author: GIndoc on 2017/11/13 下午5:31
 * email : 735506583@qq.com
 * FOR   :
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public static final String ANIMATION = "pop_animation";
    public static final String IS_FULL_WIDTH = "is_full_width";
    public static final String IS_FULL_HEIGHT = "is_full_height";
    public static final String POP_DIRECTION = "pop_direction";

    /*** 默认点击外面无效*/
    private boolean onTouchOutside = true;


    /*** 设置是否允许点击外面取消** @param onTouchOutside* @return*/
    public BaseDialogFragment setOnTouchOutside(boolean onTouchOutside) {
        this.onTouchOutside = onTouchOutside;
        return this;
    }

    /**
     * 重写onCreate方法设置Fragment的styled为DialogFragment.STYLE_NO_FRAME。据官方文档介绍，此样式不画任何框架
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 方式 1
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        // 方式 2
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DefaultDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initWindow();
        View view = initView(inflater, container, savedInstanceState);
        initData();
        return view;
    }

    public void initData() {
    }

    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    private void initWindow() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        int dialogAnimation = bundle.getInt(ANIMATION, R.style.DefaultDialogFragmentAnimation);
        boolean isFullWidth = bundle.getBoolean(IS_FULL_WIDTH, false);
        boolean isFullHeight = bundle.getBoolean(IS_FULL_HEIGHT, false);
        int popDirection = bundle.getInt(POP_DIRECTION, Gravity.CENTER);

        Window window = getDialog().getWindow();
//        // 配合方式 1 去掉标题
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 配合方式 1   // 设置背景透明
//        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 配合方式 1
//        window.setDimAmount(0.5f);//背景黑暗度

        // set cancel on touch outside
        getDialog().setCanceledOnTouchOutside(onTouchOutside);
        window.setWindowAnimations(dialogAnimation);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = window.getAttributes();
        if (isFullWidth) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (isFullHeight) {
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        params.gravity = popDirection;
    }
}
