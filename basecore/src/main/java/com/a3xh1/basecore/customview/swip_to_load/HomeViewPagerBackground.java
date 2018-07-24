package com.a3xh1.basecore.customview.swip_to_load;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.a3xh1.basecore.utils.DensityUtil;
import com.a3xh1.basecore.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: GIndoc on 2017/12/19 上午9:18
 * email : 735506583@qq.com
 * FOR   :
 */
public class HomeViewPagerBackground extends RelativeLayout {
    private List<PointF> points;
    private Paint mPaint;
    private Path mPath;

    public HomeViewPagerBackground(Context context) {
        this(context, null);
    }

    public HomeViewPagerBackground(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeViewPagerBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        points = new ArrayList<>();
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int height = getHeight();
        int paddingHorizontal = DensityUtil.dip2px(getContext(), 30);
        int paddingBottom = DensityUtil.dip2px(getContext(), 8.5f);
        int[] size = WindowUtil.getScreenWidthAndHeight(getContext());
        mPaint.setShader(new LinearGradient(size[0], 0, size[0], height, Color.parseColor("#FFAA00"), Color.parseColor("#FFEB00"), Shader.TileMode.CLAMP));
        points.clear();
        points.add(new PointF(size[0], 0));
        points.add(new PointF(size[0], height));
        points.add(new PointF(size[0] - paddingHorizontal, height - paddingBottom));
        points.add(new PointF(paddingHorizontal, height - paddingBottom));
        points.add(new PointF(0, height));
        mPath.moveTo(0, 0);
        for (int i = 0; i < points.size(); i++) {
            PointF pointF = points.get(i);
            mPath.lineTo(pointF.x, pointF.y);
        }
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
