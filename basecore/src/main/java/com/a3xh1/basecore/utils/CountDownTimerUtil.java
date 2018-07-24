package com.a3xh1.basecore.utils;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.a3xh1.basecore.R;

/**
 * Created by Administrator on 2017/5/24.
 */
public class CountDownTimerUtil {
    private static MyCountDownTimer myCountDownTimer;
    private static OnTickerListener listener;

    public static void startCountDown(Button button) {
        button.setClickable(false);
        myCountDownTimer = new MyCountDownTimer(button);
        myCountDownTimer.start();
    }

    public static void startCountDown(TextView textView) {
        textView.setClickable(false);
        myCountDownTimer = new MyCountDownTimer(textView);
        myCountDownTimer.start();
    }

    public static void cancelCountDown() {
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
    }

    public static void startCountDown(long millure, OnTickerListener listener) {
        myCountDownTimer = new MyCountDownTimer(millure, listener);
        myCountDownTimer.start();
    }

    public interface OnTickerListener {
        void onTicket(long millure);
    }

    private static class MyCountDownTimer extends CountDownTimer {

        private OnTickerListener listener;

        private Button button;
        private TextView mTextView;

        public MyCountDownTimer(Button button) {
            super(60000, 1000);
            this.button = button;
        }

        public MyCountDownTimer(TextView textView) {
            super(60000, 1000);
            mTextView = textView;
        }

        public MyCountDownTimer(long millure, OnTickerListener listener) {
            super(millure, 1000);
            this.listener = listener;
        }

        @Override
        public void onTick(long l) {
            if (button != null) {
                button.setText(l / 1000 + "s");
            }
            if (mTextView!=null){
                mTextView.setText(l / 1000 + "s");
            }
            if (listener != null) {
                listener.onTicket(l);
            }
        }

        @Override
        public void onFinish() {
            if (button != null) {
                button.setClickable(true);
                button.setText(button.getContext().getString(R.string.send_validate_code));
            }
            if (mTextView != null) {
                mTextView.setClickable(true);
                mTextView.setText(mTextView.getContext().getString(R.string.send_validate_code));
            }
            myCountDownTimer = null;
        }
    }
}