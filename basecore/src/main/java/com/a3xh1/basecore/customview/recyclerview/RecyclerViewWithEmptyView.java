package com.a3xh1.basecore.customview.recyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.a3xh1.basecore.R;
import com.a3xh1.basecore.customview.recyclerview.BaseRecyclerViewAdapter;
import com.a3xh1.basecore.utils.WindowUtil;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Author: GIndoc on 2017/12/25 下午5:46
 * email : 735506583@qq.com
 * FOR   :
 */
public class RecyclerViewWithEmptyView extends RelativeLayout {
    private View mEmptyView;
    private Context mContext;
    private SwipeToLoadLayout mSwipeToLoadLayout;
    private RecyclerView mRecyclerView;
    private int[] mSize;

    public RecyclerViewWithEmptyView(Context context) {
        this(context, null);
    }

    public RecyclerViewWithEmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewWithEmptyView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_refreshable_recyclerview, this);

        mSize = WindowUtil.getScreenWidthAndHeight(context);
        mSwipeToLoadLayout = findViewById(R.id.swipe_to_load);
        mRecyclerView = findViewById(R.id.swipe_target);
    }

    public void setEmptyView(View emptyView) {
        if (mEmptyView != null) {
            removeView(mEmptyView);
        }
        this.mEmptyView = emptyView;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mSize[0], mSize[1]);
        layoutParams.addRule(ABOVE, R.id.swipe_to_load);
        mEmptyView.setLayoutParams(layoutParams);
        mEmptyView.setVisibility(GONE);
        addView(mEmptyView);
    }

    public void setEmptyView(@LayoutRes int layout) {
        mEmptyView = LayoutInflater.from(mContext).inflate(layout, null, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mSize[0], mSize[1]);
        layoutParams.addRule(ABOVE, R.id.swipe_to_load);
        mEmptyView.setLayoutParams(layoutParams);
        mEmptyView.setVisibility(GONE);
        setEmptyView(mEmptyView);
    }

    public void setEmptyView(View emptyView, EmptyViewClickListener emptyViewClickListener) {
        setEmptyView(emptyView);
        setEmptyViewClickListener(emptyViewClickListener);
    }

    public void setEmptyView(@LayoutRes int layout, EmptyViewClickListener emptyViewClickListener) {
        setEmptyView(layout);
        setEmptyViewClickListener(emptyViewClickListener);
    }

    private void setEmptyViewClickListener(EmptyViewClickListener emptyViewClickListener) {
        mEmptyViewClickListener = emptyViewClickListener;
        mEmptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmptyViewClickListener != null) {
                    mEmptyViewClickListener.onEmptyViewClick(v);
                }
            }
        });
    }

    public void setEmptyViewChildClickListener(int id, OnClickListener onClickListener) {
        if (mEmptyView != null) {
            mEmptyView.findViewById(id).setOnClickListener(onClickListener);
        }
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void showEmptyView() {
        if (mRecyclerView.getAdapter().getItemCount() <= 0 && mEmptyView != null) {
            mEmptyView.setVisibility(VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (mRecyclerView.getAdapter().getItemCount() > 0 && mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }
    }

    public void toggleEmptyView() {
        if (mRecyclerView.getAdapter().getItemCount() <= 0 && mEmptyView != null) {
            mEmptyView.setVisibility(VISIBLE);
            mSwipeToLoadLayout.setVisibility(GONE);
        } else if (mRecyclerView.getAdapter().getItemCount() >= 0 && mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
            mSwipeToLoadLayout.setVisibility(VISIBLE);
        }
    }

    public void setAdapter(BaseRecyclerViewAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public interface EmptyViewClickListener {
        void onEmptyViewClick(View view);
    }

    public EmptyViewClickListener mEmptyViewClickListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mSwipeToLoadLayout.setOnLoadMoreListener(onLoadMoreListener);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mSwipeToLoadLayout.setOnRefreshListener(onRefreshListener);
    }

    public void setRefreshEnabled(boolean enabled) {
        mSwipeToLoadLayout.setRefreshEnabled(enabled);
    }

    public void setLoadMoreEnabled(boolean enabled) {
        mSwipeToLoadLayout.setLoadMoreEnabled(enabled);
    }

    public void setRefreshing(boolean enabled) {
        mSwipeToLoadLayout.setRefreshing(enabled);
    }

    public void setLoadingMore(boolean enabled) {
        mSwipeToLoadLayout.setLoadingMore(enabled);
    }

    public boolean isLoadingMore() {
        return mSwipeToLoadLayout.isLoadingMore();
    }

    public boolean isRefreshing() {
        return mSwipeToLoadLayout.isRefreshing();
    }
}
