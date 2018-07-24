package com.a3xh1.basecore.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a3xh1.basecore.R;

/**
 * Created by 肖勇浩 on 2018/1/12.
 */

public class PopWindowUtils {

    public static CustomPopupWindow BuilderPopWindow(int Layout, Activity activity) {

//        customPopupWindow=new CustomPopupWindow.Builder(activity)
//                .setAnimationStyle(R.anim.pop_enter_anim)
//                .setFouse(true)
//                .setOutSideCancel(true)
//                .setwidth(ViewGroup.LayoutParams.MATCH_PARENT)
//                .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setContentView(Layout)
//                .builder();
        return new CustomPopupWindow.Builder(activity)
                .setAnimationStyle(R.anim.pop_enter_anim)
                .setFouse(true)
                .setOutSideCancel(true)
                .setwidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentView(Layout)
                .builder();
    }

    ;

    public static CustomPopupWindow createComfirmPopup(Activity activity, String comfirmText, final OnConfirmClickListener listener) {

//        customPopupWindow=new CustomPopupWindow.Builder(activity)
//                .setAnimationStyle(R.anim.pop_enter_anim)
//                .setFouse(true)
//                .setOutSideCancel(true)
//                .setwidth(ViewGroup.LayoutParams.MATCH_PARENT)
//                .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setContentView(Layout)
//                .builder();
        CustomPopupWindow customPopupWindow = new CustomPopupWindow.Builder(activity)
                .setAnimationStyle(R.anim.pop_enter_anim)
                .setFouse(true)
                .setOutSideCancel(true)
                .setwidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentView(R.layout.pop_comfirm)
                .builder();
        TextView content = (TextView) customPopupWindow.getItemView(R.id.content);
        content.setText(comfirmText);
        customPopupWindow.setViewOnClick(R.id.affirm, new ViewOnClick() {
            @Override
            public void Click(View view) {
                listener.onComfirmClick();
            }
        });
        return customPopupWindow;
    }

    public interface OnConfirmClickListener {
        void onComfirmClick();
    }

    ;
}
