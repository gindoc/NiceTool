package com.a3xh1.basecore.customview.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DataBindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding dataBinding;
    private View mView;

    public DataBindingViewHolder(View itemView, ViewDataBinding dataBinding) {
        super(itemView);
        mView = itemView;
        this.dataBinding = dataBinding;
    }

    public View getView() {
        return mView;
    }

    public ViewDataBinding getBinding() {
        return dataBinding;
    }


}