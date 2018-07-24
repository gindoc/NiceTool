package com.a3xh1.basecore.customview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;


/**
 * Author: GIndoc on 2017/12/26 下午5:35
 * email : 735506583@qq.com
 * FOR   :
 */
public class MySwipeToLoadLayout extends SwipeToLoadLayout {
    private View mTargetView;

    public MySwipeToLoadLayout(Context context) {
        super(context);
    }

    public MySwipeToLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTargetView(View targetView) {
        mTargetView = targetView;
    }

    protected boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT >= 14) {
            return mTargetView.canScrollVertically(-1);
        } else {
            return mTargetView.canScrollVertically(-1) || mTargetView.getScrollY() > 0;
        }
    }

    protected boolean canChildScrollDown() {
        if (Build.VERSION.SDK_INT >= 14) {
            return mTargetView.canScrollVertically(1);
        } else {
            return mTargetView.canScrollVertically(1) || mTargetView.getScrollY() < 0;
        }
    }

}
