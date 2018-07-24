package com.a3xh1.basecore.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.a3xh1.basecore.R;


/**
 * Created by lidacheng on 2018/1/9.
 */
public class PasswordEditText extends android.support.v7.widget.AppCompatEditText {
    private Drawable hideDrawable;
    private Drawable showDrawable;
    private boolean isPasswordVisible = false;

    public PasswordEditText(Context context) {
        super(context);
        initView(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        showDrawable = context.getResources().getDrawable(R.drawable.ic_show_pwd);
        hideDrawable = context.getResources().getDrawable(R.drawable.ic_hide_pwd);
        hideDrawable.setBounds(0, 0, hideDrawable.getMinimumWidth(), hideDrawable.getMinimumHeight());
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        setCompoundDrawables(null, null, hideDrawable, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (event.getX() > (getWidth() * 4 / 5)) {
                    if (isPasswordVisible) {
                        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, hideDrawable, null);
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else {
                        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, showDrawable, null);
                        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }
                    isPasswordVisible = !isPasswordVisible;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
