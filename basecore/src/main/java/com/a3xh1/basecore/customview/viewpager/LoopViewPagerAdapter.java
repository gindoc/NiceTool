package com.a3xh1.basecore.customview.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a3xh1.basecore.R;
import com.a3xh1.basecore.customview.RoundImage;
import com.bumptech.glide.Glide;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: GIndoc on 2017/12/15 下午8:57
 * email : 735506583@qq.com
 * FOR   :
 */
public class LoopViewPagerAdapter extends BaseLoopPagerAdapter {
    private final List<String> banners;
//    private LinearLayout pagerIndicator;
    private List<ImageView> dots = new ArrayList<>();
    private int currentIndex;
    private Reference<Context> mContextRef;
    private Reference<LinearLayout> pagerIndicatorRef;
//    private Context mContext;

    public LoopViewPagerAdapter(ViewPager viewPager) {
        super(viewPager);
        mContextRef = new WeakReference<Context>(viewPager.getContext());
//        mContext = viewPager.getContext();
        banners = new ArrayList<>();
    }

    public LoopViewPagerAdapter(ViewPager viewPager, LinearLayout pagerIndicator) {
        super(viewPager);
        mContextRef = new WeakReference<Context>(viewPager.getContext());
//        mContext = viewPager.getContext();
        pagerIndicatorRef = new WeakReference<LinearLayout>(pagerIndicator);
//        this.pagerIndicator = pagerIndicator;
        banners = new ArrayList<>();
    }

    public void setList(List<String> data) {
        banners.clear();
        banners.addAll(data);
        dots.clear();
        Context context = mContextRef.get();
        LinearLayout pagerIndicator = pagerIndicatorRef.get();
        if (pagerIndicator != null && context!=null) {
            pagerIndicator.removeAllViews();
            for (int i=0; i<banners.size();++i) {
                ImageView imageView = new ImageView(context);
//                ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(R.drawable.indicator_selector);
                if (i == 0) {
                    imageView.setSelected(true);
                } else {
                    imageView.setSelected(false);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(8, 8, 8, 8);
                imageView.setLayoutParams(lp);
                dots.add(imageView);
                pagerIndicator.addView(imageView);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getPagerCount() {
        return banners.size();
    }

    @Override
    public String getItem(int position) {
        return banners.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new RoundImage(parent.getContext());
            ((RoundImage) convertView).setScaleType(ImageView.ScaleType.FIT_XY);
            ((RoundImage) convertView).setType(RoundImage.TYPE_ROUND);
            ((RoundImage) convertView).setmRadius(10);
        }
        String banner = banners.get(position);
        Glide.with(parent.getContext()).load(banner).into((ImageView) convertView);
        return convertView;
    }

    @Override
    public void onPageItemSelected(int position) {
        LinearLayout pagerIndicator = pagerIndicatorRef.get();
        if (pagerIndicator != null) {
            dots.get(currentIndex).setSelected(false);
            dots.get(position).setSelected(true);
            currentIndex = position;
        }
    }

    public void detach() {
        if (mContextRef.get() != null) {
            mContextRef.clear();
            mContextRef = null;
        }
        if (pagerIndicatorRef.get() != null) {
            pagerIndicatorRef.clear();
            pagerIndicatorRef = null;
        }
    }
}