package com.a3xh1.basecore.customview.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * Author: GIndoc on 2017/12/19 下午1:41
 * email : 735506583@qq.com
 * FOR   :
 */
public class BannerViewPager extends ViewPager {

    private CustomDurationScroller scroller = null;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupScroller();
    }

    private void setupScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scroller.setScrollDurationFactor(1000);
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
