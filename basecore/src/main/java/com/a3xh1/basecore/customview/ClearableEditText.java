package com.a3xh1.basecore.customview;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.a3xh1.basecore.R;


/**
 * Created by lidacheng on 2018/1/9.
 */

public class ClearableEditText extends android.support.v7.widget.AppCompatEditText {
    private Drawable clearDrawable;

    public ClearableEditText(Context context) {
        super(context);
        initView(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        clearDrawable = context.getResources().getDrawable(R.mipmap.ic_clear);
        clearDrawable.setBounds(0, 0, clearDrawable.getMinimumWidth(), clearDrawable.getMinimumHeight());
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    setCompoundDrawables(null, null, clearDrawable, null);
                } else {
                    setCompoundDrawables(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (event.getX() > (getWidth() - 80)) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
