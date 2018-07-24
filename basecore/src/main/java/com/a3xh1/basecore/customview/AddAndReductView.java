package com.a3xh1.basecore.customview;

import android.content.Context;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

import com.a3xh1.basecore.R;


/**
 * Created by Administrator on 2017/4/25.
 */

@InverseBindingMethods({
        @InverseBindingMethod(
                type = AddAndReductView.class,
                attribute = "count",
                event = "android:textAttrChanged",
                method = "getCount"
        )
})
public class AddAndReductView extends AppCompatTextView {
    private int count = 1;
    private OnCountChangeListener listener;

    public AddAndReductView(Context context) {
        super(context);
        init();
    }

    public AddAndReductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddAndReductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnCountChangeListener(OnCountChangeListener listener) {
        this.listener = listener;
    }

    private void init() {
        setClickable(true);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.add_and_reduce);
        setText("1");
    }

    public interface OnCountChangeListener {
        void onCountChange(int count);

        void onCountReduce(int last, int current);

        void onCountIncrease(int last, int current);
    }

    public void setCount(int count) {
        this.count = count;
        setText(String.valueOf(count));
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (x < getMeasuredWidth() / 3 && count != 0) {
                if (listener != null) {
                    listener.onCountReduce(count, count - 1);
                }
                count--;
                if (count == 0) {
                    count = 1;
                }
                setText(String.valueOf(count));
            } else if (x > (getMeasuredWidth() / 3) * 2) {
                if (listener != null) {
                    listener.onCountIncrease(count, count + 1);
                }
                count++;
                setText(String.valueOf(count));
            }
            if (listener != null) {
                listener.onCountChange(count);
            }
        }
        return super.onTouchEvent(event);
    }
}
