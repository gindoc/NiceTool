package com.a3xh1.basecore.customview.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenwenhui on 2017/7/8.
 */

public class InterceptRecyclerView extends RecyclerView {


    public InterceptRecyclerView(Context context) {
        super(context);
    }

    public InterceptRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}
