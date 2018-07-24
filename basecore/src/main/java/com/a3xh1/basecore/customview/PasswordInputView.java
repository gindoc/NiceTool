package com.a3xh1.basecore.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;

import com.a3xh1.basecore.R;
import com.a3xh1.basecore.utils.DensityUtil;

/**
 * Author: GIndoc on 2018/1/16 下午8:35
 * email : 735506583@qq.com
 * FOR   :
 */
public class PasswordInputView extends AppCompatEditText {
    private static final int DEFAULT_INPUT_NUM = 6;
    private static final int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_STROKE_WIDTH = 1;
    private static final int DEFAULT_STROKE_COLOR = R.color.textGrayColor_b0;
    private int inputNum;
    private int inputSize;
    private RectF inputRect;
    private int childHorizontalPadding = 0;
    private int radius;
    private Paint mPaint, circlePaint;

    public PasswordInputView(Context context) {
        super(context);
        setAttr(null, 0);
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttr(attrs, 0);
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttr(attrs, defStyleAttr);
    }

    private void setAttr(AttributeSet attrs, int defStyleAttr) {
        Context context = getContext();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, defStyleAttr, 0);
        int strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputView_strokeWidth, DensityUtil.dip2px(context, DEFAULT_STROKE_WIDTH));
        int strokeColor = ResourcesCompat.getColor(getResources(), DEFAULT_STROKE_COLOR, null);
        strokeColor = typedArray.getColor(R.styleable.PasswordInputView_strokeColor, strokeColor);
        radius = DensityUtil.dip2px(context, DEFAULT_RADIUS);
        radius = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputView_radius, radius);
        inputNum = typedArray.getInt(R.styleable.PasswordInputView_inputNum, DEFAULT_INPUT_NUM);
        if (inputNum == 0) {
            inputNum = DEFAULT_INPUT_NUM;
        }
        childHorizontalPadding = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputView_childHorizontalPadding, 0);

        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(strokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(15);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.FILL);

        setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.transparent, null));
        setTextSize(0);
        setInputType(InputType.TYPE_CLASS_NUMBER);

        inputRect = new RectF();

        setBackgroundDrawable(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int contentWidth = getMeasuredWidth() - getPaddingStart() - getPaddingEnd();
        inputSize = (contentWidth - childHorizontalPadding * (inputNum - 1)) / inputNum;
        setMeasuredDimension(getMeasuredWidth(), resolveSize(inputSize + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int inputCount = getText().length();
        for (int i = 0; i < inputNum; ++i) {
            int left = getPaddingStart() + i * (inputSize + childHorizontalPadding);
            int right = left + inputSize;
            inputRect.set(left, getPaddingTop(), right, getPaddingTop() + inputSize);
            canvas.drawRoundRect(inputRect, radius, radius, mPaint);
            if (i < inputCount) {
                float ovalLeft = left + inputSize / 2f - 10;
                float ovalTop = getPaddingTop() + inputSize / 2f - 10;
                float ovalRight = left + inputSize / 2f + 10;
                float ovalBottom = getPaddingTop() + inputSize / 2f + 10;
                canvas.drawCircle((ovalLeft + ovalRight) / 2, (ovalTop + ovalBottom) / 2, 10, circlePaint);
            }
        }
        if (inputCount == inputNum && mPasswordInputListener != null) {
            mPasswordInputListener.onFinish(getText().toString());
        }
    }

    PasswordInputListener mPasswordInputListener;

    public void setPasswordInputListener(PasswordInputListener passwordInputListener) {
        mPasswordInputListener = passwordInputListener;
    }

    public interface PasswordInputListener{
        void onFinish(String password);
    }
}
