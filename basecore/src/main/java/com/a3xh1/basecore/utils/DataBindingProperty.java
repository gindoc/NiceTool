package com.a3xh1.basecore.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a3xh1.basecore.R;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class DataBindingProperty {

    @BindingAdapter({"bankUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        DrawableRequestBuilder builder = Glide.with(context)
                .load(context.getString(R.string.api) + url)
                .placeholder(R.drawable.empty_bg)
                .error(R.drawable.empty_bg);
        if (imageView.getMeasuredWidth() != 0 && imageView.getMeasuredHeight() != 0) {
            builder.override(imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        }
        builder.into(imageView);
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageScr(ImageView imageView, String url) {
        DrawableRequestBuilder builder;
        builder = Glide.with(imageView.getContext())
                .load(url);
        if (imageView.getId() == R.id.iv_user_head) {
            builder.placeholder(R.drawable.default_head)
                    .error(R.drawable.default_head);

        } else {
            builder.placeholder(R.drawable.empty_bg)
                    .error(R.drawable.empty_bg);
        }
        if (imageView.getMeasuredWidth() != 0 && imageView.getMeasuredHeight() != 0) {
            builder.override(imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        }
        builder.into(imageView);
    }

    @BindingAdapter({"fileUrl"})
    public static void setImageSrc(ImageView imageView, String url) {
        File file = new File(url);
        Glide.with(imageView.getContext())
                .load(file)
                .placeholder(R.drawable.empty_bg)
                .into(imageView);
    }

    @BindingAdapter({"imageId"})
    public static void setImageId(final ImageView imageView, final int imageId) {
//        imageView.setImageResource(imageId);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                int imageWidth = imageView.getMeasuredWidth();
                Glide.with(imageView.getContext())
                        .load(imageId)
                        .placeholder(R.drawable.empty_bg)
                        .into(imageView);
            }
        });

    }

    @BindingAdapter({"isRefresh"})
    public static void isRefresh(SwipeRefreshLayout swipeRefreshLayout, boolean isLoad) {
        swipeRefreshLayout.setRefreshing(isLoad);
    }

    @BindingAdapter({"customSelected"})
    public static void setSelected(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

    @BindingAdapter({"formatPattern","formatValue"})
    public static void formatStrings(TextView textView, String format, Object value) {
        textView.setText(StringUtils.format(format, value));
    }

    @BindingAdapter({"timePattern", "timeValue"})
    public static void formatTime(TextView textView, String format, long timestamp) {
        textView.setText(TimeUtils.milliseconds2String(timestamp, new SimpleDateFormat(format, Locale.getDefault())));
    }
}
