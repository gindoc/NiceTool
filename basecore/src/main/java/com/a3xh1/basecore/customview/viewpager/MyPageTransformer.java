package com.a3xh1.basecore.customview.viewpager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author: GIndoc on 2017/12/14 下午3:23
 * email : 735506583@qq.com
 * FOR   :
 */
public class MyPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9f;
    private static final float MIN_ALPHA = 0.9f;
    private float excursion = 0;

    public MyPageTransformer(float excursion) {
        this.excursion = excursion;
    }

    @Override
    public void transformPage(View view, float position) {
        position = position + excursion;
        if (position < -0.3 || position > 0.3) {
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 0.3) { // [-1,1]
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < 0) {
                float scaleX = 1 + 0.3f * position;
                view.setScaleX(scaleX);
                view.setScaleY(scaleX);
            } else {
                float scaleX = 1 - 0.3f * position;
                view.setScaleX(scaleX);
                view.setScaleY(scaleX);
            }
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }
}
