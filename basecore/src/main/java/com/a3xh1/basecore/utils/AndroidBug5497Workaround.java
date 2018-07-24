package com.a3xh1.basecore.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * 解决沉浸式状态栏下设置android:windowSoftInputMode="adjustResize"无效的问题，记得在activity的setContentView之后设置
 */
public class AndroidBug5497Workaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497  
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.  

    public static AndroidBug5497Workaround assistActivity(Activity activity) {
        return new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;                           // 我们自己的根布局
    private int usableHeightPrevious;                       // 上一个可见区域的高度
    private FrameLayout.LayoutParams frameLayoutParams;     // 我们自己的根布局的LayoutParams
    private Activity mActivity;

    private AndroidBug5497Workaround(Activity activity) {
        mActivity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();   // DecorView的高度（包含状态栏的高度）
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;          // 被软键盘遮挡的高度
            // 如果被遮挡的高度大于DecorView的高度的1/4，则认为软键盘弹出，则将我们的布局高度设置为可见区域的高度，
            // 这样adjustResize就可以将scrollview往上移动了；
            // 否则认为软键盘没弹出，则将我们的布局高度设置为DecorView的高度
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible  
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden  
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            if (listener != null) {
                listener.onToggle(heightDifference > (usableHeightSansKeyboard / 4));
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 获取可见区域的高度，包含状态栏的高度
     *
     * @return
     */
    private int computeUsableHeight() {
//        int statusBarHeight = WindowUtil.getStatusHeight(mActivity);      // 75
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);                    // 获取我们的布局mChildOfContent在窗体的可视区域
        if (r.top == 0) {                                                   // 由于statusbarUtil的缘故，沉浸式下r.top = 75而不是0
            r.top = WindowUtil.getStatusHeight(mActivity);                  // 状态栏目的高度
            return r.bottom - r.top;    // 是否需要减去状态栏的高度？应该要，因为最终是用DecorView的高度（包含状态栏的高度）- 可见区域的高度（正常的沉浸式下不包含状态栏的高度）；注：但是由于statusbarUtil的缘故，我们的沉浸式非正常（包含状态栏的高度），r.top = 75而不是0
        }
//        return r.bottom - r.top;      // 减去状态栏的高度后软键盘上面有一块灰色空白（状态栏高度），这是因为由于statusbarUtil的缘故，我们的沉浸式非正常（状态栏'可见'），所以不需要减r.top，否则不可见区域变大了
        return r.bottom;
    }

    private onKeyboardToggleListener listener;

    public void setListener(onKeyboardToggleListener listener) {
        this.listener = listener;
    }

    public interface onKeyboardToggleListener {
        void onToggle(boolean isShow);
    }
}  