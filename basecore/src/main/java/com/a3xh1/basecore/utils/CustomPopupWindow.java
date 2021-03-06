package com.a3xh1.basecore.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


public class CustomPopupWindow {
    private PopupWindow mPopupWindow;
    private View contentview;
    private static Reference<Activity> activityReference;
//    private static Activity mContext;


    public CustomPopupWindow(Builder builder) {
        Activity activity = activityReference.get();
        if (activity == null) return;
        contentview = LayoutInflater.from(activity).inflate(builder.contentviewid, null);
        mPopupWindow = new PopupWindow(contentview, builder.width, builder.height, builder.fouse);
        //需要跟 setBackGroundDrawable 结合
        mPopupWindow.setOutsideTouchable(builder.outsidecancel);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(builder.animstyle);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Activity activity = activityReference.get();
                if (activity == null) return;
                WindowUtil.setBackgroundAlpha(activity, 1f);

            }
        });
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    /**
     * popup 消失
     */
    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 根据id获取view
     *
     * @param viewid
     * @return
     */
    public View getItemView(int viewid) {
        if (mPopupWindow != null) {
            return this.contentview.findViewById(viewid);
        }
        return null;
    }

    private ViewOnClick viewOnClick;

    public ViewOnClick getViewOnClick() {
        return viewOnClick;
    }

    public CustomPopupWindow setViewOnClick(int viewid, final ViewOnClick viewOnClick) {
        this.viewOnClick = viewOnClick;
        if (mPopupWindow != null) {
            contentview.findViewById(viewid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewOnClick.Click(v);
                }
            });
            return this;
        }
        return null;
    }

    /**
     * 根据父布局，显示位置
     *
     * @param rootviewid
     * @param gravity
     * @param x
     * @param y
     * @return
     */
    public CustomPopupWindow showAtLocation(int rootviewid, int gravity, int x, int y) {
        Activity activity = activityReference.get();
        if (mPopupWindow != null && activity!=null) {
            View rootview = LayoutInflater
                    .from(activity).inflate(rootviewid, null);
            mPopupWindow.showAtLocation(rootview, gravity, x, y);
            WindowUtil.setBackgroundAlpha(activity, 0.5f);
        }
        return this;
    }

    /**
     * 根据id获取view ，并显示在该view的位置
     *
     * @param targetviewId
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    public CustomPopupWindow showAsLaction(int targetviewId, int gravity, int offx, int offy) {
        Activity activity = activityReference.get();
        if (mPopupWindow != null && activity!=null) {
            View targetview = LayoutInflater
                    .from(activity)
                    .inflate(targetviewId, null);
            mPopupWindow.showAtLocation(targetview,
                    gravity, offx, offy);
            WindowUtil.setBackgroundAlpha(activity, 0.5f);
        }
        return this;
    }

    public CustomPopupWindow showBottom(
            int targetviewId) {
        Activity activity = activityReference.get();
        if (mPopupWindow != null && activity!=null) {
            View targetview = LayoutInflater
                    .from(activity)
                    .inflate(targetviewId, null);
            mPopupWindow.showAtLocation(targetview,
                    Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
            WindowUtil.setBackgroundAlpha(activity, 0.5f);
        }
        return this;
    }

    public CustomPopupWindow showCentre(int targetviewId) {
        Activity activity = activityReference.get();
        if (mPopupWindow != null && activity != null) {
            View targetview = LayoutInflater
                    .from(activity)
                    .inflate(targetviewId, null);
            mPopupWindow.showAtLocation(targetview,
                    Gravity.CENTER, 0, 0);
            WindowUtil.setBackgroundAlpha(activity, 0.5f);
        }
        return this;
    }

    /**
     * 显示在 targetview 的不同位置
     *
     * @param targetview
     * @param gravity
     * @param offx
     * @param offy
     * @return
     */
    public CustomPopupWindow showAsDropDown(View targetview, int gravity, int offx, int offy) {
        Activity activity = activityReference.get();
        if (mPopupWindow != null && activity != null) {
            mPopupWindow.showAsDropDown(targetview, gravity, offx, offy);
            WindowUtil.setBackgroundAlpha(activity, 0.5f);
        }
        return this;
    }

    /**
     * 根据id设置焦点监听
     *
     * @param viewid
     * @param listener
     */
    public void setOnFocusListener(int viewid, View.OnFocusChangeListener listener) {
        View view = getItemView(viewid);
        view.setOnFocusChangeListener(listener);
    }

    /**
     * builder 类
     */
    public static class Builder {
        private int contentviewid;
        private int width;
        private int height;
        private boolean fouse;
        private boolean outsidecancel;
        private int animstyle;

        public Builder(Activity context) {
            activityReference = new WeakReference<Activity>(context);
//            mContext = context;
        }

        public Builder setContentView(int contentviewid) {
            this.contentviewid = contentviewid;
            return this;
        }

        public Builder setwidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setheight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFouse(boolean fouse) {
            this.fouse = fouse;
            return this;
        }

        public Builder setOutSideCancel(boolean outsidecancel) {
            this.outsidecancel = outsidecancel;
            return this;
        }

        public Builder setAnimationStyle(int animstyle) {
            this.animstyle = animstyle;
            return this;
        }

        public CustomPopupWindow builder() {
            return new CustomPopupWindow(this);
        }
    }
}