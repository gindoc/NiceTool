package com.a3xh1.basecore.customview.swip_to_load;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DataBindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding dataBinding;

    public DataBindingViewHolder(View itemView, ViewDataBinding dataBinding) {
        super(itemView);
        this.dataBinding = dataBinding;
    }

    public ViewDataBinding getBinding() {
        return dataBinding;
    }
}