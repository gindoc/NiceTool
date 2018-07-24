package com.a3xh1.basecore.customview.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MaxRecyclerView extends RecyclerView {
    public MaxRecyclerView(Context context) {
        super(context);
        init();
    }

    public MaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MaxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    private void init() {
        setNestedScrollingEnabled(false);
    }

    /**
     * 设置recyclerView的高度为所有item的总高度
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}